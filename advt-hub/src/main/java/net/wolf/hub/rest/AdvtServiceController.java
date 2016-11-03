
package net.wolf.hub.rest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.wolf.hub.rest.dto.AdvtData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import net.wolf.hub.rest.dto.AdvtResponse;
import net.wolf.hub.rest.dto.RecipientInfo;
import net.wolf.hub.service.AsyncAdvertiseService;
import net.wolf.hub.service.AsyncRequestNotFound;
import net.wolf.hub.service.AsyncRequestRepository;
import net.wolf.hub.service.BussinesLogic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Контроллер сервисов на получение рекламы.
 * 
 * POST /give-advt - сервис для получения рекламы для переданного получателя.
 *    Если сервис не вложился в тайминг (maxResponseTimeMilis - время выполнения бизнесс логики),
 *    то в результе поле AdvtResponse.responseType будет равно ASYNC, а в поле 
 *    AdvtResponse.askedUrl будет URL (см. ниже), по которому клиенту необходимо запросить 
 *    результат через некоторое время.
 *    Если сервис вложился в тайминг, то в поле AdvtResponse.responseType будет SYNC
 *    а поле  AdvtResponse.advtData будет содержать информацию о рекламе
 * 
 * GET /give-advt/{ticket} - сервис опроса результата ASYNC запроса.
 *    Клент должен обращаться, пока поле AdvtResponse.responseType не будет равно ASYNC.
 *    Тогда в поле AdvtResponse.advtData будет информация о рекламе
 *    
 * @author Volchkov Andrey
 */
@RestController
@RequestMapping(value = AdvtServiceController.URL, produces = "application/json")
public class AdvtServiceController {
    
    public static final String URL = "/give-advt";

    private final BussinesLogic bussinesLogic;
    private final AsyncAdvertiseService asyncAdvertiseService;
    private final AsyncRequestRepository asyncRequestRepository;
    
    private int maxResponseTimeMilis = 50;

    @Autowired
    public AdvtServiceController(BussinesLogic bussinesLogic, AsyncAdvertiseService asyncAdvertiseService, AsyncRequestRepository asyncRequestRepository) {
        this.bussinesLogic = bussinesLogic;
        this.asyncAdvertiseService = asyncAdvertiseService;
        this.asyncRequestRepository = asyncRequestRepository;

    }

    @Value("${timing.max.response}")    
    public void setMaxResponseTimeMilis(int maxResponseTimeMilis) {
        this.maxResponseTimeMilis = maxResponseTimeMilis;
    }
    
    

    @RequestMapping(consumes = "application/json", method = RequestMethod.POST)
    public AdvtResponse giveAdvt(@RequestBody RecipientInfo recipientInfo) throws InterruptedException, ExecutionException {
        
        long start = System.currentTimeMillis();
        //усердно делаем нашу работу        
        bussinesLogic.doLogic();
        long elapsed = System.currentTimeMillis() - start;
        
        //получаем информацию по рекламе
        Future<AdvtData> adsDataFuture = asyncAdvertiseService.giveAds(recipientInfo);
        
        try {            
            
            AdvtData ads = adsDataFuture.get(maxResponseTimeMilis - elapsed, TimeUnit.MILLISECONDS);
            return new AdvtResponse(ads);
            
        } catch (TimeoutException toe) {
            
            Long addRequest = asyncRequestRepository.addRequest(adsDataFuture);
            return new AdvtResponse(addRequest);
            
        }
    }
    
    @RequestMapping(value = "/{ticket}", method = RequestMethod.GET)
    public AdvtResponse giveAdvtAsync(@PathVariable("ticket") Long ticket) throws AsyncRequestNotFound, InterruptedException, ExecutionException {
        Future<AdvtData> adsDataFuture = asyncRequestRepository.takeRequestIfDone(ticket);
        
        if (adsDataFuture == null) {
            return new AdvtResponse(ticket);
        } else {
            return new AdvtResponse(adsDataFuture.get());
        }
    }
    
    
    
    @ExceptionHandler(Exception.class)
    public AdvtResponse errorHandler(Exception ex) {
        if (ex instanceof AsyncRequestNotFound) {
            
            AsyncRequestNotFound arnf = (AsyncRequestNotFound) ex;
            
            return new AdvtResponse("Async request with ticket "+ arnf.getTicket() + " not found! Check the ticket number!");
        
        } else if (ex instanceof ExecutionException) {
        
            ExecutionException ee = (ExecutionException) ex;
            
            return new AdvtResponse("Error request to remote advt-service!");
            
        }
        
        return new AdvtResponse(ex.getMessage());
    }
}


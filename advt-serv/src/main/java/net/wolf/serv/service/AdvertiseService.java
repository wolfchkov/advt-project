/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.serv.service;

import javax.annotation.PostConstruct;
import net.wolf.serv.rest.dto.AdvtData;
import net.wolf.serv.rest.dto.RecipientInfo;
import net.wolf.serv.util.AccurateSleep;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Сервис рекламодателя
 *
 * В данной реализации для генерации случайной задержки используется распределение Парето.
 * Таким образом частота вероятности тяготит к minEmulationDelay.
 * {@link https://ru.wikipedia.org/wiki/%D0%A0%D0%B0%D1%81%D0%BF%D1%80%D0%B5%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5_%D0%9F%D0%B0%D1%80%D0%B5%D1%82%D0%BE}
 * @author Volchkov Andrey
 */
@Service
public class AdvertiseService {
    
    private static final double PARETO_INDEX = 1d;
    
    private static final long DEFAULT_MIN_EMULATION_DELAY = 20;
    private static final long DEFAULT_MAX_EMULATION_DELAY = 2000;

    private long minEmulationDelay = DEFAULT_MIN_EMULATION_DELAY;
    private long maxEmulationDelay = DEFAULT_MAX_EMULATION_DELAY;
    
    private final AdvertisingSloganRepository advertisingSloganRepository;
    private ParetoDistribution distribution; 

    @Autowired
    public AdvertiseService(AdvertisingSloganRepository advertisingSloganRepository) {
        this.advertisingSloganRepository = advertisingSloganRepository;        
    }

    @Value("${timing.min.delay}")
    public void setMinEmulationDelay(long minEmulationDelay) {
        this.minEmulationDelay = minEmulationDelay;       
    }

    @Value("${timing.max.delay}")
    public void setMaxEmulationDelay(long maxEmulationDelay) {
        this.maxEmulationDelay = maxEmulationDelay;
    }
    
    @PostConstruct
    public void init() {
        distribution = new ParetoDistribution(minEmulationDelay, PARETO_INDEX);
    }
    
    /**
     * Возвращает случайную величину задержки
     * @return 
     */
    private long gerDelaySample() {
        double sample = distribution.sample();
        long s = Math.round(sample);
        
        if (s > maxEmulationDelay) {
            return maxEmulationDelay;
        }
        return s;
    }
    

    public AdvtData giveAds(RecipientInfo recipientInfo) {
        
        String slogan = advertisingSloganRepository.getSlogan(recipientInfo.getNumber());
        
        long delay = gerDelaySample();
        try {
            AccurateSleep.sleep(delay);
        } catch (InterruptedException ex) {
            //а кто это сделал? о_О
        }
        
        return new AdvtData(slogan);

    }
}



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.serv.rest;

import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import net.wolf.serv.rest.dto.AdvtData;
import net.wolf.serv.rest.dto.RecipientInfo;
import net.wolf.serv.service.AdvertiseService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author Volchkov Andrey
 */
@RestController
@RequestMapping(value = "/slogan", consumes = "application/json", produces = "application/json")
public class SloganServiceController {

    private final AdvertiseService advertiseService;
    

    @Autowired
    public SloganServiceController(AdvertiseService advertiseService) {
        this.advertiseService = advertiseService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public AdvtData giveAds(@RequestBody RecipientInfo recipientInfo) throws InterruptedException, ExecutionException {
        return advertiseService.giveAds(recipientInfo);
    }
}


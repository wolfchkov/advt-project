/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.hub.service;

import java.util.concurrent.Future;
import net.wolf.hub.rest.dto.AdvtData;
import net.wolf.hub.rest.dto.RecipientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Volchkov Andrey
 */
@Component
public class AsyncAdvertiseService {
    
    private final ThreadPoolTaskExecutor taskExecutor;
    private final AdvertiseService advertiseService;

    @Autowired
    public AsyncAdvertiseService(ThreadPoolTaskExecutor taskExecutor, AdvertiseService advertiseService) {
        this.taskExecutor = taskExecutor;
        this.advertiseService = advertiseService;
    }
    
    
    public Future<AdvtData> giveAds(final RecipientInfo recipientInfo) {
        return taskExecutor.submit(
                () -> advertiseService.giveAds(recipientInfo)
        );

    }
    
}

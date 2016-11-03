/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.hub.service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.wolf.hub.rest.dto.AdvtData;
import net.wolf.hub.rest.dto.RecipientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Сервис рекламодателя
 *
 * @author Volchkov Andrey
 */
@Service
public class AdvertiseService {
    
    private final RestTemplate restTemplate; 
    private String serviceUrl;

    @Autowired
    public AdvertiseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }   

    @Value("${advertise.service.url}")
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public AdvtData giveAds(RecipientInfo recipientInfo) {
        return restTemplate.postForObject(serviceUrl, recipientInfo, AdvtData.class);
    }
}

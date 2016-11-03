/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.hub.rest.dto;

import net.wolf.hub.rest.AdvtServiceController;

/**
 * Ответ сервиса поиска рекламы
 * @author Volchkov Andrey
 */
public class AdvtResponse {
    
    /**
     * Тип полученного ответа
     */
    private final ResponseType responseType;
    
    /**
     * Тикет, для запроса результата.
     * Свидетельствует о том, что ответ не вложился в тайминг выполнения запроса 
     * в 50 мс и клиенту нужно повторить запрос через время с этим тикетом
     */
    private String askedUrl;
    
    /**
     * Информация по рекламе
     */
    private AdvtData advtData;
    
    /**
     * Ошибка 
     */
    private String error;

    public AdvtResponse(AdvtData advtData) {
        responseType = ResponseType.SYNC;
        this.advtData = advtData;
    }

    public AdvtResponse(Long asyncTicket) {
        responseType = ResponseType.ASYNC;
        this.askedUrl = AdvtServiceController.URL + "/" + asyncTicket;
    }
    
    public AdvtResponse(String error) {
        responseType = ResponseType.ASYNC;
        this.error = error;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getAskedUrl() {
        return askedUrl;
    }

    public AdvtData getAdvtData() {
        return advtData;
    }

    public String getError() {
        return error;
    }
    
    
    
}

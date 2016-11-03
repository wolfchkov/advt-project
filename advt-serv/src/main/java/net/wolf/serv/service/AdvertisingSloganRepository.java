/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.serv.service;

/**
 * Репозиторий для получения рекламного слогана 
 * @author Volchkov Andrey 
 */
public interface AdvertisingSloganRepository {
    
    /**
     * Полуить рекламный слоган
     * @param num номер для разыгрывания случайного слогана
     * @return сторка слогана
     */
    String getSlogan(int num);
}
            

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.serv.rest.dto;

/**
 * Информация о получателе рекламы
 * @author Volchkov Andrey
 */
public class RecipientInfo {
    
    /**
     * Просто номер, по которому разыграем случайный рекламный слоган
     */
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "RecipientInfo{" + "number=" + number + '}';
    }

}

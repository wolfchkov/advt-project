/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.serv.rest.dto;

/**
 *
 * @author Volchkov Andrey
 */
public class AdvtData {
    
    private String slogan;

    public AdvtData() {
    }

    public AdvtData(String slogan) {
        this.slogan = slogan;
    }
    
    

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

}

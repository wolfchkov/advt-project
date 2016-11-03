/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.serv.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Volchkov Andrey
 */
@Repository
public class AdvertisingSloganFileRepository implements AdvertisingSloganRepository{

    private final List<String> slogans;

    
    public AdvertisingSloganFileRepository() throws IOException {
        Resource resource = new ClassPathResource("/advs-slogans.txt");
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));) {
        
            slogans = new ArrayList<>();
            
            String slogan;
            
            while ( (slogan = br.readLine()) != null) {
            
                slogans.add(slogan);            
                
            }
        }
        
    }
    
    @Override
    public String getSlogan(int num) {
        int nextInt = ThreadLocalRandom.current().nextInt(slogans.size());
        int sloganIndex = (nextInt * num) % slogans.size();
        
        return slogans.get(sloganIndex);
    }
    
}

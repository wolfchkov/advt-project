package net.wolf.hub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AdvtHubApplication {
    
    @Autowired
    private AsyncPoolProperies asyncPoolProperies;
    
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(asyncPoolProperies.getCoreSize());
        threadPoolTaskExecutor.setMaxPoolSize(asyncPoolProperies.getMaxSize());
        threadPoolTaskExecutor.setQueueCapacity(asyncPoolProperies.getMaxSize());
        return  threadPoolTaskExecutor;
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .messageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }    

    public static void main(String[] args) {
        SpringApplication.run(AdvtHubApplication.class, args);
    }
}

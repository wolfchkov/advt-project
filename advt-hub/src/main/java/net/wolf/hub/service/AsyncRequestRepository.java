/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.hub.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import net.wolf.hub.rest.dto.AdvtData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Volchkov Andrey
 */
@Repository
public class AsyncRequestRepository {
    
    private final long DEFAULT_REQUEST_LIVE_TIME = 1;
    private final long DEFAULT_REQUEST_REPO_SIZE = 1_000_000;
            
    
    private final AsyncTicketFactory asyncTicketFactory;
    private Cache<Long, Future<AdvtData>> asyncTaskRepo;
    private long requstLiveTime = DEFAULT_REQUEST_LIVE_TIME;
    private long requstRepoSize = DEFAULT_REQUEST_REPO_SIZE;
    

    @Autowired
    public AsyncRequestRepository(AsyncTicketFactory asyncTicketFactory) {
        this.asyncTicketFactory = asyncTicketFactory;
    }

    @Value("${async.request.live.time}")
    public void setRequstLiveTime(long requstLiveTime) {
        this.requstLiveTime = requstLiveTime;
    }
    
    @Value("${async.request.repo.size}")
    public void setRequstRepoSize(long requstRepoSize) {
        this.requstRepoSize = requstRepoSize;
    }
    
    @PostConstruct
    public void init() {
        asyncTaskRepo = 
            CacheBuilder.newBuilder()
            .expireAfterWrite(requstLiveTime, TimeUnit.MINUTES)
            .maximumSize(requstRepoSize)
            .build();
    }
    
    public Long addRequest(Future<AdvtData> future) {
        Long nextTicket = asyncTicketFactory.getNextTicket();        
        
        asyncTaskRepo.put(nextTicket, future);
        
        return nextTicket;
    }
    
    public Future<AdvtData> takeRequestIfDone(Long ticket) throws AsyncRequestNotFound {
        
        Future<AdvtData> future = asyncTaskRepo.getIfPresent(ticket);
        
        if (future == null) {
            throw new AsyncRequestNotFound(ticket);
        }
        
        if (future.isDone()) {
            asyncTaskRepo.invalidate(ticket);
            return future;
        } 
        
        return null;
    }    
    
}




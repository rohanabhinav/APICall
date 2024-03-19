package com.api.ThirdPartyApiCallerApplication;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {

    private static final int REQUESTS_PER_MINUTE = 60;
    private Map<String, Long> requestCounts = new ConcurrentHashMap<>();

    @Before("@annotation(RateLimited)")
    public void limitRequests() {
        long now = System.currentTimeMillis() / 1000;
        String key = Long.toString(now / 60); // Key for this minute

        requestCounts.putIfAbsent(key, 0L);
        long count = requestCounts.get(key);

        if (count >= REQUESTS_PER_MINUTE) {
            throw new RateLimitExceededException("Rate limit exceeded");
        }

        requestCounts.put(key, count + 1);
    }
}

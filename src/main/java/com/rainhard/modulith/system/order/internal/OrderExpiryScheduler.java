package com.rainhard.modulith.system.order.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OrderExpiryScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderExpiryScheduler.class);

    private final OrderService orderService;

    OrderExpiryScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    //One hour
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void delete(){
        orderService.cancelExpiredOrder();
    }
}

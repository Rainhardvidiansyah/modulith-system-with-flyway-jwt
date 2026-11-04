package com.rainhard.modulith.system.order;

import com.rainhard.modulith.system.order.dto.OrderItemRequest;
import com.rainhard.modulith.system.order.dto.OrderResponse;
import com.rainhard.modulith.system.order.internal.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderFacade {


    //TODO: replace with authenticated user id from security context
    private static final UUID DUMMY_USER_ID = UUID.fromString("239dae3a-5c35-49da-ba55-a7713f5fb027");

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFacade.class);

    private final OrderService orderService;


    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }


    public OrderResponse createOrder(List<OrderItemRequest> request){
        var result = orderService.createOrder(DUMMY_USER_ID, request);
        LOGGER.info("Content of result: {}", result);
        return OrderResponse.from(result);
    }


    public OrderResponse cancelOrderByCustomer(UUID orderId){
        LOGGER.info("Order cancel info ==> order id: {}", orderId);
        var result = orderService.cancelOrderByCustomer(DUMMY_USER_ID, orderId);
        return OrderResponse.from(result);
    }


}



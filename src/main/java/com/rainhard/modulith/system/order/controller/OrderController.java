package com.rainhard.modulith.system.order.controller;

import com.rainhard.modulith.system.order.OrderFacade;
import com.rainhard.modulith.system.order.dto.OrderItemRequest;
import com.rainhard.modulith.system.order.dto.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);



    private final OrderFacade orderFacade;


    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody List<OrderItemRequest> orderItemRequests){
        return orderFacade.createOrder(orderItemRequests);
    }

    @DeleteMapping("/{orderId}")
    public OrderResponse cancelOrderByCustomer(@PathVariable("orderId") UUID orderId){
        return orderFacade.cancelOrderByCustomer(orderId);
    }
}

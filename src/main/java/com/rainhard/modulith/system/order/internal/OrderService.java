package com.rainhard.modulith.system.order.internal;


import com.rainhard.modulith.system.order.OrderCanceled;
import com.rainhard.modulith.system.order.OrderPlaced;
import com.rainhard.modulith.system.order.dto.OrderItemRequest;
import com.rainhard.modulith.system.product.ProductFacade;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class OrderService {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Value("${order.expiry-duration}")
    private int expireDateOrder;

    private final JpaOrderRepository orderRepository;
    private final JpaOrderItemRepository orderItemRepository;
    private final ProductFacade productFacade;
    private final ApplicationEventPublisher event;

    public OrderService(JpaOrderRepository orderRepository, JpaOrderItemRepository orderItemRepository,
                        ProductFacade productFacade, ApplicationEventPublisher event) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productFacade = productFacade;
        this.event = event;
    }

    @Transactional
    public OrderResult createOrder(UUID userId, List<OrderItemRequest> request){
        var orderItems = request
                .stream().map(item -> {
                        var product = productFacade.findProductById(item.productId());
                        return OrderItem.create(
                                null,
                                product.id(),
                                item.quantity(),
                                product.price()
                        );
                        //public static OrderItem create( UUID orderId, UUID productId, int quantity, BigDecimal price
                }).toList();


        BigDecimal totalAmount = orderItems
                .stream()
                .map(orderItem -> orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var order = Order.create(userId, totalAmount, expireDateOrder);

        var savedOrder = orderRepository.save(order);

        var savedOrderItem = orderItems.stream()
                        .map(item ->
                            OrderItem.create(
                                    savedOrder.getId(),
                                    item.getProductId(),
                                    item.getQuantity(),
                                    item.getPrice()
                            )
                        ).toList();


        var orderItemList = orderItemRepository.saveAll(savedOrderItem);

        var orderedItems = orderItemList.stream()
                .map(item ->
                        new OrderPlaced.OrderedItem(item.getProductId(), item.getQuantity()))
                .toList();

        event.publishEvent(new OrderPlaced(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getTotalAmount(),
                orderedItems
        ));

        return new OrderResult(order, orderItems);
    }



    //one hour
    // This method used in OrderExpiryScheduler Class
    @Transactional
    public void cancelExpiredOrder() {
        List<Order> expiredOrders = orderRepository.findExpireOrderDate(Instant.now());

        if (expiredOrders.isEmpty()) {
            return;
        }

        List<UUID> orderIds = expiredOrders.stream()
                        .map(Order::getId).toList();

        List<OrderItem> allItems = orderItemRepository.findByOrderIdIn(orderIds);

        Map<UUID, List<OrderItem>> itemsByOrderId = allItems
                .stream()
                        .collect(Collectors.groupingBy(orderItem -> orderItem.getOrderId()));

        expiredOrders.forEach(order -> {
            order.cancelOrder();
            orderRepository.save(order);

            List<OrderItem> items = itemsByOrderId.getOrDefault(order.getId(), List.of());

            List<OrderCanceled.CanceledItem> canceledItems = items.stream()
                    .map(canceledData -> new OrderCanceled.CanceledItem(canceledData.getProductId(),
                            canceledData.getQuantity()))
                    .toList();

            //Publish event:
            event.publishEvent(new OrderCanceled(order.getId(), canceledItems));
        });
    }




    //Cancel Order
    @Transactional
    public OrderResult cancelOrderByCustomer(UUID userId, UUID orderId){

        var order = orderRepository.findById(orderId)
                .orElseThrow();

        if(!order.getUserId().equals(userId)){
            throw new IllegalStateException("You are not authorized to cancel this order!");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be cancelled");
        }

        order.cancelOrder();
        var savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(order.getId());

        List<OrderCanceled.CanceledItem> canceledItems = orderItems.stream()
                .map(item ->
                    new OrderCanceled.CanceledItem(item.getProductId(), item.getQuantity())
                ).toList();


        //Publish event:
        event.publishEvent(new OrderCanceled(order.getId(), canceledItems));

        return new OrderResult(savedOrder, orderItems);
    }

    public OrderResult findOrderById(){

        return null;
    }



}


/*
Customer want to purchase → ?
Customer wants to cancel the order → ?
Payment successful → ?
look at order detail -> ?
 */
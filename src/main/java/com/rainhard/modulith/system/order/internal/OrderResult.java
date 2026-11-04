package com.rainhard.modulith.system.order.internal;


import java.util.List;

public record OrderResult(Order order, List<OrderItem> items) { }

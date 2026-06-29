package com.rainhard.modulith.system.product.dto;

import java.math.BigDecimal;

public record ProductRequest (
        String name,
        String description,
        BigDecimal price, int quantityAvailable) {}

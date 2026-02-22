package com.gabriel.ticketing.dto.purchase;

public record PurchaseEventDTO(
        Long ticketId,
        String userEmail
) {}
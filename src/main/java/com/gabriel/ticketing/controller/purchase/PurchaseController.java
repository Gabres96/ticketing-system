package com.gabriel.ticketing.controller.purchase;

import com.gabriel.ticketing.dto.purchase.PurchaseRequestDTO;
import com.gabriel.ticketing.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<String> purchase(@RequestBody PurchaseRequestDTO request) {
        purchaseService.processPurchase(request);
        return ResponseEntity.accepted().body("Reserva efetuada. Processando pagamento...");
    }
}
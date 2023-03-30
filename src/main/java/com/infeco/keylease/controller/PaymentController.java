package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Payment;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<Payment> getPayments() {
        return this.paymentService.getPayments();
    }

    @GetMapping("/payments/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(this.paymentService.getPaymentById(id));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

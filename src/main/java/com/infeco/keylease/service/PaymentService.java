package com.infeco.keylease.service;

import com.infeco.keylease.entity.PaymentEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Payment;
import com.infeco.keylease.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // GET ALL
    public List<Payment> getPayments() {
        return this.paymentRepository.findAll().stream().map(Payment::new).collect(Collectors.toList());
    }

    // GET BY ID
    public Payment getPaymentById(UUID id) throws NotFoundEntity {
        Optional<PaymentEntity> optionalPaymentEntity = this.paymentRepository.findById(id);
        if (optionalPaymentEntity.isPresent()) {
            return entityToPayment(optionalPaymentEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    // METHODS
    private Payment entityToPayment(PaymentEntity paymentEntity) {
        return new Payment(paymentEntity);
    }
}

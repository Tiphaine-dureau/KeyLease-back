package com.infeco.keylease.service;

import com.infeco.keylease.entity.LeaseContractEntity;
import com.infeco.keylease.entity.PaymentEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Payment;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final LeaseContractRepository leaseContractRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          LeaseContractRepository leaseContractRepository) {
        this.paymentRepository = paymentRepository;
        this.leaseContractRepository = leaseContractRepository;
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

    // GET ALL PAYMENTS BY LEASE CONTRACT ID
    public List<Payment> getPaymentsByLeaseContractId(UUID leaseContractId) {
        return paymentRepository.findAllByLeaseContractId(leaseContractId).stream().map(Payment::new).collect(Collectors.toList());
    }

    // POST
    public Payment addPayment(Payment payment) throws NotFoundEntity {
        LeaseContractEntity leaseContractEntity = leaseContractRepository.findById(payment.getLeaseContractId())
                .orElseThrow(NotFoundEntity::new);
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setLeaseContract(leaseContractEntity);
        paymentEntity.setRentPaymentDate(payment.getRentPaymentDate());
        paymentEntity.setPaidRent(payment.getPaidRent());

        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        return new Payment(savedPaymentEntity);
    }

    // PUT
    public Payment updatePayment(UUID paymentId, Payment payment) throws NotFoundEntity {
        PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
                .orElseThrow(NotFoundEntity::new);
        LeaseContractEntity leaseContractEntity = leaseContractRepository.findById(payment.getLeaseContractId())
                .orElseThrow(NotFoundEntity::new);
        paymentEntity.setLeaseContract(leaseContractEntity);
        paymentEntity.setRentPaymentDate(payment.getRentPaymentDate());
        paymentEntity.setPaidRent(payment.getPaidRent());

        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        return new Payment(savedPaymentEntity);
    }

    // DELETE
    public void deletePayment(UUID id) throws NotFoundEntity {
        PaymentEntity paymentEntity = paymentRepository.findById(id)
                .orElseThrow(NotFoundEntity::new);
        this.paymentRepository.delete(paymentEntity);
    }

    // METHODS
    private Payment entityToPayment(PaymentEntity paymentEntity) {
        return new Payment(paymentEntity);
    }
}

package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.models.Payment;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.LeaseContractService;
import com.infeco.keylease.service.PaymentService;
import com.infeco.keylease.service.RentReceiptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@RestController
public class RentReceiptController {

    private final RentReceiptService rentReceiptService;
    private final PaymentService paymentService;
    private final LeaseContractService leaseContractService;

    public RentReceiptController(RentReceiptService rentReceiptService,
                                 PaymentService paymentService, LeaseContractService leaseContractService) {
        this.rentReceiptService = rentReceiptService;
        this.paymentService = paymentService;
        this.leaseContractService = leaseContractService;
    }

    @GetMapping(
            value = "receipt/lease-contracts/{leaseContractId}/payments/{paymentId}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<byte[]> getPayments(@PathVariable UUID paymentId, @PathVariable UUID leaseContractId) throws NotFoundEntity {
        Payment payment = paymentService.getPaymentById(paymentId);
        LeaseContract leaseContract = leaseContractService.getLeaseContractById(leaseContractId);
        ByteArrayInputStream pdf = this.rentReceiptService.getRentReceipt(payment, leaseContract);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(pdf.readAllBytes(), headers, HttpStatus.OK);
    }
}

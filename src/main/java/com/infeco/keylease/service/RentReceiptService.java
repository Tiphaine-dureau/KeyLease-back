package com.infeco.keylease.service;

import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.models.Payment;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class RentReceiptService {
    public ByteArrayInputStream getRentReceipt(Payment payment, LeaseContract leaseContract) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            // SETTING FONT
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            Document document = new Document(pdfDocument).setFont(font);
            document.setMargins(20, 20, 20, 20);

            // TITLE
            String title = "Quittance de loyer";
            document.add(new Paragraph(title).setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER));

            // OWNER
            Div ownerDiv = new Div()
                    .setMarginTop(20)
                    .setMarginRight(300)
                    .setPaddingLeft(10)
                    .add(new Paragraph("Propriétaire : ").setBold())
                    .add(new Paragraph(leaseContract.getOwner().getLastName() + " " + leaseContract.getOwner().getFirstName()))
                    .add(new Paragraph(leaseContract.getOwner().getAddress().getStreet() + " " + leaseContract.getOwner().getAddress().getAdditionalAddress()))
                    .add(new Paragraph(leaseContract.getOwner().getAddress().getZipCode() + " " + leaseContract.getOwner().getAddress().getTown()));
            document.add(ownerDiv);

            // TENANT
            Div tenantDiv = new Div()
                    .setMarginTop(20)
                    .setPaddingLeft(10)
                    .setMarginLeft(350)
                    .add(new Paragraph("Locataire : ").setBold())
                    .add(new Paragraph(leaseContract.getTenant().getLastName() + " " + leaseContract.getTenant().getFirstName()))
                    .add(new Paragraph(leaseContract.getTenant().getAddress().getStreet() + " " + leaseContract.getTenant().getAddress().getAdditionalAddress()))
                    .add(new Paragraph(leaseContract.getTenant().getAddress().getZipCode() + " " + leaseContract.getTenant().getAddress().getTown()));
            document.add(tenantDiv);

            // PROPERTY
            Div propertyDiv = new Div()
                    .setMarginTop(50)
                    .setMarginBottom(25)
                    .add(new Paragraph("Bien concerné : ").setBold())
                    .add(new Paragraph(leaseContract.getProperty().getType() + " "
                            + leaseContract.getProperty().getArea() + "m2, composée de "
                            + leaseContract.getProperty().getRoomsNumber() + " pièces, située au "
                            + leaseContract.getProperty().getAddress().getStreet() + " "
                            + leaseContract.getProperty().getAddress().getAdditionalAddress() + " "
                            + leaseContract.getProperty().getAddress().getZipCode() + " "
                            + leaseContract.getProperty().getAddress().getTown()));
            document.add(propertyDiv);

            // PAYMENT
            Table paymentTable = new Table(new float[]{1, 1}).useAllAvailableWidth();
            paymentTable.addCell(new Cell().add(new Paragraph("Date du paiement ").setBold()));
            paymentTable.addCell(new Cell().add(new Paragraph("Montant du paiement ").setBold()));
            //paymentTable.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getRentPaymentDate()))));
            Date date = payment.getRentPaymentDate();
            LocalDate paymentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            paymentTable.addCell(new Cell().add(new Paragraph(paymentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))));

            if (leaseContract.getRentCharges() != null) {
                paymentTable.addCell(new Cell().add(new Paragraph(payment.getPaidRent() + " € dont " + leaseContract.getRentCharges() + "€ de charges inclues")));
            } else {
                paymentTable.addCell(new Cell().add(new Paragraph(payment.getPaidRent() + " € ")));
            }
            document.add(paymentTable);

            // DATE OF THE DAY
            LocalDate currentDate = LocalDate.now();
            Paragraph currentDateParagraph = new Paragraph("Fait le " + currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .setMarginTop(25);
            document.add(currentDateParagraph);

            // LEGACY
            Div leagcyDiv = new Div()
                    .add(new Paragraph("Cette quittance annule tous les reçus qui auraient pu être donnés pour " +
                            "acompte versé sur le présent terme, même si ces reçus portent une date postérieure à la " +
                            "date ci contre. Le paiement de la présente quittance n'emporte pas " +
                            "présomption de paiement des termes antérieurs.")).setMarginTop(20);
            document.add(leagcyDiv);

            // SIGNATURE
            Image signatureImage = new Image(ImageDataFactory.create("target/classes/static/Signature.png"))
                    .setWidth(150)
                    .setHeight(150)
                    .setMarginTop(10)
                    .setMarginLeft(350);
            document.add(signatureImage);

            // CLOSE
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}

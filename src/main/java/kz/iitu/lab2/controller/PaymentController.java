package kz.iitu.lab2.controller;

import kz.iitu.lab2.dtos.CommentDTO;
import kz.iitu.lab2.dtos.PurchaseRequest;
import kz.iitu.lab2.dtos.ReviewDTO;
import kz.iitu.lab2.entity.Comment;
import kz.iitu.lab2.entity.Payment;
import kz.iitu.lab2.entity.Review;
import kz.iitu.lab2.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(createdPayment);
    }

    @PostMapping("/{productId}/buy/{buyerId}")
    public ResponseEntity<String> buyProduct(
            @PathVariable Long productId,
            @PathVariable Long buyerId,
            @RequestBody PurchaseRequest purchaseRequest) {
        boolean isPurchased = paymentService.buyProduct(buyerId, productId, purchaseRequest);
        return isPurchased ?
                ResponseEntity.ok("Product purchased successfully")
                :
                ResponseEntity.badRequest().body("Purchase failed. Insufficient funds or product not available");
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}

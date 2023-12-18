package kz.iitu.lab2.service;

import kz.iitu.lab2.dtos.CommentDTO;
import kz.iitu.lab2.dtos.PurchaseRequest;
import kz.iitu.lab2.dtos.ReviewDTO;
import kz.iitu.lab2.entity.*;
import kz.iitu.lab2.repository.BuyerRepository;
import kz.iitu.lab2.repository.PaymentRepository;
import kz.iitu.lab2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    private final ReviewService reviewService;
    private final CommentService commentService;

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public boolean buyProduct(Long buyerId, Long productId, PurchaseRequest purchaseRequest) {
        Buyer buyer = buyerRepository.findById(buyerId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        CommentDTO comment = purchaseRequest.getComment();
        ReviewDTO review = purchaseRequest.getReview();

        if (buyer != null && product != null && !product.isSold()) {
            boolean isPaymentSuccessful = createPaymentForProduct(buyer, product);

            if (isPaymentSuccessful) {
                // Обновление баланса покупателя
                buyer.setBalance(buyer.getBalance() - product.getPrice());
                buyerRepository.save(buyer);

                // Изменение статуса продукта на "продано"
                product.setSold(true);
                product.setBuyer(buyer);
                productRepository.save(product);

                if (review != null) {
                    Review newReview = new Review();
                    newReview.setBuyer(buyer);
                    newReview.setProduct(product);
                    newReview.setComment(review.getComment());
                    newReview.setRating(review.getRating());
                    reviewService.addReview(newReview);
                }

                if (comment != null) {
                    Comment newComment = new Comment();
                    newComment.setBuyer(buyer);
                    newComment.setProduct(product);
                    newComment.setCommentText(comment.getCommentText());
                    newComment.setDate(new Date());
                    commentService.addComment(newComment);
                }

                return true;
            }
        }

        return false; // Недостаточно средств, продукт не найден или уже продан
    }

    public boolean createPaymentForProduct(Buyer buyer, Product product) {
        if (buyer.getBalance() >= product.getPrice()) {
            // Создание записи о платеже
            Payment payment = new Payment();
            payment.setBuyer(buyer);
            payment.setProduct(product);
            payment.setAmount(product.getPrice());
            payment.setDate(new Date());
            paymentRepository.save(payment);

            return true; // Успешное создание платежа
        }

        return false; // Недостаточно средств у покупателя
    }
}

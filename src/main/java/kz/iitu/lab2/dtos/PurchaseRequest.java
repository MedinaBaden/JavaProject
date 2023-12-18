package kz.iitu.lab2.dtos;

import lombok.Data;

@Data
public class PurchaseRequest {
    private ReviewDTO review;
    private CommentDTO comment;
}

package kz.iitu.lab2.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private String commentText;
    private Date date;
}

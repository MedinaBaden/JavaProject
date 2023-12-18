package kz.iitu.lab2.service;

import kz.iitu.lab2.entity.Comment;
import kz.iitu.lab2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }
}

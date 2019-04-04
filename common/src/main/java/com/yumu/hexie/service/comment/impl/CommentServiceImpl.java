package com.yumu.hexie.service.comment.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.commonsupport.comment.Comment;
import com.yumu.hexie.model.commonsupport.comment.CommentRepository;
import com.yumu.hexie.service.comment.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Inject
	private CommentRepository commentRepository;

    @Override
    public Comment comment(int orderType, long orderId, Comment comment) {
        comment.setOrderType(orderType);
        comment.setOrderId(orderId);
        return commentRepository.save(comment);
    }

	

}

package com.yumu.hexie.service.comment;

import com.yumu.hexie.model.commonsupport.comment.Comment;

public interface CommentService {

    public Comment comment(int orderType,long orderId,Comment comment);

}

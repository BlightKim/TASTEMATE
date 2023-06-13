package com.tastemate.domain.board;

public class BoardVOWithCommentVOList extends BoardVO {
    private java.util.List<com.tastemate.domain.comment.CommentVO> commentVOList;

    public java.util.List<com.tastemate.domain.comment.CommentVO> getCommentVOList() {
        return commentVOList;
    }

    public void setCommentVOList(java.util.List<com.tastemate.domain.comment.CommentVO> commentVOList) {
        this.commentVOList = commentVOList;
    }
}
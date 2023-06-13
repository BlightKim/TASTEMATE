package com.tastemate.handlers;

import com.tastemate.domain.board.BoardStatus;
import com.tastemate.domain.comment.CommentStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(CommentStatus.class)
public class CommentStatusHandler extends BaseTypeHandler<CommentStatus> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i,
      CommentStatus commentStatus, JdbcType jdbcType) throws SQLException {
    ps.setString(i, commentStatus.getCommentStatusCode());
  }

  @Override
  public CommentStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String code = rs.getString(columnName);
    return getCodeEnum(code);
  }

  @Override
  public CommentStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
    return null;
  }

  @Override
  public CommentStatus getNullableResult(CallableStatement callableStatement, int i)
      throws SQLException {
    return null;
  }
  private CommentStatus getCodeEnum(String commentStatusCode) {
    if(commentStatusCode == null) {
      return CommentStatus.등록;
    }
    switch (commentStatusCode) {
      case "등록":
        return CommentStatus.등록;
      case "삭제":
        return CommentStatus.삭제;
    }
    return CommentStatus.등록;
  }
}

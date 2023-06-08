package com.tastemate.handlers;

import com.tastemate.domain.board.BoardStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(BoardStatus.class)
public class BoardStatusHandler extends BaseTypeHandler<BoardStatus> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i,
      BoardStatus boardStatus, JdbcType jdbcType) throws SQLException {
    ps.setString(i, boardStatus.getBoardStatusCode());
  }

  @Override
  public BoardStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String code = rs.getString(columnName);
    return getCodeEnum(code);
  }

  @Override
  public BoardStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String code = rs.getString(columnIndex);
    return getCodeEnum(code);
  }

  @Override
  public BoardStatus getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    String code = cs.getString(columnIndex);
    return getCodeEnum(code);
  }

  private BoardStatus getCodeEnum(String boardStatusCode) {
    if(boardStatusCode == null) {
      return BoardStatus.등록;
    }
    switch (boardStatusCode) {
      case "등록":
        return BoardStatus.등록;
      case "삭제":
        return BoardStatus.삭제;
    }
    return BoardStatus.등록;

  }
}

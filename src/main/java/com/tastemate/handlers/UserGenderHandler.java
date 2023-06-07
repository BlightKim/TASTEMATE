package com.tastemate.handlers;

import com.tastemate.domain.member.UserGender;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class UserGenderHandler extends BaseTypeHandler<UserGender> {

  @Override
  public void setNonNullParameter(PreparedStatement pstmt, int i,
      UserGender userGender, JdbcType jdbcType) throws SQLException {
    pstmt.setInt(i, userGender.getGenderCode());
  }

  @Override
  public UserGender getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return UserGender.valueOf(rs.getInt(columnName));
  }

  @Override
  public UserGender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return UserGender.valueOf(rs.getInt(columnIndex));
  }

  @Override
  public UserGender getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    return UserGender.valueOf(cs.getInt(columnIndex));
  }
}

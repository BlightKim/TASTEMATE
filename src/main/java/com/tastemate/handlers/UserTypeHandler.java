package com.tastemate.handlers;

import com.tastemate.domain.member.UserType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(UserType.class)
public class UserTypeHandler extends BaseTypeHandler<UserType> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, UserType userType,
      JdbcType jdbcType) throws SQLException {
    ps.setInt(i, userType.getUserTypeCode());
  }

  @Override
  public UserType getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return UserType.valueOf(rs.getInt(columnName));
  }

  @Override
  public UserType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return UserType.valueOf(rs.getInt(columnIndex));
  }

  @Override
  public UserType getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    return UserType.valueOf(cs.getInt(columnIndex));
  }
}

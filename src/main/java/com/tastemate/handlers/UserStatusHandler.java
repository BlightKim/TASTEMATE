package com.tastemate.handlers;

import com.tastemate.domain.member.UserStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(UserStatus.class)
public class UserStatusHandler extends BaseTypeHandler<UserStatus> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i,
      UserStatus userStatus, JdbcType jdbcType) throws SQLException {
    ps.setInt(i, userStatus.getUserStatusCode());
  }

  @Override
  public UserStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return UserStatus.valueOf(rs.getInt(columnName));
  }

  @Override
  public UserStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return UserStatus.valueOf(rs.getInt(columnIndex));
  }

  @Override
  public UserStatus getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    return UserStatus.valueOf(cs.getInt(columnIndex));
  }

}

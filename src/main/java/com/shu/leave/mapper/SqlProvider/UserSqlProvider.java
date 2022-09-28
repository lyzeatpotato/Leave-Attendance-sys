package com.shu.leave.mapper.SqlProvider;

import com.shu.leave.entity.UserExample;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {

    public String selectByExample(UserExample user) {
        SQL sql = new SQL();
        if (user != null && user.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("username");
        sql.SELECT("yuanxi");
        sql.SELECT("ptype");
        sql.SELECT("pstatus");
        sql.SELECT("password");
        sql.SELECT("email");
        sql.SELECT("telephone");
        sql.SELECT("role");
        sql.SELECT("is_deleted");
        sql.SELECT("gmt_create");
        sql.SELECT("gmt_modified");
        sql.WHERE("is_deleted=0");      // 仅查询未被逻辑删除的信息
        sql.FROM("user_info");

        if (user != null && user.getOrderByClause() != null) {
            sql.ORDER_BY(user.getOrderByClause());
        }

        return sql.toString();
    }
}
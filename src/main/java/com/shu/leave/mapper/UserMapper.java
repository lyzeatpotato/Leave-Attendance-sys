package com.shu.leave.mapper;

import com.shu.leave.entity.User;
import com.shu.leave.entity.UserExample;
import com.shu.leave.mapper.SqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMapper {

    /**
     * 新增一条用户数据（插入除id以外完整的用户信息）
     * @param user
     * @return 返回新增用户的主键id值
     */
    @Insert({
            "insert into user_info",
            "(userid, username, yuanxi, ptype, pstatus, password, email, telephone, role, is_deleted, gmt_create, gmt_modified)",
            "values (#{userId,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR}, #{yuanXi,jdbcType=VARCHAR}, #{pType,jdbcType=VARCHAR},",
            "#{pStatus,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{telephone,jdbcType=VARCHAR},",
            "#{role,jdbcType=CHAR}, #{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=DATE}, #{gmtModified,jdbcType=DATE})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    int insert(User user);

    /**
     * 根据主键直接删除用户（一般不用）
     * @param id
     * @return 删除是否成功
     */
    @Delete({
            "delete from user_info",
            "where id = #{id,jdbcType=BIGINT}"
    })
    boolean deleteById(Long id);

    /**
     * 根据主键逻辑删除用户
     * @param id
     * @return 被逻辑删除的用户id
     */
    @Update({
            "update user_info",
            "set is_deleted=1",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteLogicallyById(Long id);

    /**
     * 修改全部用户信息
     * @param id
     * @return 修改完成后的用户id
     */
    @Update({
            "update user_info",
            "set name = #{name,jdbcType=VARCHAR},",
            "title = #{title,jdbcType=VARCHAR},",
            "label = #{label,jdbcType=VARCHAR},",
            "avatar = #{avatar,jdbcType=LONGVARCHAR},",
            "introduction = #{introduction,jdbcType=LONGVARCHAR},",
            "story = #{story,jdbcType=LONGVARCHAR},",
            "video = #{video,jdbcType=LONGVARCHAR}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateUserById(User id);


    /**
     * 查询全部用户信息
     * @param User
     * @return 全部用户信息列表
     */
    @SelectProvider(type= UserSqlProvider.class, method="selectByExample")
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="username", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="yuanxi", property="yuanXi", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="ptype", property="pType", jdbcType=JdbcType.VARCHAR),
            @Result(column="pstatus", property="pStatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="telephone", property="telephone", jdbcType=JdbcType.VARCHAR),
            @Result(column="role", property="role", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.DATE),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.DATE),
    })
    List<User> selectAll(UserExample User);

    /**
     * 根据id查询用户信息
     * @param id
     * @return 单个用户的全部信息
     */
    @Select({
            "select",
            "userid, username, yuanxi, ptype, pstatus, password, email, telephone, role, is_deleted, gmt_create, gmt_modified",
            "from user_info",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="username", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="yuanxi", property="yuanXi", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="ptype", property="pType", jdbcType=JdbcType.VARCHAR),
            @Result(column="pstatus", property="pStatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="telephone", property="telephone", jdbcType=JdbcType.VARCHAR),
            @Result(column="role", property="role", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.DATE),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.DATE),
    })
    User selectById(Long id);

}

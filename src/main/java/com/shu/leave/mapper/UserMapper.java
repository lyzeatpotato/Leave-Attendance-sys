package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户工号，映射其在user表中的主键
     * @param userId
     * @return 用户主键id
     */
    @Select({
            "select id from user_info where userid=#{userId,jdbcType=VARCHAR}"
    })
    long getUserPrimaryKeyByUserId(String userId);

    /**
     * 新增一条用户数据（插入除id以外完整的用户信息）
     * @param user
     * @return 新增用户的主键id值
     */
    @Insert({
            "insert into user_info",
            "(userid, username, yuanxi, ptype, pstatus, gender, role, is_deleted, gmt_create, gmt_modified)",
            "values (#{userId,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR}, #{yuanXi,jdbcType=VARCHAR}, #{pType,jdbcType=VARCHAR},",
            "#{pStatus,jdbcType=VARCHAR}, #{gender,jdbcType=VARCHAR}, #{role,jdbcType=CHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    int insert(User user);

    /**
     * 修改一条用户数据
     * @param user
     * @return 修改用户的主键id值
     */
    @Update({
            "update user_info set",
            "userid = #{userId,jdbcType=VARCHAR}, username = #{userName,jdbcType=VARCHAR}, yuanxi = #{yuanXi,jdbcType=VARCHAR}, ptype = #{pType,jdbcType=VARCHAR},",
            "pstatus = #{pStatus,jdbcType=VARCHAR}, gender = #{gender,jdbcType=VARCHAR}, role = #{role,jdbcType=CHAR},",
            "gmt_create = #{gmtCreate,jdbcType=TIMESTAMP}, gmt_modified = #{gmtModified,jdbcType=TIMESTAMP} where id = #{id,jdbcType=VARCHAR}"
    })

    int update(User user);

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
     * @return 被逻辑删除的数据id
     */
    @Update({
            "update user_info",
            "set is_deleted=1",
            "where id = #{id,jdbcType=BIGINT} "
    })
    int deleteLogicallyById(Long id);

    /**
     * 修改全部用户信息
     * @param id
     * @return 修改完成后的用户id
     */
    @Update({
            "update user_info",
            "set userid = #{userId,jdbcType=VARCHAR},",
            "username = #{userName,jdbcType=VARCHAR},",
            "yuanxi = #{yuanXi,jdbcType=VARCHAR},",
            "ptype = #{pType,jdbcType=VARCHAR},",
            "pstatus = #{pStatus,jdbcType=VARCHAR},",
            "gender = #{gender,jdbcType=VARCHAR},",
            "role = #{role,jdbcType=CHAR}",
            "where id = #{id,jdbcType=BIGINT} and is_deleted = 0 "
    })
    int updateUserById(User id);


    /**
     * 查询全部用户信息
     * @return 全部用户信息列表
     */
    @Select({
            "select",
            "id, userid, username, yuanxi, ptype, pstatus, gender, role, is_deleted, gmt_create, gmt_modified",
            "from user_info where is_deleted = 0 AND role <> 0"
    })
    @Results(id = "userInfoMapper", value = {
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="username", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="yuanxi", property="yuanXi", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="ptype", property="pType", jdbcType=JdbcType.VARCHAR),
            @Result(column="pstatus", property="pStatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
            @Result(column="role", property="role", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    List<User> findAll();

    /**
     * 根据id查询用户信息
     * @param id
     * @return 单个用户的全部信息
     */
    @Select({
            "select",
            "id, userid, username, yuanxi, ptype, pstatus, gender, role, is_deleted, gmt_create, gmt_modified",
            "from user_info",
            "where id = #{id,jdbcType=BIGINT} AND is_deleted = 0"
    })
    @ResultMap(value = "userInfoMapper")
    User findById(Long id);


    /**
     * 根据userid查询用户信息
     * @param userid
     * @return 单个用户的全部信息
     */
    @Select({
            "select",
            "id, userid, username, yuanxi, ptype, pstatus, gender, role, is_deleted, gmt_create, gmt_modified",
            "from user_info",
            "where userid = #{userid,jdbcType=BIGINT} and is_deleted = 0 "
    })
    @ResultMap(value = "userInfoMapper")
    User findByUserid(String userid);
}

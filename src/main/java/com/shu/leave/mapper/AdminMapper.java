package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.Admin;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 新增一条管理员信息数据（插入除id以外完整的管理信息）
     * @param admin
     * @return 新增主键id值
     */
    @Insert({
            "insert into admin_info",
            "(userid, username, is_deleted, gmt_create, gmt_modified)",
            "values (#{userId,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR}",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Admin admin);

    /**
     * 根据主键直接删除管理数据（一般不用）
     * @param id
     * @return 删除是否成功
     */
    @Delete({
            "delete from admin_info",
            "where id = #{id,jdbcType=BIGINT}"
    })
    boolean deleteById(Long id);

    /**
     * 根据主键逻辑删除管理数据
     * @param id
     * @return 被逻辑删除的用户id
     */
    @Update({
            "update admin_info",
            "set is_deleted=1",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteLogicallyById(Long id);

    /**
     * 修改全部管理信息
     * @param id
     * @return 修改完成后的用户id
     */
    @Update({
            "update admin_info",
            "set userid = #{userId,jdbcType=VARCHAR},",
            "username = #{userName,jdbcType=VARCHAR},",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateAdminById(Admin id);


    /**
     * 查询全部管理信息
     * @param Admin
     * @return 全部管理信息列表
     */
    @Select({
            "select",
            "id, userid, username, is_deleted, gmt_create, gmt_modified",
            "from admin_info"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="username", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    List<Admin> selectAll(Admin Admin);

    /**
     * 根据id查询管理信息
     * @param id
     * @return 单个管理数据的全部信息
     */
    @Select({
            "select",
            "userid, username, is_deleted, gmt_create, gmt_modified",
            "from admin_info",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="username", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    Admin selectById(Long id);

}

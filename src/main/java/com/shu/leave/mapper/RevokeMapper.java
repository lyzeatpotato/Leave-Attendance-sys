package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.Revoke;
import com.shu.leave.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RevokeMapper extends BaseMapper<Revoke> {

    /**
     * 根据用户主键查询当前用户的全部销假记录
     * @author liyuanzhe
     * @param userId
     * @return 销假记录列表
     */
    @Select({
            "select tb_revoke.id,tb_revoke.formid,tb_revoke.revoke_submit_time,tb_revoke.revoke_report_time,",
            "tb_revoke.status,tb_revoke.department_status,tb_revoke.hr_status,tb_revoke.is_deleted,tb_revoke.is_deleted,tb_revoke.gmt_create,tb_revoke.gmt_modified",
            "from leave,tb_revoke",
            "where (leave.id = tb_revoke.formid and leave.userid = #{userId,jdbcType=BIGINT} and tb_revoke.is_deleted = 0 and tb_revoke.status != 3)"
    })
    @Results(id = "RevokeMapper", value = {
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="formid", property="formId", jdbcType=JdbcType.BIGINT),
            @Result(column="revoke_submit_time", property="revokeSubmitTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="revoke_report_time", property="revokeReportTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="department_status", property="departmentStatus", jdbcType=JdbcType.CHAR),
            @Result(column="hr_status", property="hrStatus", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType= JdbcType.TIMESTAMP),
            @Result(
                    column = "formid", property = "leave",  javaType = Leave.class,
                    one = @One(select = "com.shu.leave.mapper.LeaveMapper.findById")
            )
    })
    List<Revoke> findAllRevokeListByUserId(Long userId);
}

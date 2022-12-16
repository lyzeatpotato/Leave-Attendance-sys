package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.SchoolDepartment;
import com.shu.leave.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface SchoolDepartmentMapper extends BaseMapper<SchoolDepartment> {
    /**
     * liugai
     * 根据school_leader_id查询校领导对应分管部门信息
     * @param school_leader_id
     * @return 当前school_leader_id的校领导对应的分管部门信息
     */
    @Select({
            "SELECT id,school_leader_id,department " ,"from [school_department]" ,
                    "WHERE school_leader_id=#{chool_leader_id,jdbcType=BIGINT}  "
    })
    @Results( {
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="school_leader_id", property="school_leader_id", jdbcType=JdbcType.VARCHAR),
            @Result(column="department", property="department", jdbcType=JdbcType.VARCHAR),
    })
    List<SchoolDepartment> selectBySchoolLeaderId(String school_leader_id);

    /**
     * 查询全校校领导-部门对应数据以及校领导数据
     * @author liyuanzhe
     * @date 2022/12/15 17:15
     * @return List<SchoolDepartment>
     */
    @Select({
            "SELECT school_department.id,school_department.school_leader_id,school_department.department " ,
            "from user_info, school_department" ,
            "WHERE school_department.school_leader_id = user_info.id "
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="school_leader_id", property="school_leader_id", jdbcType=JdbcType.VARCHAR),
            @Result(column="department", property="department", jdbcType=JdbcType.VARCHAR),
            @Result(
                    column = "school_leader_id", property = "leaderUser", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<SchoolDepartment> selectAllSchoolDept();

}

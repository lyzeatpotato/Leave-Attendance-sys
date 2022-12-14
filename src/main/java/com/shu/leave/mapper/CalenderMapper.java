package com.shu.leave.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Calender;

import com.shu.leave.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface CalenderMapper extends BaseMapper<Calender> {

    /**
     * 新增一条校历假期表单数据（插入除id以外完整的校历假期信息）
     * @param calender
     * @return 新增假期表单数据的主键id值
     */
    @Insert({
            "insert into calender",
            "(adminid, holiday_name, holiday_start_date, holiday_end_date, description, ",
            "is_deleted, gmt_create, gmt_modified)",
            "values (#{adminId,jdbcType=VARCHAR}, #{holidayName,jdbcType=VARCHAR}, #{holidayStartDate,jdbcType=VARCHAR}, #{holidayEndDate,jdbcType=VARCHAR},",
            "#{description,jdbcType=VARCHAR}, #{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Calender calender);

    /**
     * 修改某条校历假期表单数据（给出除id以外完整的校历假期信息）
     * @param calender
     * @return 要修改的假期表单数据的主键id值
     */
    @Update({
            "update calender set",
            "adminid = #{adminId,jdbcType=VARCHAR}, holiday_name = #{holidayName,jdbcType=VARCHAR}, holiday_start_date = #{holidayStartDate,jdbcType=VARCHAR}, holiday_end_date = #{holidayEndDate,jdbcType=VARCHAR},",
            "description = #{description,jdbcType=VARCHAR}, gmt_create = #{gmtCreate,jdbcType=TIMESTAMP}, gmt_modified = #{gmtModified,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=VARCHAR}"
    })
    int update (Calender calender);

    /**
     * 根据主键逻辑删除假期数据
     * @param id
     * @return 被逻辑删除的假期数据id
     */
    @Update({
            "update calender",
            "set is_deleted=1",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteLogicallyById(Long id);

    /**
     * 根据主键完全删除假期数据
     * @param id
     * @return 被逻辑删除的假期数据id
     */
    @Delete({
            "delete from calender",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteById(Long id);

    /**
     * 查询全部校历信息
     *
     * @return 全部用户信息列表
     */
    @Select({
            "select",
            "id, adminid, holiday_name, holiday_start_date, holiday_end_date, description",
            "from calender where is_deleted = 0 and year(holiday_start_date)=#{year,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="adminid", property="adminId", jdbcType=JdbcType.VARCHAR),
            @Result(column="holiday_name", property="holidayName", jdbcType=JdbcType.VARCHAR),
            @Result(column="holiday_start_date", property="holidayStartDate", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="holiday_end_date", property="holidayEndDate", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
//            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
//            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
//            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    List<Calender> selectAll(Long year);

    /**
     *author：王仕杰
     * 假期查询代码简化
     */
    /**
     * 查询在给定起止时间范围内是否存在有calender中假期名称描述为description的记录
     * @param leaveStartTime
     * @param leaveEndTime
     * @param description
     * @return 返回符合条件的'法定节假日'列表
     */
    @Select({
            "select id, adminid, holiday_name, holiday_start_date, holiday_end_date, description",
            "from calender where (holiday_start_date between #{leaveStartTime,jdbcType=TIMESTAMP} and #{leaveEndTime,jdbcType=TIMESTAMP})",
            "or (holiday_end_date between #{leaveStartTime,jdbcType=TIMESTAMP} and #{leaveEndTime,jdbcType=TIMESTAMP})",
            "and description = #{description,jdbcType=VARCHAR} and is_deleted = 0"
    })
    List<Calender> selectHolidayByStartEndTimeDiscribtionInner(Date leaveStartTime, Date leaveEndTime, String description);


    /**
     * 查询给定的时间范围是否包含在某条calender的假期名称描述为description的记录
     * @param leaveStartTime
     * @param leaveEndTime
     * @param description
     * @return 返回符合条件的'法定节假日'列表
     */
    @Select({
            "select id, adminid, holiday_name, holiday_start_date, holiday_end_date, description",
            "from calender where (holiday_start_date <= #{leaveStartTime,jdbcType=TIMESTAMP})",
            "and (holiday_end_date >= #{leaveEndTime,jdbcType=TIMESTAMP})",
            "and description = #{description,jdbcType=VARCHAR} and is_deleted = 0"
    })
    List<Calender> selectHolidayByStartEndTimeDiscribtionContainer(Date leaveStartTime, Date leaveEndTime, String description);
}

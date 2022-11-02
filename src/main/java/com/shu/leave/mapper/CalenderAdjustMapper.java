package com.shu.leave.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Calender;
import com.shu.leave.entity.CalenderAdjust;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface CalenderAdjustMapper extends BaseMapper<CalenderAdjust>{

    /**
     * 按照假期id查询对应的假期调休信息
     *
     * @return id对应的假期调休信息
     */
    @Select({
            "select",
            "id, calenderid, holiday_name, holiday_start_date, holiday_end_date",
            "from calender_leave_adjust where is_deleted = 0 ",
            "and calenderid = #{calenderId,jdbcType=VARCHAR} and holiday_name = #{holidayName, jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="calenderid", property="calenderId", jdbcType=JdbcType.VARCHAR),
            @Result(column="holiday_name", property="holidayName", jdbcType=JdbcType.VARCHAR),
            @Result(column="holiday_start_date", property="holidayStartDate", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="holiday_end_date", property="holidayEndDate", jdbcType=JdbcType.VARCHAR),
//            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
//            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
//            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
//            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    List<CalenderAdjust> selectAdjustById(Long calenderId, String holidayName);
}

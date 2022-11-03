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
     * author:王仕杰
     * 按照假期id查询对应的假期调休信息
     * @return id对应的假期调休信息
     */
    @Select({
            "select",
            "id, calender_id, adjust_name, adjust_start_date, adjust_end_date",
            "from calender_leave_adjust where is_deleted = 0 ",
            "and calender_id = #{calenderId,jdbcType=VARCHAR} and adjust_name = #{adjustName, jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="calender_id", property="calenderId", jdbcType=JdbcType.VARCHAR),
            @Result(column="adjust_name", property="adjustName", jdbcType=JdbcType.VARCHAR),
            @Result(column="adjust_start_date", property="adjustStartDate", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="adjust_end_date", property="adjustEndDate", jdbcType=JdbcType.VARCHAR),
//            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
//            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
//            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
//            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    List<CalenderAdjust> selectAdjustById(Long calenderId, String adjustName);

    /**
     * author:王仕杰
     * 按照主键id修改调休信息
     * @return 修改的id号
     */
    @Update({
            "update calender_leave_adjust set",
            "calender_id = #{calenderId,jdbcType=VARCHAR}, adjust_name = #{adjustName,jdbcType=VARCHAR}, adjust_start_date = #{adjustStartDate,jdbcType=VARCHAR}, adjust_end_date = #{adjustEndDate,jdbcType=VARCHAR},",
            "gmt_modified = #{gmtModified,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateAdjustById (CalenderAdjust calenderAdjust);

    /**
     * author：王仕杰
     * 根据主键逻辑删除调休数据
     * @param id
     * @return 被逻辑删除的调休数据主键id
     */
    @Update({
            "update calender_leave_adjust",
            "set is_deleted=1",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteLogicallyById(Long id);

    /**
     * author：王仕杰
     * 新增一条数据（插入除id以外完整的校历假期信息）
     * @param calenderAdjust
     * @return 新增假期表单数据的主键id值
     */
    @Insert({
            "insert into calender_leave_adjust",
            "(calender_id, adjust_name, adjust_start_date, adjust_end_date, ",
            "is_deleted, gmt_create, gmt_modified)",
            "values (#{calenderId,jdbcType=VARCHAR}, #{adjustName,jdbcType=VARCHAR}, #{adjustStartDate,jdbcType=VARCHAR}, #{adjustEndDate,jdbcType=VARCHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    int insertAdjust(CalenderAdjust calenderAdjust);
}

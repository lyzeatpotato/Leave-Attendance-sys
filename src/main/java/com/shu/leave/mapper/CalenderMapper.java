package com.shu.leave.mapper;


import com.shu.leave.entity.Calender;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface CalenderMapper {

    /**
     * 新增一条校历假期表单数据（插入除id以外完整的校历假期信息）
     * @param calender
     * @return 新增假期表单数据的主键id值
     */
    @Insert({
            "insert into calender",
            "(adminid, holiday_name, holiday_start_name, holiday_end_date, description",
            "is_deleted, gmt_create, gmt_modified)",
            "values (#{adminid,jdbcType=VARCHAR}, #{holiday_name,jdbcType=VARCHAR}, #{holiday_start_name,jdbcType=VARCHAR}, #{holiday_end_date,jdbcType=VARCHAR},",
            "#{description,jdbcType=VARCHAR}, #{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Calender calender);
}

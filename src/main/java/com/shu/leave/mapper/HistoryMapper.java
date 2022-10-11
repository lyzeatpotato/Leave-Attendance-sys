package com.shu.leave.mapper;

import com.shu.leave.entity.History;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper {

    /**
     * 新增一条用户请假记录
     * @param history
     * @return 插入成功返回True，插入失败返回False
     */
    @Insert({
            "insert into absence_history",
            "(userid, year, month, shijia_days, bingjia_days, hunjia_days, tanqinjia_days,",
            " sangjia_days, gongshangjia_days, kuanggong_days, inactive_days, is_deleted, gmt_create, gmt_modified)",
            "values (#{userId,jdbcType=VARCHAR}, #{year,jdbcType=VARCHAR}, #{month,jdbcType=VARCHAR}, #{shijiaDays,jdbcType=VARCHAR},",
            "#{bingjiaDays,jdbcType=VARCHAR}, #{hunjiaDays,jdbcType=VARCHAR}, #{tanqinjiaDays,jdbcType=VARCHAR}, #{sangjiaDays,jdbcType=CHAR},",
            "#{gongshangjiaDays,jdbcType=CHAR}, #{kuanggongDays,jdbcType=CHAR}, #{inactiveDays,jdbcType=CHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    boolean insert(History history);
}

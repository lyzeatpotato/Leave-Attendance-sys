package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.History;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface HistoryMapper extends BaseMapper<History> {

    /**
     * 新增一条用户请假记录
     * @param history
     * @return 插入成功返回True，插入失败返回False
     */
    @Insert({
            "insert into absence_history",
            "(userid, year, month, shijia_days, bingjia_days, hunjia_days, shengyujia_days, tanqinjia_days,",
            " sangjia_days, gongshangjia_days, gongchai_days, kuanggong_days, inactive_days, is_deleted, gmt_create, gmt_modified)",
            "values (#{userId,jdbcType=VARCHAR}, #{year,jdbcType=VARCHAR}, #{month,jdbcType=VARCHAR}, #{shijiaDays,jdbcType=VARCHAR},",
            "#{bingjiaDays,jdbcType=VARCHAR}, #{hunjiaDays,jdbcType=VARCHAR}, #{shengyujiaDays,jdbcType=VARCHAR}, #{tanqinjiaDays,jdbcType=VARCHAR}, #{sangjiaDays,jdbcType=CHAR},",
            "#{gongshangjiaDays,jdbcType=CHAR}, #{gongchaiDays,jdbcType=VARCHAR}, #{kuanggongDays,jdbcType=CHAR}, #{inactiveDays,jdbcType=CHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    int addHistory(History history);

    /**
     * 根据年月查询当前记录数
     * @param userid
     * @param month
     * @param year
     * @return 某一用户在某年某月下的考勤情况
     */
    @Select({
            "select id, userid, shijia_days, bingjia_days, hunjia_days, shengyujia_days, tanqinjia_days, sangjia_days, gongshangjia_days, gongchai_days, kuanggong_days, inactive_days",
            "from absence_history where is_deleted=0 and userid=#{userid,jdbcType=BIGINT} and month=#{month,jdbcType=VARCHAR} and year=#{year,jdbcType=VARCHAR}"
    })
    History selectWithMonthYear(long userid, String month, String year);


    /**
     * 根据用户工号查看某教师的全部考勤信息
     * @param userid
     * @return 当前工号的全部考勤信息
     */
    @Select({
            "select userid, year, month, shijia_days, bingjia_days, hunjia_days, shengyujia_days, tanqinjia_days,",
            "sangjia_days, gongshangjia_days, gongchai_days, kuanggong_days, inactive_days, is_deleted, gmt_create, gmt_modified",
            "from absence_history where is_deleted = 0 and userid=#{userid,jdbcType=BIGINT}"
    })
    @Results(id = "HistoryMapper", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "year", property = "year", jdbcType = JdbcType.VARCHAR),
            @Result(column = "month", property = "month", jdbcType = JdbcType.VARCHAR),
            @Result(column = "shijia_days", property = "shijiaDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bingjia_days", property = "bingjiaDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hunjia_days", property = "hunjiaDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "shengyujia_days", property = "shengyujiaDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tanqinjia_days", property = "tanqinjiaDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sangjia_days", property = "sangjiaDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gongshangjia_days", property = "gongshangjiaDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gongchai_days", property = "gongchaiDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "kuanggong_days", property = "kuanggongDays", jdbcType = JdbcType.VARCHAR),
            @Result(column = "inactive_days", property = "inactiveDays", jdbcType = JdbcType.VARCHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    List<History> selectWithUserId(long userid);
}

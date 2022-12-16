package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.History;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
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
            "select id, userid, year, month, shijia_days, bingjia_days, hunjia_days, shengyujia_days, tanqinjia_days, sangjia_days, gongshangjia_days, gongchai_days, kuanggong_days, inactive_days",
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
            "from absence_history where is_deleted = 0 and userid=#{userid,jdbcType=BIGINT} order by year DESC, month ASC"
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

    /**
     * 根据工号查询某用户在某年度某请假类型所对应的总天数
     * @param userId
     * @param leaveType
     * @param year
     * @return 用户某年度下某请假类型的总天数
     */
    @Select({
            "select sum(${leaveType}) from absence_history ",
            "where userid=#{userId,jdbcType=BIGINT} and year=#{year,jdbcType=VARCHAR}"
    })
    int selectByTypeYear(Long userId, String leaveType, String year);

    /**
     * 按部门查询: 查询某一部门下的全部请假记录
     * @param department
     * @return 返回请假列表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, ",
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, ",
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified ",
            "FROM leave, user_info ",
            "WHERE leave.userid = user_info.id and leave.status = 1",
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR}",
            "ORDER BY leave.id DESC",
    })
    @Results(id = "leaveDeptRelatedMapper", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    Page<Leave> selectPaginByUserDept(Page<Leave> page, String department);

    /**
     * 分页查询请假起止时间包含指定日期的请假数据
     * @author liyuanzhe
     * @date 2022/12/15 16:02
     * @param page
     * @param department
     * @return Page<Leave>
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, ",
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, ",
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified ",
            "FROM leave, user_info ",
            "WHERE leave.userid = user_info.id and leave.status = 1",
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} ",
            "and leave.leave_start_time <= #{nowDate, jdbcType=TIMESTAMP} and leave.leave_end_time >= #{nowDate, jdbcType=TIMESTAMP}",
            "ORDER BY leave.id DESC",
    })
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPaginByNowDateDept(Page<Leave> page, String department, Date nowDate);
}

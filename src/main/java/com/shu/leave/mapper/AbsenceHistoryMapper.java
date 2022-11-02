package com.shu.leave.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AbsenceHistoryMapper {

    /**
     * 查询某个用户某年事假累积天数
     * @author xieyuying
     * @param userId 用户id
     * @param year 年份
     * @return
     */
    @Select("select shijia_days from absence_history where userid = #{userId} and year = #{year}")
    int selectShiJiaDaysByUidAndYear(@Param("userId") Long userId, @Param("year") String year);

    /**
     * 查询某个用户某年病假累积天数
     * @author xieyuying
     * @param userId 用户id
     * @param year 年份
     * @return
     */
    @Select("select bingjia_days from absence_history where userid = #{userId} and year = #{year}")
    int selectBingJiaDaysByUidAndYear(@Param("userId") Long userId, @Param("year") String year);

}

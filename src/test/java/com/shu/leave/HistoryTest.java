package com.shu.leave;

import com.shu.leave.entity.History;
import com.shu.leave.mapper.HistoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName: HistoryTest
 * @Description: 测试考勤表相关接口
 * @author: lyz
 * @date: 2022 10 2022/10/18 9:47
 */

@SpringBootTest
public class HistoryTest {

    @Autowired
    HistoryMapper historyMapper;

    @Test
    public void testHistoryMethod() {
        //select userid, year, month, shijia_days, bingjia_days, hunjia_days, shengyujia_days, tanqinjia_days, sangjia_days, gongshangjia_days, gongchai_days,
        // kuanggong_days, inactive_days, is_deleted, gmt_create, gmt_modified from absence_history where is_deleted = 0 and userid=?
        List<History> historyList = historyMapper.selectWithUserId(1L);
        historyList.forEach(System.out::println);
    }
}

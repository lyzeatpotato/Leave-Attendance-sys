package com.shu.leave.service.Impl;

import com.shu.leave.entity.History;
import com.shu.leave.mapper.HistoryMapper;
import com.shu.leave.service.HistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Resource
    HistoryMapper historyMapper;

    @Override
    public int addUserLeaveHistory(String[] param) {
        History history = new History();
        Date date = new Date();
        LocalDate current_date = LocalDate.now();
        history.setUserId(Long.valueOf(param[0]));
        history.setYear(String.valueOf(current_date.getYear()));
        history.setMonth(String.valueOf(current_date.getMonth()));
        history.setShijiaDays(param[1]);
        history.setBingjiaDays(param[2]);
        history.setHunjiaDays(param[3]);
        history.setShengyujiaDays(param[4]);
        history.setTanqinjiaDays(param[5]);
        history.setSangjiaDays(param[6]);
        history.setGongshangjiaDays(param[7]);
        history.setKuanggongDays(param[8]);
        history.setInactiveDays(param[11]);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        history.setGmtCreate(timeStamp);
        history.setGmtModified(timeStamp);
        return historyMapper.insert(history);
    }
}

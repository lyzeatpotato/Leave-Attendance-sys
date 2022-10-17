package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.History;
import com.shu.leave.entity.Leave;
import com.shu.leave.mapper.HistoryMapper;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.mapper.UserMapper;
import com.shu.leave.service.LeaveService;
import com.shu.leave.utils.UnitedUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Resource
    UserMapper userMapper;

    @Resource
    LeaveMapper leaveMapper;

    @Resource
    HistoryMapper historyMapper;

    @Override
    public int addLeaveForm(String[] params) throws ParseException {
        Leave leaveForm = new Leave();
        long userPrimaryKey = userMapper.getUserPrimaryKeyByUserId(params[0]);
        leaveForm.setUserId(userPrimaryKey);
        leaveForm.setLeaveType(params[1]);  // 前端输入的请假类型
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd HH");
        Date startDate = df0.parse(params[2]);
        Date endDate = df0.parse(params[3]);
        int dayDiffer = UnitedUtils.getDayDiffer(startDate, endDate);   // 计算总计请假天数
        leaveForm.setLeaveStartTime(startDate);
        leaveForm.setLeaveEndTime(endDate);
        System.out.println(leaveForm.getLeaveStartTime());
        leaveForm.setLeaveReason(params[4]);
        leaveForm.setLeaveMaterial(params[5]);
        leaveForm.setStatus("0");
        // 进行当前请假信息状态的判别（需要进行到部门审核/人事处审核/校领导审核哪一个步骤）
        leaveForm.setDepartmentStatus("0");
        leaveForm.setHrStatus("2");
        leaveForm.setSchoolStatus("2");
        leaveForm.setIsDeleted("0");
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        leaveForm.setGmtCreate(timeStamp);
        leaveForm.setGmtModified(timeStamp);
        // 对考勤表的对应请假类型进行修改
        LocalDate current_date = LocalDate.now();
        try {
            History historyArr = historyMapper.selectWithMonthYear(Long.valueOf(params[0]), String.valueOf(current_date.getMonthValue()), String.valueOf(current_date.getYear()));
            // 若根据当前年月查询到了对应的用户记录，则根据请假类型对之修改
            int leaveTypeIndex = UnitedUtils.getLeaveTypeIndex(params[1]);
            UpdateWrapper<History> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", historyArr.getId());
            switch (params[1]) {
                case "事假":
                    String shijiaDays = historyArr.getShijiaDays();
                    int shijia = Integer.valueOf(shijiaDays) + dayDiffer;
                    updateWrapper.set("shijia_days", String.valueOf(shijia));
                    break;
                case "病假":
                    String bingjiaDays = historyArr.getBingjiaDays();
                    int bingjia = Integer.valueOf(bingjiaDays) + dayDiffer;
                    updateWrapper.set("bingjia_days", String.valueOf(bingjia));
                    break;
                case "婚假":
                    String hunjiaDays = historyArr.getHunjiaDays();
                    int hunjia = Integer.valueOf(hunjiaDays) + dayDiffer;
                    updateWrapper.set("hunjia_days", String.valueOf(hunjia));
                    break;
                case "产假":
                    String chanjiaDays = historyArr.getShengyujiaDays();
                    int chanjia = Integer.valueOf(chanjiaDays) + dayDiffer;
                    updateWrapper.set("shengyujia_days", String.valueOf(chanjia));
                    break;
                case "陪产假":
                    String peichanjiaDays = historyArr.getShengyujiaDays();
                    int peichanjia = Integer.valueOf(peichanjiaDays) + dayDiffer;
                    updateWrapper.set("shengyujia_days", String.valueOf(peichanjia));   // 产假与陪产假都归为生育假
                    break;
                case "探亲假":
                    String tanqinjiaDays = historyArr.getSangjiaDays();
                    int tanqinjia = Integer.valueOf(tanqinjiaDays) + dayDiffer;
                    updateWrapper.set("tanqinjia_days", String.valueOf(tanqinjia));
                    break;
                case "丧假":
                    String sangjiaDays = historyArr.getSangjiaDays();
                    int sangjia = Integer.valueOf(sangjiaDays) + dayDiffer;
                    updateWrapper.set("sangjia_days", String.valueOf(sangjia));
                    break;
                case "因公出差":
                    String gongchaiDays = historyArr.getGongchaiDays();
                    int gongchai = Integer.valueOf(gongchaiDays) + dayDiffer;
                    updateWrapper.set("gongchai_days", String.valueOf(gongchai));
                    break;
                case "工伤假":
                    String gongshangDays = historyArr.getGongshangjiaDays();
                    int gongshang = Integer.valueOf(gongshangDays) + dayDiffer;
                    updateWrapper.set("gongshangjia_days", String.valueOf(gongshang));
                    break;
            }
            historyMapper.update(null, updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            // 若根据当前年月查询不到对应用户的考勤记录，说明该用户本年本月度没有请假记录，则新增一条考勤记录
            History history = new History();
            history.setUserId(Long.valueOf(params[0]));
            history.setYear(String.valueOf(current_date.getYear()));
            history.setMonth(String.valueOf(current_date.getMonthValue()));
            history.setShijiaDays("0");
            history.setBingjiaDays("0");
            history.setHunjiaDays("0");
            history.setShengyujiaDays("0");
            history.setTanqinjiaDays("0");
            history.setSangjiaDays("0");
            history.setGongshangjiaDays("0");
            history.setGongchaiDays("0");
            history.setKuanggongDays("0");
            history.setInactiveDays("0");
            history.setIsDeleted("0");
            history.setGmtCreate(timeStamp);
            history.setGmtModified(timeStamp);
            switch (params[1]) {
                case "事假":
                    history.setShijiaDays(String.valueOf(dayDiffer));
                    break;
                case "病假":
                    history.setBingjiaDays(String.valueOf(dayDiffer));
                    break;
                case "婚假":
                    history.setHunjiaDays(String.valueOf(dayDiffer));
                    break;
                case "产假":
                    history.setShengyujiaDays(String.valueOf(dayDiffer));
                    break;
                case "陪产假":
                    history.setShengyujiaDays(String.valueOf(dayDiffer));   // 产假与陪产假都归为生育假
                    break;
                case "探亲假":
                    history.setTanqinjiaDays(String.valueOf(dayDiffer));
                    break;
                case "丧假":
                    history.setSangjiaDays(String.valueOf(dayDiffer));
                    break;
                case "因公出差":
                    history.setGongchaiDays(String.valueOf(dayDiffer));
                    break;
                case "工伤假":
                    history.setGongshangjiaDays(String.valueOf(dayDiffer));
                    break;
            }
            historyMapper.addHistory(history);
        }
        return leaveMapper.insert(leaveForm);
    }

    @Override
    public List<Leave> findAllLeaveForm() {
        return leaveMapper.selectAllLeave();
    }

    @Override
    public IPage<Leave> findAllLeaveFormPagination() {
        Page<Leave> page = new Page<>(0, 10);
        QueryWrapper<Leave> queryWrapper = new QueryWrapper<Leave>();
        queryWrapper.eq("is_deleted", "0");
        IPage iPage = leaveMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public Leave findLeaveFormById(Long id) {
        return leaveMapper.selectById(id);
    }

    @Override
    public List<Leave> findLeaveFormByUserid(Long userid) {
        return leaveMapper.selectByUserid(userid);
    }

    @Override
    public List<Leave> findLeaveFormByUserDept(String department) {
        return leaveMapper.selectByUserDept(department);
    }

    @Override
    public List<Leave> findLeaveFormByUserDeptAndUnfinishedHR(String department) {
        return leaveMapper.selectByUserDeptAndUnfinishedHR(department);
    }

    @Override
    public List<Leave> findLeaveFormByUserDeptAndUnfinishedSchool(String department) {
        return leaveMapper.selectByUserDeptAndUnfinishedSchool(department);
    }


    @Override
    public List<Leave> findAllLeaveFormByUnfinishedHR() {
        return leaveMapper.selectAllByUnfinishedHR();
    }
}

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
            History historyArr = historyMapper.selectWithMonthYear(Long.parseLong(params[0]), String.valueOf(current_date.getMonthValue()), String.valueOf(current_date.getYear()));
            // 若根据当前年月查询到了对应的用户记录，则根据请假类型对之修改
//            int leaveTypeIndex = UnitedUtils.getLeaveTypeIndex(params[1]);
            UpdateWrapper<History> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", historyArr.getId());
            switch (params[1]) {
                case "事假":
                    int shijia = historyArr.getShijiaDays() + dayDiffer;
                    updateWrapper.set("shijia_days", shijia);
                    break;
                case "病假":
                    int bingjia = historyArr.getBingjiaDays() + dayDiffer;
                    updateWrapper.set("bingjia_days", bingjia);
                    break;
                case "婚假":
                    int hunjia = historyArr.getHunjiaDays() + dayDiffer;
                    updateWrapper.set("hunjia_days", hunjia);
                    break;
                case "产假":
                    int chanjia = historyArr.getShengyujiaDays() + dayDiffer;
                    updateWrapper.set("shengyujia_days", chanjia);
                    break;
                case "陪产假":
                    int peichanjia = historyArr.getShengyujiaDays() + dayDiffer;
                    updateWrapper.set("shengyujia_days", peichanjia);   // 产假与陪产假都归为生育假
                    break;
                case "探亲假":
                    int tanqinjia = historyArr.getSangjiaDays() + dayDiffer;
                    updateWrapper.set("tanqinjia_days", tanqinjia);
                    break;
                case "丧假":
                    int sangjia = historyArr.getSangjiaDays() + dayDiffer;
                    updateWrapper.set("sangjia_days", sangjia);
                    break;
                case "因公出差":
                    int gongchai = historyArr.getGongchaiDays() + dayDiffer;
                    updateWrapper.set("gongchai_days", gongchai);
                    break;
                case "工伤假":
                    int gongshang = historyArr.getGongshangjiaDays() + dayDiffer;
                    updateWrapper.set("gongshangjia_days", gongshang);
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
            history.setShijiaDays(0);
            history.setBingjiaDays(0);
            history.setHunjiaDays(0);
            history.setShengyujiaDays(0);
            history.setTanqinjiaDays(0);
            history.setSangjiaDays(0);
            history.setGongshangjiaDays(0);
            history.setGongchaiDays(0);
            history.setKuanggongDays(0);
            history.setInactiveDays(0);
            history.setIsDeleted("0");
            history.setGmtCreate(timeStamp);
            history.setGmtModified(timeStamp);
            switch (params[1]) {
                case "事假":
                    history.setShijiaDays(dayDiffer);
                    break;
                case "病假":
                    history.setBingjiaDays(dayDiffer);
                    break;
                case "婚假":
                    history.setHunjiaDays(dayDiffer);
                    break;
                case "产假":
                    history.setShengyujiaDays(dayDiffer);
                    break;
                case "陪产假":
                    history.setShengyujiaDays(dayDiffer);   // 产假与陪产假都归为生育假
                    break;
                case "探亲假":
                    history.setTanqinjiaDays(dayDiffer);
                    break;
                case "丧假":
                    history.setSangjiaDays(dayDiffer);
                    break;
                case "因公出差":
                    history.setGongchaiDays(dayDiffer);
                    break;
                case "工伤假":
                    history.setGongshangjiaDays(dayDiffer);
                    break;
            }
            historyMapper.addHistory(history);
        }
        return leaveMapper.addLeave(leaveForm);
    }

    @Override
    public List<Leave> findAllLeaveForm() {
        return leaveMapper.selectAllLeave();
    }

    @Override
    public IPage findAllLeaveFormPagination() {
        Page<Leave> page = new Page<>(0, 10);
        QueryWrapper<Leave> queryWrapper = new QueryWrapper<Leave>();
        queryWrapper.eq("is_deleted", "0");
        IPage iPage = leaveMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public Leave findLeaveFormById(Long id) {
        return leaveMapper.findById(id);
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

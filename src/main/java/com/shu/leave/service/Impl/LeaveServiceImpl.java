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
import com.shu.leave.service.CalenderService;
import com.shu.leave.service.LeaveService;
import com.shu.leave.utils.UnitedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.shu.leave.vo.SingleLeaveStepVo;
import com.shu.leave.vo.SingleLeaveVo;
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

    @Autowired
    UserMapper userMapper;
    @Autowired
    LeaveMapper leaveMapper;
    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    CalenderService calenderService;

    @Override
    public int addLeaveForm(String[] params) throws ParseException {
        Leave leaveForm = new Leave();
        long userPrimaryKey = userMapper.getUserPrimaryKeyByUserId(params[0]);
        leaveForm.setUserId(userPrimaryKey);
        String leaveType = params[1];
        leaveForm.setLeaveType(leaveType);  // 前端输入的请假类型
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd HH");
        Date startDate = df0.parse(params[2]);
        Date endDate = df0.parse(params[3]);
        leaveForm.setLeaveStartTime(startDate);
        leaveForm.setLeaveEndTime(endDate);
        System.out.println(leaveForm.getLeaveStartTime());
        /**
         * 根据请假类型判断假期是否需要顺延，并根据校历信息对请假起止时间进行修改（暂未考虑补休）
         * 规则：1.病假遇寒暑假、公休日和法定节假日不顺延；
         *      2.事假、丧假遇到公休日和法定节假日顺延；
         *      3.婚假、产假、生育假与配偶陪产假遇寒暑假和法定节假日顺延。
         */

        int dayDiffer = 0;
        if (leaveType.equals("事假") || leaveType.equals("丧假")) {
            // 事假、丧假 判别公休日和法定节假日
            int holidayExtends = calenderService.totalExtendHolidays(startDate, endDate);   // 遇到法定节假日需要顺延的天数
            if (holidayExtends != -1) {     // =>此处判断不等于-1是确认用户选择的请假范围未被某一个假期范围所包含，如被包含则不记录请假时长(dayDiffer=0)
                // 请假天数=当前申请天数-遇到公休/法定节假日顺延的天数
                dayDiffer = UnitedUtils.getDayDiffer(startDate, endDate) - holidayExtends;
            }
        } else if (leaveType.equals("婚假") || leaveType.equals("产假") || leaveType.equals("陪产假")) {
            // 婚假、产假、陪产假 判别法定节假日和寒暑假
            int holidayExtends = calenderService.totalExtendHolidays(startDate, endDate);   // 遇到法定节假日需要顺延的天数
            int vocationExtends = calenderService.totalExtendVocation(startDate, endDate);  // 遇到寒暑假需要顺延的天数
            if (holidayExtends != -1 && vocationExtends != -1) {
                // 请假天数=当前申请天数-遇到法定节假日/寒暑假顺延的天数
                dayDiffer = UnitedUtils.getDayDiffer(startDate, endDate) - holidayExtends - vocationExtends;
            }
        }
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
            History historyArr = historyMapper.selectWithMonthYear(userPrimaryKey, String.valueOf(current_date.getMonthValue()), String.valueOf(current_date.getYear()));
            // 若根据当前年月查询到了对应的用户记录，则根据请假类型对之修改
//            int leaveTypeIndex = UnitedUtils.getLeaveTypeIndex(leaveType);
            UpdateWrapper<History> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", historyArr.getId());
            switch (leaveType) {
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
            history.setUserId(userPrimaryKey);
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
            switch (leaveType) {
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
    public List<Leave> findLeaveFormByUserid(String userid) {
        return leaveMapper.selectByUserid(userMapper.getUserPrimaryKeyByUserId(userid));
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

    @Override
    public SingleLeaveVo selectSingleLeave(String role, String yuanxi, long id){
        SingleLeaveVo singleLeaveVo = leaveMapper.selectSingleLeave(yuanxi,id);
        return singleLeaveVo;
    }
    @Override
    public SingleLeaveStepVo selectSingleLeaveStep(String role,long id,String step){
        if (step=="1") {
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else if (step=="2"){
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else if (step=="3"){
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else if (step=="4"){
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else {
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
    }
    /*
    审核工作
     */
    @Override
    public int singleLeaveAudit(String role, String userid, long id, String result, String recommend) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = df.format(System.currentTimeMillis());
        switch (role) {
            case "1": {
                leaveMapper.dpOfficerAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.dpOfficerAudity(id,time);
                else
                    leaveMapper.dpOfficerAuditn(id,time);
                break;
            }
            case "2": {
                leaveMapper.dpLeaderAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.dpLeaderAudity(id,time);
                else
                    leaveMapper.dpLeaderAuditn(id,time);
                break;
            }
            case "3": {
                leaveMapper.hrOfficerAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.hrOfficerAudity(id,time);
                else
                    leaveMapper.hrLeaderAuditn(id,time);
                break;
            }
            case "4": {
                leaveMapper.hrLeaderAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.hrLeaderAudity(id,time);
                else
                    leaveMapper.hrLeaderAuditn(id,time);
                break;
            }
            case "5": {
                leaveMapper.scLeaderAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.scLeaderAudity(id,time);
                else
                    leaveMapper.scLeaderAuditn(id,time);
                break;
            }
            default:
                return 0;
        }
        return 1;
    }
    @Override
    public List<Leave> findLeaveFormByUsername(String username) {
        return leaveMapper.selectByUsername(username);
    }
    @Override
    public List<Leave> findLeaveFormByUseridInDept(String userid,String department) {
        return leaveMapper.selectByUseridInDept(userid,department);
    }
    @Override
    public List<Leave> findLeaveFormByUsernameInDept(String username,String department) {
        return leaveMapper.selectByUsernameInDept(username,department);
    }
    @Override
    public List<Leave> findLeaveFormByUseridInHR(String userid) {
        return leaveMapper.selectByUseridInHR(userid);
    }
    @Override
    public List<Leave> findLeaveFormByUsernameInHR(String username) {
        return leaveMapper.selectByUsernameInHR(username);
    }
    @Override
    public List<Leave> findLeaveFormByUseridInSchool(String userid,String department) {
        return leaveMapper.selectByUseridInSchool(userid,department);
    }
    @Override
    public List<Leave> findLeaveFormByUsernameInSchool(String username,String department) {
        return leaveMapper.selectByUsernameInSchool(username,department);
    }
    @Override
    public List<Leave> findLeaveFormByUserDeptCheck(String department) {
        return leaveMapper.selectByUserDeptCheck(department);
    }

}


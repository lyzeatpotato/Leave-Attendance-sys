# Leave-Attendance-sys
人事请假-考勤系统后端

请注意拉取代码后在github上创建自己的分支，命名规则：姓名全拼小写字母（如：liyuanzhe）


## Spring-boot后端接口编写流程
Step1.根据数据库表格创建实体类(entity文件夹下)  
请注意java后端数据类型与SQLServer数据库字段类型的对应关系：  
  **java**----------->**sqlServer**  
  String--------->CHAR/VARCHAR/text  
  Long---------->bigint/int  
  Date---------->datetime
   
Step2.创建Mapper接口文件(mapper文件夹下)
 
Step3.创建Service接口文件和实现类(Service文件夹下)
 
Step4.创建Controller接口文件(Controller文件夹下)  
**相关注释**  
@ApiOperation(value = "接口名字", notes = "接口描述信息")  
@ApiOperationSupport(order = 1)（接口文档排列顺序）  
@GetMapping("在网址url上显示的接口名称")  
 
Step5.访问 <a>http://localhost:8007/leave-attendance/doc.html#/home</a> 查看接口文档

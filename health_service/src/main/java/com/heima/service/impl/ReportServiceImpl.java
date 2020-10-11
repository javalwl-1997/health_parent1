package com.heima.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.heima.dao.MemberDao;
import com.heima.dao.OrderDao;
import com.heima.health.utils.DateUtils;
import com.heima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/10/11 16:10<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String,Object> reportData =new HashMap<String,Object>();
        Date today =new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        //周一
        String monday = sdf.format(DateUtils.getFirstDayOfWeek(today));
        //周日
        String sunday = sdf.format(DateUtils.getLastDayOfWeek(today));
        //本月第一天
        String firstDayOfMonth = sdf.format(DateUtils.getFirstDayOfThisMonth());
        //本月第二天
        String lastDayOfMonth = sdf.format(DateUtils.getLastDayOfThisMonth());
        String reportDate = sdf.format(today);
        //+++++++++++++++++++++++++++++++++++++++++++++订单统计
        //今日预约
        int todayOrderNumber=orderDao.findOrderCountByToday(reportDate);
        //今日到诊
        int todayVisitsNumber=orderDao.findVisitsCountByToday(reportDate);
        //本周预约
        int thisWeekOrderNumber=orderDao.findOrderCountBetweenDate(monday,sunday);
        //本周到诊
        int thisWeekVisitsNumber=orderDao.findVisitsNumberBythisWeek(monday);
        //本月预约
        int thisMonthOrderNumber=orderDao.findOrderCountBetweenDate(firstDayOfMonth,lastDayOfMonth);
        //本月到诊
        int thisMonthVisitsNumber=orderDao.findVisitsNumberBythisMonth(firstDayOfMonth);
        //+++++++++++++++++++++++++++++++++++++++++++++会员数量统计
        //今日新增会员
        int todayNewNumber= memberDao.findMemberCountByToday(reportDate);
        //会员总数
        int totalMember=memberDao.findMemberTotalCount();
        //本周新增的会员
        int thisWeekNewMember=memberDao.findThisWeekNewMember(monday);
        //本月新增会员数
        int thisMonthNewMember=memberDao.findThisMonthNewMember(firstDayOfMonth);
        //+++++++++++++++++++++++++++++++++++++++++++++热门套餐
        List<Map<String,Object>> hotSetmeal =orderDao.findHotSetmeal();
        reportData.put("reportDate",reportDate);
        reportData.put("todayOrderNumber",todayOrderNumber);
        reportData.put("todayVisitsNumber",todayVisitsNumber);
        reportData.put("thisWeekOrderNumber",thisWeekOrderNumber);
        reportData.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        reportData.put("thisMonthOrderNumber",thisMonthOrderNumber);
        reportData.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        reportData.put("todayNewNumber",todayNewNumber);
        reportData.put("totalMember",totalMember);
        reportData.put("thisWeekNewMember",thisWeekNewMember);
        reportData.put("thisMonthNewMember",thisMonthNewMember);
        reportData.put("hotSetmeal",hotSetmeal);
        return reportData;
    }
}

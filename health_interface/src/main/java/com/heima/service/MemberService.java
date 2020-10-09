package com.heima.service;

import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/9/28 18:13<br/>
 *
 * @author Administrator<br />
 * @since JDK 1.8
 */
public interface MemberService {
    List<Integer> getMemberReport(List<String> months);

    List<Map<String, Object>> getMemberReportBySex();

    Map<String, Object> getMemberAge();
}

package com.example;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.domain.Task;
import com.example.domain.TaskUser;
import com.example.domain.User;
import com.example.dto.MajorDto;
import com.example.service.MajorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.util.MailSenderConstant.SUBJECT;

@SpringBootTest
class CollegeOfficeInfoSysDevApplicationTests {

    @Resource
    private MajorService majorService;

    @Test
    void contextLoads() {

        String dateStr = "2022-12-14 19:06:00";
        DateTime dt = DateUtil.parse(dateStr);

        LocalDateTime of = LocalDateTimeUtil.of(dt);

        LocalDateTime deadTime = LocalDateTimeUtil.offset(of, -2, ChronoUnit.HOURS);
        System.out.println(deadTime);
        Date date = DateUtil.parse(LocalDateTimeUtil.format(deadTime, DatePattern.NORM_DATETIME_FORMATTER));
        System.out.println(date);
        Timer timer = new Timer();
        TimerTask sc = new TimerTask() {
            @Override
            @Transactional
            public void run() {
                System.out.println("task run......");
            }
        };
        timer.schedule(sc,date);
    }

    @Test
    void testMajorAndUser() {
        MajorDto majorDto = new MajorDto();
        majorDto.setMajorName("智能科学");
        majorDto.setUsername("刘李庚");
        majorDto.setUserPosition("教研室主任");
        majorDto.setUserMail("2121289671@qq.com");
        majorDto.setUserSex("男");
        majorService.addMajorAndUser(majorDto);
    }
}

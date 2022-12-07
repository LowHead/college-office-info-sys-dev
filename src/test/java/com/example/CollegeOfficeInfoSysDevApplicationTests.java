package com.example;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.domain.Task;
import com.example.domain.TaskUser;
import com.example.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.util.MailSenderConstant.SUBJECT;

@SpringBootTest
class CollegeOfficeInfoSysDevApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime deadTime = LocalDateTimeUtil.offset(LocalDateTime.now(), 1, ChronoUnit.MICROS);

        Date date = DateUtil.parse(LocalDateTimeUtil.format(deadTime, DatePattern.NORM_DATETIME_FORMATTER));
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

}

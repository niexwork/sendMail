package com.niex.sendmail.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.power.common.util.OkHttp3Util;
import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Objects;

/**
 * 类说明
 * <p>
 *
 * @author niexiang
 * @version 1.0.0
 * @date 2019/7/24
 */
@Component
public class MonitorSchedule implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorSchedule.class);

    private static Integer count = 0;


    @Override
    public void afterPropertiesSet() throws Exception {
        OhMyEmail.config(OhMyEmail.SMTP_QQ(true), "*", "*");
    }

    @Scheduled(fixedRate = 60*1000)
    public void faceId() throws MessagingException {
        try {
            LOGGER.info("跑批开始-------------------------");
            String s = OkHttp3Util.syncGet("http://192.168.151.67:9810/health");
            LOGGER.info("响应信息：{}",s);
            JSONObject jsonObject = JSON.parseObject(s);
            String status = jsonObject.getString("status");
            if (Objects.equals(status,"UP") || count > 1) {
                return;
            }
            OhMyEmail.subject("人脸项目服务器挂了！！！")
                    .from("监控邮箱")
                    .to("*@163.com")
                    .text("快去重启吧")
                    .send();
            count++;
        } catch (SendMailException e) {
            e.printStackTrace();
        }

    }

}

package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Dai
 * @Date: 2024/04/28 18:00
 * @Description: EventProducer
 * @Version: 1.0
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fireEvent(Event event){
        //将事件发布到指定的主题(参数1：主题，参数2：事件对象的所有数据)
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));


    }
}

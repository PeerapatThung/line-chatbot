package com.chatbot.demo.linechatbot;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@LineMessageHandler
@Slf4j
public class LineEchoHandler {
    int state = 1;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
//        System.out.println("event: " + event);
//        return new TextMessage(event.getMessage().getText());
        if(state == 1){
            state = 2;
            redisTemplate.opsForHash().put("LineCache", event.getSource().getUserId(),state);
            return TextMessage.builder().text(event.getMessage().getText()).build();
        }else{
            state = 1;
            redisTemplate.opsForHash().put("LineCache", event.getSource().getUserId(),state);
            return TextMessage.builder().text("xx").build();
        }
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}

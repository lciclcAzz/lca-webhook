package com.lciclcazz.webhook.service;

import com.lciclcazz.webhook.config.Constants;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lciclcazz on 6/30/17.
 */

@Service
public class LineBotService {
    final Logger logger = LoggerFactory.getLogger(LineBotService.class);

    @Autowired
    private LineMessagingClient lineMessagingClient;

    public void pushText(@NonNull String message) throws IOException {
        pushText(Constants.TOKEN,message,Constants.LINE_UlciclcAzz);
    }

    public void pushText(@NonNull String token,@NonNull String message,@NonNull String to) throws IOException {
        logger.info("<<<< "+Thread.currentThread().getStackTrace()[1].getMethodName()+" >>>>");
        TextMessage textMessage = new TextMessage(message);
        PushMessage pushMessage = new PushMessage( to, textMessage );
        Response<BotApiResponse> response = LineMessagingServiceBuilder
                .create(token)
                .build()
                .pushMessage(pushMessage)
                .execute();
        logger.info("BotApiResponse : "+response.code() + " " + response.message());
    }

    public void pushTextContents(@NonNull String token, @NonNull HashMap message) throws IOException {
        String imageUrl = createUri("/static/buttons/1040.jpg");
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                new URIAction("Go to line.me",
                                        "https://line.me"),
                                new PostbackAction("Say hello1",
                                        "hello ")
                        )),
                        new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                new PostbackAction("hello2",
                                        "hello ",
                                        "hello "),
                                new MessageAction("Say message",
                                        "Rice=米")
                        ))
                ));
        TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
        this.reply(token, templateMessage);
    }

    public void pushTextContentsButton(@NonNull String token, @NonNull HashMap message) throws IOException {
        String imageUrl = createUri("/static/buttons/1040.png");
        ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
                imageUrl,
                "Push by : lciclcAzz",
                "commitId : "+message.get("commitId"),
                Arrays.asList(
                        new URIAction("Go to Project","lciclcAzz Project"),
                        new PostbackAction("Say hello1",
                                "hello "),
                        new PostbackAction("hello2",
                                " TIME : Timestamp",
                                "hello "),
                        new MessageAction("Say message", "STATUS : "+message.get("status"))
                ));
        TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
        this.push(token, templateMessage);

    }

    public String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().toUriString();
    }

    public void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    public void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
            logger.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public void push(@NonNull String token, @NonNull Message message) {
        push(token, Collections.singletonList(message));
    }

    public void push(@NonNull String token, @NonNull List<Message> messages) {
        try {
            PushMessage pushMessage = new PushMessage( Constants.LINE_UlciclcAzz, messages );
            Response<BotApiResponse> response = LineMessagingServiceBuilder
                    .create(token)
                    .build()
                    .pushMessage(pushMessage)
                    .execute();
            logger.info("BotApiResponse : "+response.code() + " " + response.message());

//            BotApiResponse apiResponse;
//            apiResponse = lineMessagingClient.pushMessage(new PushMessage(Constants.LINE_UlciclcAzz,messages)).get();
//            logger.info("Sent messages: {}", apiResponse);
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (InterruptedException | ExecutionException e) {
//        throw new RuntimeException(e);
        }
    }
}

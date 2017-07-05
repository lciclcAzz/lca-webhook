package com.lciclcazz.webhook.service;

import com.lciclcazz.webhook.config.Constants;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
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
        String imageUrl = "https://gitlab.com/uploads/system/project/avatar/2281010/PEO-octocat-2.svg.png";//createUri("/static/buttons/1040.jpg");
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
        pushTextContentsButton(token,message, (Constants.DEFUALT_LINE_GROUP != null?Constants.DEFUALT_LINE_GROUP:Constants.LINE_UlciclcAzz) );
    }

    public void pushTextContentsButton(@NonNull String token, @NonNull HashMap message,@NonNull String userId) throws IOException {
        String imageUrl = createUri("/static/build/build.png");
        ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
                (Constants.FAILED.equals(message.get(Constants.FAILED))?createUri("/static/build/build_failed.png"):imageUrl),
                "Project : "+message.get(Constants.PROJECT),    //max 40 characters.
                "Act. By : "+                                   //max (160:no image,60 within image) characters.
                (
                        ("Act. By : "+message.get(Constants.CM_AUTHOR)+"<"+message.get(Constants.CM_EMAIL)+">").length() < 60?
                        message.get(Constants.CM_AUTHOR)+"<"+message.get(Constants.CM_EMAIL)+">"
                        :"<"+message.get(Constants.CM_EMAIL)+">"
                ),

                Arrays.asList(
                        //URI action.
                        new URIAction(
                                "Goto Commit:"+message.get(Constants.CM_ID).toString().substring(0,5)+"...",//max 40 characters.
                                message.get(Constants.CM_URL)+""//max 1000 characters.
                        ),
                        //Postback action.
                        new PostbackAction(
                                "GOOD COMMIT",
                                "good_commit"
                        ),
                        //Message action.
                        new MessageAction(
                                "BUILD STATUS",
                                "status"
                        )

                ));
        TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
        this.push(token, templateMessage,userId);

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
        push(token, Collections.singletonList(message),(Constants.DEFUALT_LINE_GROUP != null?Constants.DEFUALT_LINE_GROUP:Constants.LINE_UlciclcAzz) );
    }

    public void push(@NonNull String token, @NonNull Message message, @NonNull String userId) {
        push(token, Collections.singletonList(message),userId);
    }

    public void push(@NonNull String token, @NonNull List<Message> messages, @NonNull String userId) {
        try {
            PushMessage pushMessage = new PushMessage(userId, messages );
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
            throw new RuntimeException(e);
//        } catch (InterruptedException | ExecutionException e) {
//        throw new RuntimeException(e);
        }
    }
}

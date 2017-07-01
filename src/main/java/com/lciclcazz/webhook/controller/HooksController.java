package com.lciclcazz.webhook.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lciclcazz.webhook.config.Constants;
import com.lciclcazz.webhook.domain.gitlab.ReqBody;
import com.lciclcazz.webhook.domain.gitlab.push.PushHook;
import com.lciclcazz.webhook.service.GitlabHooksServiceImpl;
import com.lciclcazz.webhook.service.LineBotService;
import com.lciclcazz.webhook.utils.Tools;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lciclcazz on 6/30/17.
 *
 */

@RestController
@RequestMapping("/hooks")
public class HooksController {
    final Logger logger = LoggerFactory.getLogger(HooksController.class);

    @Autowired
    private LineBotService lineBotService;

    @RequestMapping(value = "/gitlab",method = RequestMethod.POST)
//    public void performTask(HttpServletRequest req, HttpServletResponse res,@RequestBody  PushHook reqBody) throws IOException {
    public void performTask(HttpServletRequest req, HttpServletResponse res,@RequestBody String reqBody) throws IOException {
        logger.info("<<<< "+Thread.currentThread().getStackTrace()[1].getMethodName()+" >>>>");
        JsonNode jsonNode = null;
        ObjectMapper objectMapper = new ObjectMapper();

        switch (Constants.GITLAB_HEADER){
            case "TAG" :
                System.out.println();
                break;
            case "ISSUE" :
                System.out.println();
                break;
            case "WIKI" :
                System.out.println();
                break;
            case "PIPELINE" :
                System.out.println();
                break;
            case "BUILD" :
                System.out.println();
                break;
            case "NOTE" : //commit,merge,
                System.out.println();
                break;
            default : //PUSH Event
                HashMap message = new HashMap();
                jsonNode = Tools.getEvent(reqBody,"commits");
                message.put("commitId",jsonNode.path(0).path("id").asText());
                message.put("message",jsonNode.path(0).path("message").asText());
                message.put("timestamp",jsonNode.path(0).path("timestamp").asText());
                message.put("author",jsonNode.path(0).path("author").path("name").asText()+
                        "<"+jsonNode.path(0).path("author").path("email").asText()+">" );

                logger.info("Push Message {} \n{} \n{} \n{} "  ,message.get("commitId") ,message.get("message") ,message.get("timestamp") ,message.get("author"));

                lineBotService.pushTextContentsButton(Constants.TOKEN,message);
                break;

        }
    }

}

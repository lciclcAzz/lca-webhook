package com.lciclcazz.webhook.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lciclcazz.webhook.config.Constants;
import com.lciclcazz.webhook.service.LineBotService;
import com.lciclcazz.webhook.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

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
        HashMap message = null;
        logger.info("<<<<< CASE >>>>>{}",req.getHeader(Constants.GITLAB_HEADER));
        switch (req.getHeader(Constants.GITLAB_HEADER)){
            case Constants._TAG :

                break;
            case Constants._ISSUE :

                break;
            case Constants._WIKI :

                break;
            case Constants._PIPELINE :
                message = this.prepareMsg(reqBody,Constants._PIPELINE);
                if(Constants.FAILED.equals(message.get(Constants.CM_STATUS)))
                    lineBotService.pushTextContentsButton(Constants.TOKEN,message);

                break;
            case Constants._BUILD :

                break;
            case Constants._NOTE : //commit,merge,

                break;
            case Constants._PUSH : //commit,merge,
                message = this.prepareMsg(reqBody);
                lineBotService.pushTextContentsButton(Constants.TOKEN,message);
                break;
            default :
                break;

        }
    }

    public HashMap prepareMsg(String reqBody){
        return prepareMsg(reqBody,null);
    }
    public HashMap prepareMsg(String reqBody,String gitLabEvent){
        HashMap message = new HashMap();
        JsonNode jsonNode = null;
        jsonNode = Tools.getEvent(reqBody);
        message.put(Constants.PROJECT       ,jsonNode.path(Constants.PROJECT).path(Constants.CM_NAME).asText());
        message.put(Constants.PJ_ID         ,jsonNode.path(Constants.PJ_ID).asText());
        jsonNode = Tools.getEvent(reqBody,Constants.COMMITS);
        message.put(Constants.CM_ID         ,jsonNode.path(0).path(Constants.CM_ID).asText());
        message.put(Constants.CM_MSG        ,jsonNode.path(0).path(Constants.CM_MSG).asText());
        message.put(Constants.CM_TIMESTAMP  ,jsonNode.path(0).path(Constants.CM_TIMESTAMP).asText());
        message.put(Constants.CM_AUTHOR     ,jsonNode.path(0).path(Constants.CM_AUTHOR).path(Constants.CM_NAME).asText());
        message.put(Constants.CM_EMAIL      ,jsonNode.path(0).path(Constants.CM_AUTHOR).path(Constants.CM_EMAIL).asText());
        message.put(Constants.CM_URL        ,jsonNode.path(0).path(Constants.CM_URL).asText());

        if(gitLabEvent!=null) {
            jsonNode = Tools.getEvent(reqBody,Constants.BUILDS);
            if(Constants.FAILED.equals(jsonNode.path(0).path(Constants.CM_STATUS).asText()))
                message.put(Constants.CM_STATUS, Constants.FAILED);
            else
                message.put(Constants.CM_STATUS, Constants.PASS);
        }

        logger.info("<<<<< Push Message >>>>>{} \n{} \n{} \n{} \n{} \n{} "
                ,message.get(Constants.PROJECT)
                ,message.get(Constants.PJ_ID)
                ,message.get(Constants.CM_ID)
                ,message.get(Constants.CM_MSG)
                ,message.get(Constants.CM_TIMESTAMP)
                ,message.get(Constants.CM_AUTHOR));

        return message;
    }

}

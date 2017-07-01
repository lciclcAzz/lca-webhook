package com.lciclcazz.webhook.service;

import com.lciclcazz.webhook.domain.gitlab.push.PushHook;
import org.springframework.stereotype.Service;

/**
 * Created by lciclcazz on 6/30/17.
 */

@Service
public class GitlabHooksServiceImpl implements HooksService {

    @Override
    public Object getPushMessage(String strJson) {
        PushHook pushHook = new PushHook();
        return pushHook;
    }

    @Override
    public Object getTagMessage(String strJson) {
        return null;
    }

    @Override
    public Object getIssueMessage(String strJson) {
        return null;
    }

    @Override
    public Object getWikiMessage(String strJson) {
        return null;
    }

    @Override
    public Object getPipelineMessage(String strJson) {
        return null;
    }

    @Override
    public Object getBuildMessage(String strJson) {
        return null;
    }

    @Override
    public Object getCommitMessage(String strJson) {
        return null;
    }
}

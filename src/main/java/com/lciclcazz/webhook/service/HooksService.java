package com.lciclcazz.webhook.service;

import org.springframework.stereotype.Service;

/**
 * Created by lciclcazz on 6/30/17.
 */

@Service
public interface HooksService<T> {
    T getPushMessage(String strJson);
    T getTagMessage(String strJson);
    T getIssueMessage(String strJson);
    T getWikiMessage(String strJson);
    T getPipelineMessage(String strJson);
    T getBuildMessage(String strJson);
    T getCommitMessage(String strJson);

}

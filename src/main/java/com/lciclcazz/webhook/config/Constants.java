package com.lciclcazz.webhook.config;

import java.util.HashMap;

/**
 * Application constants.
 */
public final class Constants {

    private Constants() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";


    public static final String APP_NAME = System.getenv("APP_NAME");
    public static final String SECRET_KEY = System.getenv("LINE_BOT_CHANNEL_SECRET");
    public static final String TOKEN = System.getenv("LINE_BOT_CHANNEL_TOKEN");
    public static final String DEFUALT_LINE_GROUP = System.getenv("DEFUALT_LINE_GROUP");

    public static final String DEFAULT_HEADER="X-HEADER";
    public static final String GITLAB_HEADER="X-Gitlab-Event";
    public static final String LINE_HEADER="X-Line-Signature";

    public static final String LINE_UlciclcAzz="U286d471884e10b385774885526bdea35";
    public static final String LINE_UxDreamsBox="U6bf578ca3ebe7005bdd7e41d7725725e";


    public static final String PROJECT          = "project";
    public static final String PJ_ID            = "project_id";
    public static final String BUILDS            = "builds";

    public static final String COMMITS          = "commits";
    public static final String CM_ID            = "id";
    public static final String CM_MSG           = "message";
    public static final String CM_TIMESTAMP     = "timestamp";
    public static final String CM_AUTHOR        = "author";
    public static final String CM_NAME          = "name";
    public static final String CM_EMAIL         = "email";
    public static final String CM_URL           = "url";
    public static final String CM_STATUS        = "status";
    public static final String FAILED           = "failed";
    public static final String PASS             = "success";

    public static final String _PUSH      = "Push Hook"     ;
    public static final String _TAG       = "Tag Push Hook" ;
    public static final String _ISSUE     = "Issue Hook"    ;
    public static final String _WIKI      = "Wiki Page Hook";
    public static final String _PIPELINE  = "Pipeline Hook" ;
    public static final String _BUILD     = "Build Hook"    ;
    public static final String _NOTE      = "Note Hook"     ;


    public static HashMap<String,String> LINE_USER_ID = new HashMap<String,String>();
    public static HashMap<String,String> GITLAB_EVENT = new HashMap<String,String>();
    static {
        LINE_USER_ID.put("lciclcAzz", "U286d471884e10b385774885526bdea35");
        LINE_USER_ID.put("xDreamsBox", "U6bf578ca3ebe7005bdd7e41d7725725e");

    }

    public static String getGitlabEvent(String key) {
        return (String) GITLAB_EVENT.get(key);
    }
}

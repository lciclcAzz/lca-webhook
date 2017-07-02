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
    public static final String PJ_ID            = "project";

    public static final String COMMITS          = "commits";
    public static final String CM_ID            = "id";
    public static final String CM_MSG           = "message";
    public static final String CM_TIMESTAMP     = "timestamp";
    public static final String CM_AUTHOR        = "author";
    public static final String CM_NAME          = "name";
    public static final String CM_EMAIL         = "email";
    public static final String CM_URL           = "url";
    public static final String CM_STATUS        = "status";

    public static final String _PUSH      = "PUSH";
    public static final String _TAG       = "TAG";
    public static final String _ISSUE     = "ISSUE";
    public static final String _WIKI      = "WIKI";
    public static final String _PIPELINE  = "PIPELINE";
    public static final String _BUILD     = "BUILD";


    public static HashMap<String,String> LINE_USER_ID = new HashMap<String,String>();
    public static HashMap<String,String> GITLAB_EVENT = new HashMap<String,String>();
    static {
        LINE_USER_ID.put("lciclcAzz", "U286d471884e10b385774885526bdea35");
        LINE_USER_ID.put("xDreamsBox", "U6bf578ca3ebe7005bdd7e41d7725725e");

        GITLAB_EVENT.put(_PUSH    ,"Push Hook");
        GITLAB_EVENT.put(_TAG     ,"Tag Push Hook");
        GITLAB_EVENT.put(_ISSUE   ,"Issue Hook");
        GITLAB_EVENT.put(_WIKI    ,"Wiki Page Hook");
        GITLAB_EVENT.put(_PIPELINE,"Pipeline Hook");
        GITLAB_EVENT.put(_BUILD   ,"Pipeline Hook");

        //commit Event
        GITLAB_EVENT.put("NOTE","Note Hook");

    }

    public static String getGitlabEvent(String key) {
        return (String) GITLAB_EVENT.get(key);
    }
}

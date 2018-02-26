package controllers;

import com.google.inject.Inject;
import com.typesafe.config.Config;

public class LogoutController extends org.pac4j.play.LogoutController {

    @Inject
    public LogoutController(Config config) {
        String baseUrl = config.getString("baseUrl");
        setDefaultUrl(baseUrl/* + "/?defaulturlafterlogoutafteridp"*/);
        setLocalLogout(true);
        setCentralLogout(true);
        setLogoutUrlPattern(baseUrl + "/.*");
    }
}

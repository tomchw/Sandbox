package org.tchw.fakturownia.services.requestForAllData;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.tchw.fakturownia.services.requestForAllData.GetRequest.Login;

public class RequestLogin implements InitializingBean {

    @Value("${fakturownia.login}")
    private String login;

    @Value("${fakturownia.token}")
    private String token;

    private Login requestLogin;

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("Login: " + login);
    }

    public Login login() {
        if( requestLogin == null ) {
            log.debug("Preparing login for " + login);
            requestLogin = GetRequest.login(login, token);
        }
        return requestLogin;
    }

}

package com.drawsforall.usermanagement.configsecurity;

import org.springframework.security.core.Authentication;


public interface AuthenticationServiceInterface {
    Authentication getAuthentication();

}

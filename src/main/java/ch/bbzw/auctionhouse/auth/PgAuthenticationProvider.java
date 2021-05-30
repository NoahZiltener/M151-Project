package ch.bbzw.auctionhouse.auth;

import ch.bbzw.auctionhouse.model.UserGroup;
import ch.bbzw.auctionhouse.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PgAuthenticationProvider implements AuthenticationProvider {

    private final LoginService loginService;

    @Autowired
    public PgAuthenticationProvider(final LoginService loginService){
        this.loginService = loginService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        return getAuth(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Authentication getAuth(final String username, final String password){
        if(loginService.login(username, password).isPresent()){
            final UserGroup userGroup = loginService.login(username, password).get();
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    Collections.singletonList(new SimpleGrantedAuthority(userGroup.toString())));
        }
        return null;
    }
}

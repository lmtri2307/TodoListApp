package com.example.todolist.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private Object credentials = null;
    private final Object principal;
    public JWTAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
//        this.credentials = credentials;
    }
    public JWTAuthenticationToken(Object principal,Object credentials, Collection<? extends GrantedAuthority> authorities ) {
        super(authorities);

        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    public static JWTAuthenticationToken unauthenticated(Object principal) {
        return new JWTAuthenticationToken(principal);
    }

    public static JWTAuthenticationToken authenticated(Object principal, Object credentials,
                                                                    Collection<? extends GrantedAuthority> authorities) {
        return new JWTAuthenticationToken(principal, credentials, authorities);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}

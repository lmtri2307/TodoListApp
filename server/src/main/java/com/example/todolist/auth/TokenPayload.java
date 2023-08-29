package com.example.todolist.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenPayload {
    private String email;
    private Collection<? extends GrantedAuthority> authorities = Collections.emptyList();;

    public TokenPayload(Map<?, ?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            try {
                Field field = getClass().getDeclaredField(key);
                field.setAccessible(true);
                field.set(this, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString(){
        return email;
    }
}

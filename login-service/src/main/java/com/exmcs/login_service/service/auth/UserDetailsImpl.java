package com.exmcs.login_service.service.auth;

import com.exmcs.login_service.model.entity.UserLogin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserLogin user) {

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getUsername(),
                user.getPassword(), null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return super.equals(obj);

        UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(id, user.id);
    }
}

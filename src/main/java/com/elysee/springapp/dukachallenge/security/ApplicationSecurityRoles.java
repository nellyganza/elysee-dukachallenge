package com.elysee.springapp.dukachallenge.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.elysee.springapp.dukachallenge.security.ApplicationSecurityPermissions.TASK_READ;
import static com.elysee.springapp.dukachallenge.security.ApplicationSecurityPermissions.TASK_WRITE;

@Getter
@AllArgsConstructor
public enum ApplicationSecurityRoles {
    TASKOWNER(Sets.newHashSet(TASK_READ, TASK_WRITE));

    private Set<ApplicationSecurityPermissions> permissions;

    public Set<SimpleGrantedAuthority> grantedAuthorities(){
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}

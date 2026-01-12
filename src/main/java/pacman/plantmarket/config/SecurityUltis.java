package pacman.plantmarket.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pacman.plantmarket.entity.User;

public class SecurityUltis {
    private SecurityUltis() {
    }

    public static String getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated()){
            return null;
        }

        return auth.getName();
    }

    public static UserDetails toUserDetails(User user){
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(authority.getAuthority())
                .build();
    }
}

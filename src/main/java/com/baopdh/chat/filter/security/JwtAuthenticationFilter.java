package com.baopdh.chat.filter.security;

import com.baopdh.chat.model.CustomPrincipal;
import com.baopdh.chat.service.CustomUserDetailsService;
import com.baopdh.chat.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.userdetails.User;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(httpServletRequest);

        if (StringUtils.hasText(jwt) && jwtTokenUtil.validateToken(jwt)) {
            String username = jwtTokenUtil.getUsernameFromToken(jwt);
            int id = jwtTokenUtil.getIdFromToken(jwt);
            try {
                CustomPrincipal customPrincipal = new CustomPrincipal(User.withUsername(username)
                        .password("")
                        .roles("USER")
                        .build());
                customPrincipal.setId(id);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customPrincipal, null, customPrincipal.getAuthorities());

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } catch (UsernameNotFoundException e) {} //ignore exception
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

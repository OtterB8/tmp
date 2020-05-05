/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.config.websocket;

import com.baopdh.chat.constants.FrontendHost;
import com.baopdh.chat.model.CustomPrincipal;
import com.baopdh.chat.model.socket.UserStatusResponseBody;
import com.baopdh.chat.repository.OnlineUsers;
import com.baopdh.chat.util.JwtTokenUtil;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 *
 * @author admin
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, ApplicationListener<SessionDisconnectEvent> {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private OnlineUsers onlineUsers;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect")
                .setAllowedOrigins(FrontendHost.ORIGIN)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic", "/queue");
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor =
                    MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand()) && accessor.getNativeHeader("Authorization") != null) {
                    String jwt = getJwtFromMessage(accessor);

                    if (StringUtils.hasText(jwt) && jwtTokenUtil.validateToken(jwt)) {
                        int id = jwtTokenUtil.getIdFromToken(jwt);
                        try {
                            CustomPrincipal customPrincipal = new CustomPrincipal(User.withUsername(String.valueOf(id))
                                    .password("")
                                    .roles("USER")
                                    .build());
                            customPrincipal.setId(id);

                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customPrincipal, null, customPrincipal.getAuthorities());

                            accessor.setUser(authentication);

                            onlineUsers.addUser(id);
                            messagingTemplate.convertAndSend("/topic/userstatus", new UserStatusResponseBody(id, true));
                        } catch (UsernameNotFoundException e) {} //ignore exception
                    }
                }

                return message;
              }
          });
    }
    
    @Override
    public void onApplicationEvent(SessionDisconnectEvent e) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(e.getMessage(), StompHeaderAccessor.class);
        
        Principal principal = accessor.getUser();
        if (principal != null) {
            CustomPrincipal customPrincipal = (CustomPrincipal)((Authentication)principal).getPrincipal();
            int id = customPrincipal.getId();
            
            onlineUsers.removeUser(id);
            if (onlineUsers.hasUser(id))
                return;
            
            messagingTemplate.convertAndSend("/topic/userstatus",
                    new UserStatusResponseBody(customPrincipal.getId(), false));
        }
    }
    
    private String getJwtFromMessage(StompHeaderAccessor accessor) {
        List<String> headers = accessor.getNativeHeader("Authorization");
        if (headers != null) {
            String bearerToken = headers.get(0);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
        }
        
        return null;
    }
}
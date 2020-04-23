package com.baopdh.chat.config.security;

//package com.baopdh.chat.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableWebSecurity
//public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
//    private final String ZALO_APP_ID = "2317330623716227111";
//    private final String ZALO_APP_SECRET = "sAI2L8yRn7gbjzQu52G9";
//
//    @Autowired
//    ClientRegistrationRepository clientRegistrationRepository;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests(a -> a
//                .anyRequest().authenticated()
//            );
////            .exceptionHandling(e -> e
////                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
////            )
////            .oauth2Login(oauth2 -> oauth2
////                .authorizationEndpoint(a -> a.authorizationRequestResolver(authorizationRequestResolver()))
////                .clientRegistrationRepository(this.clientRegistrationRepository)
////            );
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(this.clientRegistration());
//    }
//
//    private OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
//        return new OAuth2AuthorizationRequestResolver() {
//            private OAuth2AuthorizationRequestResolver defaultResolver =
//                    new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
//
//            @Override
//            public OAuth2AuthorizationRequest resolve(HttpServletRequest httpServletRequest) {
//                OAuth2AuthorizationRequest req = defaultResolver.resolve(httpServletRequest);
//                if(req != null) {
//                    req = customizeAuthorizationRequest(req);
//                }
//                return req;
//            }
//
//            @Override
//            public OAuth2AuthorizationRequest resolve(HttpServletRequest httpServletRequest, String clientRegistrationId) {
//                OAuth2AuthorizationRequest req = defaultResolver.resolve(httpServletRequest, clientRegistrationId);
//                if(req != null) {
//                    req = customizeAuthorizationRequest(req);
//                }
//                return req;
//            }
//
//            private OAuth2AuthorizationRequest customizeAuthorizationRequest(OAuth2AuthorizationRequest req) {
//                Map<String,Object> extraParams = new HashMap<>();
//                extraParams.put("app_id", ZALO_APP_ID);
//
//                return OAuth2AuthorizationRequest
//                        .from(req)
//                        .additionalParameters(extraParams)
//                        .build();
//            }
//        };
//    }
//
//    private ClientRegistration clientRegistration() {
//        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId("zalo");
//        builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
//            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//            .redirectUriTemplate("https://{baseHost}{basePort}/login/oauth2/code/{registrationId}")
//            .authorizationUri("https://oauth.zaloapp.com/v3/auth")
//            .tokenUri("https://oauth.zaloapp.com/v3/access_token")
//            .userInfoUri("https://graph.zalo.me/v2.0/me?fields=id,birthday,name,gender,picture")
//            .clientName("Zalo")
//            .clientId(ZALO_APP_ID)
//            .clientSecret(ZALO_APP_SECRET);
//
//        return builder.build();
//    }
//}

package com.example.jslproject.oauth2;

import com.example.jslproject.auth.CustomDetails;
import com.example.jslproject.constant.Role;
import com.example.jslproject.oauth2.provider.GoogleOAuth2UserInfo;
import com.example.jslproject.oauth2.provider.KakaoOAuth2UserInfo;
import com.example.jslproject.oauth2.provider.NaverOAuth2UserInfo;
import com.example.jslproject.oauth2.provider.OAuth2UserInfo;
import com.example.jslproject.repository.UserRepository;
import com.example.jslproject.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        System.out.println(oAuth2User.getAttributes());
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("google Login");
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("naver Login");
            oAuth2UserInfo = new NaverOAuth2UserInfo(oAuth2User.getAttribute("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("kakao Login");
            oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes(),oAuth2User.getAttribute("properties"),oAuth2User.getAttribute("kakao_account"));

        }

        String name = oAuth2UserInfo.getName();
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String password = passwordEncoder.encode("oauth1234" + providerId);

        User user = userRepository.findByEmailAndProviderAndUsername(email, provider, name);

        if (user == null) {
            user = User.builder()
                    .username(name)
                    .provider(provider)
                    .provider_id(providerId)
                    .userFullName(name + providerId + provider)
                    .email(email)
                    .password(password)
                    .role(Role.ROLE_USER)
                    .build();
            userRepository.save(user);
        }


        return new CustomDetails(user, oAuth2User.getAttributes());

    }
}

package mariTime.app.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Preluarea datelor utilizatorului
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // În GitHub, identificatorul unic poate fi "login" sau "id"
        String principalName = (String) attributes.get("login");  // Folosind "login" de exemplu, pentru GitHub

        if (principalName == null || principalName.isEmpty()) {
            throw new IllegalArgumentException("principalName cannot be empty");
        }

        // Construiește un OAuth2User personalizat, dacă este necesar
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "login");  // Mapează atributul "login" la principalName
    }

}

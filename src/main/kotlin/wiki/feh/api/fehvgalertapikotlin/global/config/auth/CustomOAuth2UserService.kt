package wiki.feh.api.fehvgalertapikotlin.global.config.auth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import wiki.feh.api.fehvgalertapikotlin.domain.user.domain.User
import wiki.feh.api.fehvgalertapikotlin.domain.user.repository.UserRepository
import wiki.feh.api.fehvgalertapikotlin.global.config.auth.dto.OAuthAttributes
import wiki.feh.api.fehvgalertapikotlin.global.config.auth.dto.SessionUser
import java.util.*
import javax.servlet.http.HttpSession

@Service
class CustomOAuth2UserService(
    val userRepository: UserRepository,
    val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest) : OAuth2User {
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        var oAuth2User: OAuth2User = delegate.loadUser(userRequest)

        var registrationId: String = userRequest.clientRegistration.registrationId
        var userNameAttributeName: String = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        var attributes: OAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.attributes)

        var user : User = saveOrUpdate(attributes)

        httpSession.setAttribute("user", SessionUser(user))

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(user.getRoleKey())),
            attributes.attributes,
            attributes.nameAttributeKey)

    }

    fun saveOrUpdate(attributes: OAuthAttributes) : User{
        val user: User = userRepository.findByEmail(attributes.email).orElse(attributes.toEntity()).update(attributes.name, attributes.picture)
        return userRepository.save(user)
    }

}
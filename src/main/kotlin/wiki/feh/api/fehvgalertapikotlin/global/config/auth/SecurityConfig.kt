package wiki.feh.api.fehvgalertapikotlin.global.config.auth

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import wiki.feh.api.fehvgalertapikotlin.domain.user.domain.Role

@EnableWebSecurity
class SecurityConfig(
    val customOAuth2UserService: CustomOAuth2UserService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable().headers().frameOptions().disable().and()
            .authorizeRequests()
            .antMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
            .regexMatchers("/admin/board/posts\\/[\\d]*").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/vginfo/**", "/api/v1/vgdata/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/v1/vginfo/**").hasRole(Role.USER.name)
            .antMatchers(HttpMethod.POST, "/api/v1/vginfo/**").hasRole(Role.USER.name)
            .antMatchers("/admin/manualcron", "/api/v1/posts/**", "/admin/board/posts/update/**", "/admin/board/posts/save/**").hasRole(Role.USER.name)
            .antMatchers("/vg/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and()
            .logout()
            .logoutSuccessUrl("/admin/board/")
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
    }
}
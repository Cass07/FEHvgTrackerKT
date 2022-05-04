package wiki.feh.api.fehvgalertapikotlin.global.config.util

import org.springframework.stereotype.Component
import wiki.feh.api.fehvgalertapikotlin.global.config.auth.dto.SessionUser
import javax.servlet.http.HttpSession

@Component
class SecurityUtil (val httpSession: HttpSession) {

    fun getLoginUser() : SessionUser? {
        return httpSession.getAttribute("user") as? SessionUser
    }

}
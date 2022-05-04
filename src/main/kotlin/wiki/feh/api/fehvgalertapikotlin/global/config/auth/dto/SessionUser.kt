package wiki.feh.api.fehvgalertapikotlin.global.config.auth.dto

import wiki.feh.api.fehvgalertapikotlin.domain.user.domain.User

class SessionUser(user: User) {
    val name: String = user.name
    val email: String = user.email
    val picture: String = user.picture
}
package wiki.feh.api.fehvgalertapikotlin.global.config.auth.dto

import wiki.feh.api.fehvgalertapikotlin.domain.user.domain.Role
import wiki.feh.api.fehvgalertapikotlin.domain.user.domain.User

open class OAuthAttributes (
    val attributes: Map<String, Any>,
    val nameAttributeKey: String,
    val name: String,
    val email: String,
    val picture: String
    ) {

    companion object{
        fun of(registerId: String, usernameAttirbuteName: String, attributes: Map<String, Any>) : OAuthAttributes{
            return ofGoogle(usernameAttirbuteName, attributes)
        }
        fun ofGoogle(usernameAttirbuteName: String, attributes : Map<String, Any>) : OAuthAttributes{
            return OAuthAttributes(name = attributes.get("name").toString(),
                email = attributes.get("email").toString(),
                picture = attributes.get("picture").toString(),
                attributes = attributes,
                nameAttributeKey = usernameAttirbuteName)
        }
    }

    fun toEntity() : User {
        return User(name = name, email = email, picture = picture, role = Role.GUEST)
    }


}
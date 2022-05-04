package wiki.feh.api.fehvgalertapikotlin.domain.user.domain

enum class Role(val key : String, val title : String) {
    GUEST("ROLE_GUEST", "guest"),
    USER("ROLE_USER", "user");

}
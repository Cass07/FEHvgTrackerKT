package wiki.feh.api.fehvgalertapikotlin.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import wiki.feh.api.fehvgalertapikotlin.domain.user.domain.User
import java.util.*

interface UserRepository : JpaRepository<User,Long> {
    fun findByEmail(email:String) : Optional<User>

}
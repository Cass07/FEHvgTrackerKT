package wiki.feh.api.fehvgalertapikotlin.domain.user.domain

import wiki.feh.api.fehvgalertapikotlin.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class User(name: String,
           email: String,
           picture: String,
           role: Role
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var name: String = name
        protected set

    @Column(nullable = false)
    var email: String = email
        protected set

    @Column
    var picture: String = picture
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = role
        protected set

    fun update(name: String, picture: String): User {
        this.name = name
        this.picture = picture

        return this
    }

    fun getRoleKey(): String {
        return this.role.key
    }

}
package wiki.feh.api.fehvgalertapikotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class FehVgAlertApiKotlinApplication

fun main(args: Array<String>) {
    runApplication<FehVgAlertApiKotlinApplication>(*args)
}

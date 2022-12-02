package kotlinvienna.spring

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import java.util.*

@EnableJpaRepositories
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@RestController
@RequestMapping("/talk")
class TalkController(
    private val talkRepository: TalkRepository
) {

    @GetMapping
    fun getAllTalks(): List<Talk> = talkRepository.findAll()

    @GetMapping("/{uuid}")
    fun getTalk(@PathVariable uuid: UUID): Talk? = talkRepository.findByIdOrNull(uuid)

    @PostMapping
    fun addTalk(@RequestBody talk: Talk): Talk = talkRepository.save(talk)

}

@Entity
data class Talk(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
)

@Repository
interface TalkRepository : JpaRepository<Talk, UUID>

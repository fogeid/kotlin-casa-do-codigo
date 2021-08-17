package br.com.zup.repositories

import br.com.zup.models.Autor
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface AutorRepository: JpaRepository<Autor, String> {
    fun findByEmail(email: String): Optional<Autor>
}
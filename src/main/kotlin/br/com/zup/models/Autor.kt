package br.com.zup.models

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Autor(
    val nome: String,
    val email: String,
    var descricao: String,
    val endereco: Endereco
) {
    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: String? = null
    val criadoEm: LocalDateTime = LocalDateTime.now()
}
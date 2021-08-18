package br.com.zup.controllers

import br.com.zup.dto.response.AutorResponse
import br.com.zup.dto.response.EnderecoResponse
import br.com.zup.models.Autor
import br.com.zup.models.Endereco
import br.com.zup.repositories.AutorRepository
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class AutorControllerTest {
    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor: Autor

    @BeforeEach
    internal fun setup() {
        val enderecoResponse = EnderecoResponse(
            logradouro = "Rua Seis",
            complemento = "Portão Laranja",
            bairro = "Coophamil",
            localidade = "Cuiabá",
            uf = "MT"
        )
        val endereco = Endereco(
            enderecoResponse,
            numero = "10"
        )
        autor = Autor(
            nome = "Diego Batista",
            email = "diego.batista@zup.com.br",
            descricao = "Vsf porra!",
            endereco = endereco
        )

        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`() {
        val response = client.toBlocking().exchange(
            "/autores?email=${autor.email}",
            AutorResponse::class.java
        )

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.email, response.body()!!.email)
        assertEquals(autor.descricao, response.body()!!.descricao)
    }
}
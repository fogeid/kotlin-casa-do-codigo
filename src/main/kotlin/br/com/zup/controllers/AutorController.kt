package br.com.zup.controllers

import br.com.zup.clients.EnderecoClient
import br.com.zup.dto.request.AutorRequest
import br.com.zup.dto.response.AutorResponse
import br.com.zup.dto.response.EnderecoResponse
import br.com.zup.repositories.AutorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class AutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient
) {
    @Post
    @Transactional
    fun inserir(@Body @Valid request: AutorRequest): HttpResponse<Any> {
        val enderecoResponse: HttpResponse<EnderecoResponse> = enderecoClient.consulta(request.cep)
        val autor = request.toModel(enderecoResponse.body()!!)

        autorRepository.save(autor)
        val uri = UriBuilder.of("/autores/{id}")
            .expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)
    }

    @Get
    @Transactional
    fun lista(@QueryValue(defaultValue = "") email: String): HttpResponse<Any> {
        if (email.isBlank()) {
            val autores = autorRepository.findAll()
            val response = autores.map {
                    autor -> AutorResponse(autor)
            }
            return HttpResponse.ok(response)
        }
        val isAutor = autorRepository.findByEmail(email)

        if (isAutor.isEmpty) {
            return HttpResponse.notFound()
        }
        return HttpResponse.ok(AutorResponse(isAutor.get()))
    }

    @Put("/{id}")
    @Transactional
    fun atualiza(@PathVariable id: String, descricao: String): HttpResponse<Any> {
        val isAutor = autorRepository.findById(id)

        if (isAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = isAutor.get()
        autor.descricao = descricao
        autorRepository.update(autor)
        return HttpResponse.ok(AutorResponse(autor))
    }

    @Delete("/{id}")
    @Transactional
    fun delete(@PathVariable id: String): HttpResponse<Any> {
        val isAutor = autorRepository.findById(id)

        if (isAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        autorRepository.deleteById(id)
        return HttpResponse.ok()
    }

//    @Get
//    fun liata(@QueryValue(defaultValue = "") email: String):

}
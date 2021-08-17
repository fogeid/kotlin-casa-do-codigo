package br.com.zup.controllers

import br.com.zup.dto.request.AutorRequest
import br.com.zup.dto.response.AutorResponse
import br.com.zup.repositories.AutorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class AutorController(val autorRepository: AutorRepository) {

    @Post
    @Transactional
    fun inserir(@Body @Valid request: AutorRequest): HttpResponse<Any> {
        val autor = request.toModel()
        autorRepository.save(autor)
        val uri = UriBuilder.of("/autores/{id}")
            .expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)
    }

    @Get
    @Transactional
    fun lista(): HttpResponse<List<AutorResponse>> {
        val autores = autorRepository.findAll()
        val response = autores.map {
            autor -> AutorResponse(autor)
        }

        return HttpResponse.ok(response)
    }

    @Put("/{id}")
    @Transactional
    fun atualiza(@PathVariable id: String, descricao: String): HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao
        autorRepository.update(autor)
        return HttpResponse.ok(AutorResponse(autor))
    }

}
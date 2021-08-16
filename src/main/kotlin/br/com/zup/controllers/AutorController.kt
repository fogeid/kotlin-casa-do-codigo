package br.com.zup.controllers

import br.com.zup.dto.request.AutorRequest
import br.com.zup.dto.response.AutorResponse
import br.com.zup.repositories.AutorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class AutorController(val autorRepository: AutorRepository) {

    @Post
    fun inserir(@Body @Valid request: AutorRequest) {
        val autor = request.toModel()
        autorRepository.save(autor)
    }

    @Get
    fun lista(): HttpResponse<List<AutorResponse>> {
        val autores = autorRepository.findAll()
        val response = autores.map {
            autor -> AutorResponse(autor)
        }

        return HttpResponse.ok(response)
    }

}
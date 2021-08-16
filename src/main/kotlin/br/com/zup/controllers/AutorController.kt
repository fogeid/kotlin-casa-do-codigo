package br.com.zup.controllers

import br.com.zup.dto.request.AutorRequest
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class AutorController {

    @Post
    fun inserir(@Body @Valid request: AutorRequest) {
        println("Requisição => $request")
        val autor = request.toModel()
        println("Autor => ${autor.nome}")
    }

}
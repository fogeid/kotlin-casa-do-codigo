package br.com.zup.clients

import br.com.zup.dto.response.EnderecoResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("https://viacep.com.br/ws")
interface EnderecoClient {
    @Get("/{cep}/json")
//    @Get(consumes = [ MediaType.APPLICATION_XML ])
    fun consulta(cep: String): HttpResponse<EnderecoResponse>
}
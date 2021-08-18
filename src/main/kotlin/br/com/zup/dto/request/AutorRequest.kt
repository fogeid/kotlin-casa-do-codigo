package br.com.zup.dto.request

import br.com.zup.dto.response.EnderecoResponse
import br.com.zup.models.Autor
import br.com.zup.models.Endereco
import br.com.zup.validators.ValorUnico
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class AutorRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email @field:ValorUnico(obj = Autor::class, fieldName = "email") val email: String,
    @field:NotBlank @field:Size(max = 400) val descricao: String,
    @field:NotBlank val cep: String,
    @field:NotBlank val numero: String
) {
    fun toModel(enderecoResponse: EnderecoResponse): Autor {
        val endereco = Endereco(enderecoResponse, numero)
        return Autor(nome = this.nome, email = this.email, descricao = this.descricao, endereco = endereco)
    }
}

package br.com.zup.models

import br.com.zup.dto.response.EnderecoResponse
import javax.persistence.Embeddable

@Embeddable
data class Endereco(
    val rua: String,
    val bairro: String,
    val numero: String,
    val complemento: String,
    val cidade: String,
    val estado: String
) {
    constructor(enderecoResponse: EnderecoResponse, numero: String): this(
        rua = enderecoResponse.logradouro,
        bairro = enderecoResponse.bairro,
        numero = numero,
        complemento = enderecoResponse.complemento,
        cidade = enderecoResponse.localidade,
        estado = enderecoResponse.uf
    )
}

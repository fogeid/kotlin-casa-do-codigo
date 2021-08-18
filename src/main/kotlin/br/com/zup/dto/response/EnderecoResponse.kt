package br.com.zup.dto.response

data class EnderecoResponse(
    val logradouro: String,
    val complemento: String,
    val bairro: String,
    val localidade: String,
    val uf: String
)
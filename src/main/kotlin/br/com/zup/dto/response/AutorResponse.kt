package br.com.zup.dto.response

import br.com.zup.models.Autor

class AutorResponse(val autor: Autor) {
    val nome: String = autor.nome
    val email: String = autor.email
    val descricao: String = autor.descricao
}
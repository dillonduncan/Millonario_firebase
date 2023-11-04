package com.example.millonariofirebase.Model

data class Preguntas(
    var id: String,
    var pregunta: String,
    var categoria: String,
    var respuestCorrct: String,
    var listaOpciones: MutableList<Respuestas>
){
    constructor():this("","","","", mutableListOf())
}

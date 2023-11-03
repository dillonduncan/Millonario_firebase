package com.example.millonariofirebase.Model

data class Preguntas(
    var id: Int,
    var pregunta: String,
    var categoria: String,
    var respuestCorrct: String,
    var listaOpciones: MutableList<Respuestas>
)

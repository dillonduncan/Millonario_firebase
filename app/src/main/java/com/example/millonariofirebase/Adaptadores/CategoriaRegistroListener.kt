package com.example.millonariofirebase.Adaptadores

import com.example.millonariofirebase.Model.Categorias
import com.google.firebase.database.DataSnapshot

interface CategoriaRegistroListener {
    fun onDeleteItemClick(categorias: Categorias)
    fun onEditItemClick(categorias: Categorias)
}
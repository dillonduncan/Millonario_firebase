package com.example.millonariofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.millonariofirebase.databinding.ActivityMenuCategoriasBinding

class Menu_Categorias_Activity : AppCompatActivity() {
    lateinit var binding: ActivityMenuCategoriasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMenuCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegistrarCategorias.setOnClickListener { startActivity(Intent(this,Registrar_Categorias_Activity::class.java)) }
    }
}
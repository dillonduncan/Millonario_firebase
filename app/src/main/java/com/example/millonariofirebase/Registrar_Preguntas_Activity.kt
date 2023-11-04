package com.example.millonariofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.millonariofirebase.Adaptadores.AdaptadorCategoriasRegistro
import com.example.millonariofirebase.Model.Categorias
import com.example.millonariofirebase.Model.Preguntas
import com.example.millonariofirebase.Model.Respuestas
import com.example.millonariofirebase.databinding.ActivityRegistrarPreguntasBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.launch

class Registrar_Preguntas_Activity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrarPreguntasBinding
    lateinit var preguntasRef: DatabaseReference
    lateinit var categoriasRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var categoriaSelec: String
    var listRespuestas:MutableList<Respuestas> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()
        categoriasRef = database.getReference("Categorias")
        preguntasRef = database.getReference("Preguntas")
        LlenarSpCategorias()
        binding.btnRegistrarPregunta.setOnClickListener {  RegistrarPreguntas()}
    }

    private fun RegistrarPreguntas() {
        if (binding.edtPregunta.text.isNullOrEmpty()){
            binding.edtPregunta.error="Campo obligatorio"
        }
        if (binding.edtRespuestaCorrecta.text.isNullOrEmpty()){
            binding.edtRespuestaCorrecta.error="Campo obligatorio"
        }
        if (binding.edtRespuestaMala1.text.isNullOrEmpty()){
            binding.edtRespuestaMala1.error="Campo obligatorio"
        }
        if (binding.edtRespuestaMala2.text.isNullOrEmpty()){
            binding.edtRespuestaMala2.error="Campo obligatorio"
        }
        if (binding.edtRespuestaMala3.text.isNullOrEmpty()){
            binding.edtRespuestaMala3.error="Campo obligatorio"
        }else if (binding.btnRegistrarPregunta.text.equals("Agregar")){
            listRespuestas.add(Respuestas("a",binding.edtRespuestaCorrecta.text.toString()))
            listRespuestas.add(Respuestas("b",binding.edtRespuestaMala1.text.toString()))
            listRespuestas.add(Respuestas("c",binding.edtRespuestaMala2.text.toString()))
            listRespuestas.add(Respuestas("d",binding.edtRespuestaMala3.text.toString()))
            var id=preguntasRef.push().key
            var pregunta=Preguntas(
                id.toString(),
                binding.edtPregunta.text.toString(),
                binding.spCategorias.selectedItem.toString(),
                binding.edtRespuestaCorrecta.text.toString(),
                listRespuestas
            )
            preguntasRef.child(id ?:"").setValue(pregunta)
            Toast.makeText(
                this@Registrar_Preguntas_Activity,
                "Registro exitoso",
                Toast.LENGTH_SHORT
            ).show()
            LimpiarCampos()
        }
    }

    private fun LimpiarCampos() {
        binding.edtPregunta.setText("")
        binding.edtRespuestaCorrecta.setText("")
        binding.edtRespuestaMala1.setText("")
        binding.edtRespuestaMala2.setText("")
        binding.edtRespuestaMala3.setText("")
    }

    fun LlenarSpCategorias() {
        lifecycleScope.launch {
            categoriasRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listCategorias: MutableList<String> = mutableListOf()
                    snapshot.children.forEach { snap ->
                        var categoriaNombre=snap.getValue(Categorias::class.java)
                        listCategorias.add(
                            categoriaNombre!!.nombre
                        )
                    }
                    var adaptador = object : ArrayAdapter<String>(
                        this@Registrar_Preguntas_Activity,
                        android.R.layout.simple_spinner_dropdown_item,
                        listCategorias
                    ) {
                    }
                    binding.spCategorias.adapter = adaptador
                    binding.spCategorias.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                categoriaSelec = binding.spCategorias.selectedItem.toString()
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}
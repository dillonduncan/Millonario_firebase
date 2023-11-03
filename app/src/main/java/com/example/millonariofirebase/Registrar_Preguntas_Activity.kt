package com.example.millonariofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.millonariofirebase.Adaptadores.AdaptadorCategoriasRegistro
import com.example.millonariofirebase.Model.Categorias
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
    lateinit var categoriasRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var categoriaSelec: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()
        categoriasRef = database.getReference("Categorias")
        LlenarSpCategorias()
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
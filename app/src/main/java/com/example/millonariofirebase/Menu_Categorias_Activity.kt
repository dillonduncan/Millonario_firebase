package com.example.millonariofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.millonariofirebase.Adaptadores.AdaptadorCategorias
import com.example.millonariofirebase.Adaptadores.AdaptadorCategoriasRegistro
import com.example.millonariofirebase.Model.Categorias
import com.example.millonariofirebase.databinding.ActivityMenuCategoriasBinding
import com.example.millonariofirebase.databinding.ActivityRegistrarCategoriasBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class Menu_Categorias_Activity : AppCompatActivity() {
    lateinit var binding: ActivityMenuCategoriasBinding
    lateinit var categoriasRef: DatabaseReference
    lateinit var adaptador: AdaptadorCategorias
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvCategorias.layoutManager = LinearLayoutManager(this)
        database = FirebaseDatabase.getInstance()
        categoriasRef = database.getReference("Categorias")
        binding.txtCategAdd.setOnClickListener { CambiarRegistroCategorias() }
        binding.btnRegistrarCategorias.setOnClickListener {CambiarRegistroCategorias()}
        ObtenerCategorias()
        binding.txtPreguntAdd.setOnClickListener { CambiarRegistroPreguntas() }
        binding.btnRegistrarPreguntas.setOnClickListener { CambiarRegistroPreguntas() }
    }

    fun CambiarRegistroCategorias() =
        startActivity(Intent(this, Registrar_Categorias_Activity::class.java))

    fun CambiarRegistroPreguntas() =
        startActivity(Intent(this, Registrar_Preguntas_Activity::class.java))

    fun ObtenerCategorias() {
        lifecycleScope.launch {
            categoriasRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listCategorias: MutableList<Categorias> = mutableListOf()
                    snapshot.children.forEach { snap ->
                        listCategorias.add(
                            snap.getValue(Categorias::class.java) ?: Categorias(
                                "",
                                ""
                            )
                        )
                    }
                    lifecycleScope.launch {
                        adaptador = AdaptadorCategorias(
                            listCategorias
                        )
                        binding.rvCategorias.adapter = adaptador
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}
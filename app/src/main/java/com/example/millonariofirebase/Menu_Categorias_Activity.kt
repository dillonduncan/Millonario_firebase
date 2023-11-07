package com.example.millonariofirebase

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.millonariofirebase.Adaptadores.AdaptadorCategorias
import com.example.millonariofirebase.Adaptadores.AdaptadorCategoriasRegistro
import com.example.millonariofirebase.Model.Categorias
import com.example.millonariofirebase.Model.Preguntas
import com.example.millonariofirebase.Model.Respuestas
import com.example.millonariofirebase.databinding.ActivityMenuCategoriasBinding
import com.example.millonariofirebase.databinding.ActivityRegistrarCategoriasBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

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
        binding.btnRegistrarCategorias.setOnClickListener { CambiarRegistroCategorias() }
        ObtenerCategorias()
        binding.txtPreguntAdd.setOnClickListener { CambiarRegistroPreguntas() }
        binding.btnRegistrarPreguntas.setOnClickListener { CambiarRegistroPreguntas() }
        binding.btnComenzar.setOnClickListener { PlayJuego() }
        Ranking()
        ReproducirMusica()
    }

    fun ReproducirMusica() {2
        val mpMusic = MediaPlayer.create(this, R.raw.musiquitaprincipal)
        mpMusic.start()
    }

    fun Ranking() {
        binding.apply {
            txtRankingMoney.text = "Dinero: $" + JuegoPlay_Activity.money
            txtRankingCuestions.text = "Preguntas: " + JuegoPlay_Activity.cantPreguntas
        }
    }

    fun PlayJuego() {
        if (listCategorias.size < 1) {
            Toast.makeText(this, "Escoja una categoria", Toast.LENGTH_SHORT).show()
        } else if (listCategorias.size > 0) {
            startActivity(Intent(this, JuegoPlay_Activity::class.java))
        }
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
                    if (listCategorias.size < 1) {
                        //registrar categorias
                        val id1 = categoriasRef.push().key
                        val categ1 = Categorias(id1!!, "Desportes")
                        categoriasRef.child(id1!!).setValue(categ1)
                        val id2 = categoriasRef.push().key
                        val categ2 = Categorias(id2!!, "Matematicas")
                        categoriasRef.child(id2!!).setValue(categ2)

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

    companion object {
        var listCategorias: MutableList<String> = mutableListOf()
    }
}
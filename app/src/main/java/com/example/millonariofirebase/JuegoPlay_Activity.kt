package com.example.millonariofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.millonariofirebase.Model.Preguntas
import com.example.millonariofirebase.databinding.ActivityJuegoPlayBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class JuegoPlay_Activity : AppCompatActivity() {
    lateinit var binding: ActivityJuegoPlayBinding
    lateinit var preguntasRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()
        preguntasRef = database.getReference("Preguntas")
        ObtenerPreguntasRest()
    }

    fun ObtenerPreguntasRest() {
        lifecycleScope.launch {
            preguntasRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listPreguntas: MutableList<Preguntas> = mutableListOf()
                    snapshot.children.forEach { snap ->
                        var pregunta = snap.getValue(Preguntas::class.java)
                        Menu_Categorias_Activity.listCategorias.forEach {
                            if (pregunta!!.categoria == it) {
                                listPreguntas.add(pregunta)
                            }
                        }
                    }
                    var numalt= Random
                    var num=numalt.nextInt(1..listPreguntas.size)
                    binding.txtPregunta.text=listPreguntas[num].pregunta
                    binding.txtOpcA.text="a."+listPreguntas[num].listaOpciones[1].respuesta
                    binding.txtOpcB.text="b."+listPreguntas[num].listaOpciones[0].respuesta
                    binding.txtOpcC.text="c."+listPreguntas[num].listaOpciones[2].respuesta
                    binding.txtOpcD.text="d."+listPreguntas[num].listaOpciones[3].respuesta
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}
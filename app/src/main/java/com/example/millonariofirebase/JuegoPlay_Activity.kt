package com.example.millonariofirebase

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
    fun MostrarPregunta(){

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
                    var respSelect=""
                    var numalt = Random
                    var num = numalt.nextInt(1..listPreguntas.size)
                    var respCorrect=listPreguntas[num].respuestCorrct
                    binding.apply {
                        txtPregunta.text = listPreguntas[num].pregunta
                        txtOpcA.text = listPreguntas[num].listaOpciones[1].respuesta
                        txtOpcB.text = listPreguntas[num].listaOpciones[0].respuesta
                        txtOpcC.text = listPreguntas[num].listaOpciones[2].respuesta
                        txtOpcD.text = listPreguntas[num].listaOpciones[3].respuesta
                        txtOpcA.setOnClickListener {
                            if (txtOpcA.text==respCorrect){
                                txtOpcA.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcB.isEnabled=false
                                txtOpcC.isEnabled=false
                                txtOpcD.isEnabled=false
                            }else{
                                txtOpcA.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcB.isEnabled=false
                                txtOpcC.isEnabled=false
                                txtOpcD.isEnabled=false
                            }
                        }
                        txtOpcB.setOnClickListener {
                            if (txtOpcB.text==respCorrect){
                                txtOpcB.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcA.isEnabled=false
                                txtOpcC.isEnabled=false
                                txtOpcD.isEnabled=false
                            }else{
                                txtOpcB.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcA.isEnabled=false
                                txtOpcC.isEnabled=false
                                txtOpcD.isEnabled=false
                            }
                        }
                        txtOpcC.setOnClickListener {
                            if (txtOpcC.text==respCorrect){
                                txtOpcC.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcA.isEnabled=false
                                txtOpcB.isEnabled=false
                                txtOpcD.isEnabled=false
                            }else{
                                txtOpcC.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcA.isEnabled=false
                                txtOpcB.isEnabled=false
                                txtOpcD.isEnabled=false
                            }
                        }
                        txtOpcD.setOnClickListener {
                            if (txtOpcD.text==respCorrect){
                                txtOpcD.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcA.isEnabled=false
                                txtOpcB.isEnabled=false
                                txtOpcC.isEnabled=false
                            }else{
                                txtOpcD.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcA.isEnabled=false
                                txtOpcB.isEnabled=false
                                txtOpcC.isEnabled=false
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}
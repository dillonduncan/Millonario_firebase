package com.example.millonariofirebase

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
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
    var num1 = 0
    var num2 = 0
    var num3 = 0
    var num4 = 0
    var money = 0
    var respCorrect = ""
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
                    var numalt = Random
                    var num = numalt.nextInt(1..listPreguntas.size)
                    respCorrect = listPreguntas[num].respuestCorrct
                    binding.apply {
                        txtPregunta.text = listPreguntas[num].pregunta
                        num1 = numalt.nextInt(0..3)
                        num2 = numalt.nextInt(0..3)
                        while (num1 == num2) {
                            num2 = numalt.nextInt(0..3)
                        }
                        num3 = numalt.nextInt(0..3)
                        while (num3 == num2 || num3 == num1) {
                            num3 = numalt.nextInt(0..3)
                        }
                        num4 = numalt.nextInt(0..3)
                        while (num4 == num3 || num4 == num2 || num4 == num1) {
                            num4 = numalt.nextInt(0..3)
                        }
                        txtOpcA.text = listPreguntas[num].listaOpciones[num1].respuesta
                        txtOpcB.text = listPreguntas[num].listaOpciones[num2].respuesta
                        txtOpcC.text = listPreguntas[num].listaOpciones[num3].respuesta
                        txtOpcD.text = listPreguntas[num].listaOpciones[num4].respuesta
                        txtOpcA.setOnClickListener {
                            if (txtOpcA.text == respCorrect) {
                                txtOpcA.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcB.isEnabled = false
                                txtOpcC.isEnabled = false
                                txtOpcD.isEnabled = false
                                money += 1000
                                MostrarPregunta(
                                    listPreguntas,
                                    this.txtOpcA
                                )
                            } else {
                                txtOpcA.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcB.isEnabled = false
                                txtOpcC.isEnabled = false
                                txtOpcD.isEnabled = false
                                Reiniciar()
                            }
                            binding.txtRankin.text = "$ " + money.toString()
                        }
                        txtOpcB.setOnClickListener {
                            if (txtOpcB.text == respCorrect) {
                                txtOpcB.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcA.isEnabled = false
                                txtOpcC.isEnabled = false
                                txtOpcD.isEnabled = false
                                money += 1000
                                MostrarPregunta(
                                    listPreguntas,
                                    this.txtOpcB
                                )
                            } else {
                                txtOpcB.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcA.isEnabled = false
                                txtOpcC.isEnabled = false
                                txtOpcD.isEnabled = false
                                Reiniciar()
                            }
                            binding.txtRankin.text = "$ " + money.toString()
                        }
                        txtOpcC.setOnClickListener {
                            if (txtOpcC.text == respCorrect) {
                                txtOpcC.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcA.isEnabled = false
                                txtOpcB.isEnabled = false
                                txtOpcD.isEnabled = false
                                money += 1000
                                MostrarPregunta(
                                    listPreguntas,
                                    this.txtOpcC
                                )
                            } else {
                                txtOpcC.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcA.isEnabled = false
                                txtOpcB.isEnabled = false
                                txtOpcD.isEnabled = false
                                Reiniciar()
                            }
                            binding.txtRankin.text = "$ " + money.toString()
                        }
                        txtOpcD.setOnClickListener {
                            if (txtOpcD.text == respCorrect) {
                                txtOpcD.setBackgroundColor(Color.parseColor("#00FF00"))
                                txtOpcA.isEnabled = false
                                txtOpcB.isEnabled = false
                                txtOpcC.isEnabled = false
                                money += 1000
                                MostrarPregunta(
                                    listPreguntas,
                                    this.txtOpcD
                                )
                            } else {
                                txtOpcD.setBackgroundColor(Color.parseColor("#FF0000"))
                                txtOpcA.isEnabled = false
                                txtOpcB.isEnabled = false
                                txtOpcC.isEnabled = false
                                Reiniciar()
                            }
                            binding.txtRankin.text = "$ " + money.toString()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun Reiniciar() {
        Menu_Categorias_Activity.listCategorias = mutableListOf()
        money = 0
        binding.apply {
            txtOpcA.isEnabled = true
            txtOpcB.isEnabled = true
            txtOpcC.isEnabled = true
            txtOpcD.isEnabled = true
        }
        startActivity(Intent(this,Menu_Categorias_Activity::class.java))
    }

    fun MostrarPregunta(
        listPregunt: MutableList<Preguntas>,
        textView: TextView
    ) {
        var numalt = Random
        var num = numalt.nextInt(1..listPregunt.size)
        Handler().postDelayed({
            num1 = numalt.nextInt(0..3)
            num2 = numalt.nextInt(0..3)
            while (num1 == num2) {
                num2 = numalt.nextInt(0..3)
            }
            num3 = numalt.nextInt(0..3)
            while (num3 == num2 || num3 == num1) {
                num3 = numalt.nextInt(0..3)
            }
            num4 = numalt.nextInt(0..3)
            while (num4 == num3 || num4 == num2 || num4 == num1) {
                num4 = numalt.nextInt(0..3)
            }
            binding.apply {
                txtPregunta.text = listPregunt[num].pregunta
                respCorrect = listPregunt[num].respuestCorrct
                textView.setBackgroundResource(R.drawable.fondo_opc)
                txtOpcA.text = listPregunt[num].listaOpciones[num1].respuesta
                txtOpcB.text = listPregunt[num].listaOpciones[num2].respuesta
                txtOpcC.text = listPregunt[num].listaOpciones[num3].respuesta
                txtOpcD.text = listPregunt[num].listaOpciones[num4].respuesta
                txtOpcA.isEnabled = true
                txtOpcB.isEnabled = true
                txtOpcC.isEnabled = true
                txtOpcD.isEnabled = true
            }
        }, 2000)


    }
}
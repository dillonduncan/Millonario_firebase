package com.example.millonariofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.millonariofirebase.Adaptadores.AdaptadorCategoriasRegistro
import com.example.millonariofirebase.Adaptadores.CategoriaRegistroListener
import com.example.millonariofirebase.Model.Categorias
import com.example.millonariofirebase.databinding.ActivityRegistrarCategoriasBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class Registrar_Categorias_Activity : AppCompatActivity(), CategoriaRegistroListener {
    lateinit var binding: ActivityRegistrarCategoriasBinding
    lateinit var database: FirebaseDatabase
    lateinit var adaptador: AdaptadorCategoriasRegistro
    lateinit var categoriasRef: DatabaseReference
    lateinit var categorias: Categorias
    var idActualizar: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()
        categoriasRef = database.getReference("Categorias")
        // InsertarCategorias()
        binding.rvCategoriasRegistro.layoutManager = LinearLayoutManager(this)
        //  ObtenerCategorias()
        //GetCategorias()]
        binding.btnAgregarCategoria.setOnClickListener {
            AgregarCategorias(
                Categorias(
                    "",
                    binding.edtNombreCategoria.text.toString()
                )
            )
        }
        binding.btnMotrarCategorias.setOnClickListener { GetCategorias() }
    }

    private fun AgregarCategorias(categorias: Categorias) {
        if (binding.edtNombreCategoria.text.isNullOrEmpty()) {
            binding.edtNombreCategoria.error = "Campo obligatorio"
        } else if (binding.edtNombreCategoria.text!!.isNotEmpty() && binding.btnAgregarCategoria.text.equals("Agregar")) {
            var id = categoriasRef.push().key
            categorias.id = id ?: ""
            categoriasRef.child(id ?: "").setValue(categorias)
            Toast.makeText(
                this@Registrar_Categorias_Activity,
                "Registro exitoso",
                Toast.LENGTH_SHORT
            ).show()
            LimpiarCampos()
            GetCategorias()
        } else if (binding.btnAgregarCategoria.text.equals("Actualizar")) {
            ActualizarCategoria(
                Categorias(
                    idActualizar,
                    binding.edtNombreCategoria.text.toString()
                )
            )
        }
    }

    fun LimpiarCampos() {
        categorias.id = ""
        categorias.nombre = ""
        binding.edtNombreCategoria.setText("")
        if (binding.btnAgregarCategoria.text!!.equals("Actualizar")) {
            binding.btnAgregarCategoria.setText("Agregar")
        }
    }

    fun GetCategorias() {
        categoriasRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listCategorias: MutableList<Categorias> = mutableListOf()
                snapshot.children.forEach { snap ->
                    listCategorias.add(snap.getValue(Categorias::class.java) ?: Categorias("", ""))
                }
                lifecycleScope.launch {
                    adaptador = AdaptadorCategoriasRegistro(
                        listCategorias,
                        this@Registrar_Categorias_Activity
                    )
                    binding.rvCategoriasRegistro.adapter = adaptador
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun ActualizarCategoria(categorias: Categorias) {
        var id = categorias.id
        var Actlzcategoria = HashMap<String, Any>()
        Actlzcategoria["nombre"] = categorias.nombre
        categoriasRef.child(id).setValue(categorias)
        LimpiarCampos()
    }

    override fun onDeleteItemClick(categorias: Categorias) {
        var id = "NiBo39wSdYD_vkTAAh4"
        categoriasRef.child(id).removeValue()
        GetCategorias()
    }

    override fun onEditItemClick(categorias: Categorias) {
        binding.btnAgregarCategoria.setText("Actualizar")
        this.categorias = categorias
        binding.edtNombreCategoria.setText(this.categorias.nombre)
        idActualizar = "${this.categorias.id}"
    }
}
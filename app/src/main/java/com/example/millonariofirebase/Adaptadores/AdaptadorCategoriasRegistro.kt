package com.example.millonariofirebase.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.millonariofirebase.Model.Categorias
import com.example.millonariofirebase.R
import com.google.firebase.database.DataSnapshot

class AdaptadorCategoriasRegistro(
    val listCategorias: MutableList<Categorias>,
    val listener: CategoriaRegistroListener
) : RecyclerView.Adapter<AdaptadorCategoriasRegistro.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vista=LayoutInflater.from(parent.context).inflate(R.layout.ly_mostrar_categorias_registro,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int =listCategorias.toList().size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var categoria=listCategorias.toList()[position]
        holder.txtId.text=categoria.id
        holder.txtCategoriaName.text=categoria.nombre
        holder.btnBorrarCategoria.setOnClickListener {
            listener.onDeleteItemClick(categoria)
        }
        holder.cvCategoriasRegistro.setOnClickListener {
            listener.onEditItemClick(categoria)
        }
    }
    inner class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView) {
        val cvCategoriasRegistro=ItemView.findViewById<CardView>(R.id.cvCategoriasRegistro)
        val txtId=ItemView.findViewById<TextView>(R.id.txtIdCategoria)
        val txtCategoriaName=ItemView.findViewById<TextView>(R.id.txtNombreCategoria)
        val btnBorrarCategoria=ItemView.findViewById<Button>(R.id.btnBorrarCategoria)
    }
}
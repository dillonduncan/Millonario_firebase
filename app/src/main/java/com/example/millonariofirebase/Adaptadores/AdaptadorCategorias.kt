package com.example.millonariofirebase.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.millonariofirebase.Model.Categorias
import com.example.millonariofirebase.R

class AdaptadorCategorias(val listaCategorias: MutableList<Categorias>) :
    RecyclerView.Adapter<AdaptadorCategorias.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vista=LayoutInflater.from(parent.context).inflate(R.layout.ly_mostrar_categorias,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int =listaCategorias.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categorias=listaCategorias[position]
        holder.cbCategorias.setText(categorias.nombre)
    }
    inner class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView) {
        val cvCategorias=itemView.findViewById<CardView>(R.id.cvCategorias)
        val cbCategorias=itemView.findViewById<CheckBox>(R.id.cbCategorias)
    }
}
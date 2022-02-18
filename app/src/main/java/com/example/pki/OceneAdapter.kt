package com.example.pki

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView

class OceneAdapter(private  val context: Activity, private val list:ArrayList<Ocene>):
    ArrayAdapter<Ocene>(context, R.layout.ocene_adapter, list) {
    val books = getBooks(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = inflater.inflate(R.layout.ocene_adapter, null)
        val autor : TextView = view.findViewById(R.id.textView)
        val rBar : RatingBar = view.findViewById(R.id.ratingBarId)


        autor.text = list[position].username
        rBar.rating= list[position].ocena.toFloat()
        return view
    } }
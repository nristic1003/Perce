package com.example.pki

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class KomentariAdapter(private  val context: Activity, private val list:ArrayList<Komentari>):
    ArrayAdapter<Komentari>(context, R.layout.adapter_komentari, list) {
    val books = getBooks(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = inflater.inflate(R.layout.adapter_komentari, null)
        val autor : TextView = view.findViewById(R.id.textView)
        val komentar : TextView = view.findViewById(R.id.textView2)
        val datum : TextView = view.findViewById(R.id.textView3)


        autor.text = list[position].username
        komentar.text = list[position].komentar
        datum.text = list[position].datum

        return view
    } }

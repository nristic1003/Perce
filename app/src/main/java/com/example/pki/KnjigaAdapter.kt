package com.example.pki

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class KnjigaAdapter(private  val context: Activity, private val list:ArrayList<Knjiga>):ArrayAdapter<Knjiga>(context, R.layout.adapter_view_layout, list) {
    private lateinit var slika: ImageView
    private lateinit var naslov: TextView
    private lateinit var autor: TextView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = inflater.inflate(R.layout.adapter_view_layout, null)
        val imageView : ImageView = view.findViewById(R.id.imageView)
        val naslov : TextView = view.findViewById(R.id.naslovKnjige)
        val autor : TextView = view.findViewById(R.id.imeAutora)
        val popust : TextView = view.findViewById(R.id.popustKnjige)


        imageView.setImageResource(list[position].slika)
        naslov.text = list[position].naslov
        autor.text = list[position].autor
        if(list[position].promocija > 0)
        {
                popust.text =list[position].promocija.toString() + "%"
                popust.visibility = View.VISIBLE
        }
        return view
    }


}
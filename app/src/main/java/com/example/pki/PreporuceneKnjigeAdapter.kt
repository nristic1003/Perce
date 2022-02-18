package com.example.pki

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class PreporuceneKnjigeAdapter(private  val context: Activity, private val list:ArrayList<Preporucene>):
    ArrayAdapter<Preporucene>(context, R.layout.adapter_view_layout, list) {
    val books = getBooks(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = inflater.inflate(R.layout.adapter_preporucene, null)
        val imageView : ImageView = view.findViewById(R.id.imageView)
        val naslov : TextView = view.findViewById(R.id.naslovPreporuceneKnjige)
        val autor : TextView = view.findViewById(R.id.imePreporucenogAutora)
        val preporucio : TextView = view.findViewById(R.id.imePreporucioca)
        val popust : TextView = view.findViewById(R.id.popustKnjige)
        val book = books.find { it.id == list[position].bookId }

        if (book != null) {
            imageView.setImageResource(book.slika)
        }
        if (book != null) {
            naslov.text = book.naslov
        }
        if (book != null) {
            autor.text = book.autor
        }
        if (book != null && book.promocija > 0) {
            popust.text =book.promocija.toString() + "%"
            popust.visibility = View.VISIBLE
        }

        preporucio.text = "Preporucio: " +  list[position].fromUsername
        return view
    }


}
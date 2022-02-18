package com.example.pki

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.example.pki.databinding.ActivityDetaljiKnjigaBinding
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList

class DetaljiKnjigaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetaljiKnjigaBinding
    val usernames = mutableListOf<String>()
    var preporuka: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetaljiKnjigaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.log)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))


        binding.listViewDetalji.isEnabled()

        val users = getUsers(this).toTypedArray()
        val currentUser = getCurrentUser(this)

        users.forEachIndexed { index, user ->
            usernames.add(user.username)
        }


        val knjiga = intent.getStringExtra("knjiga")
        var k = Gson().fromJson(knjiga, Knjiga::class.java) //KNJIGA kao objekat

        if(k.promocija > 0)
        {
            binding.popustKnjige.text = k.promocija.toString() + "%"
            binding.popustKnjige.visibility = View.VISIBLE
        }

        binding.detaljislika.setImageResource(k.slika)
        binding.detaljiAutor.text = "Autor:" + k.autor
        binding.naslovDetalji.text = "Naslov:" + k.naslov
        binding.detaljiOcena.text = "Ocena:" + calcOcena(k.ocene).toString()
        binding.detaljiGodina.text = "Godina:" + k.godina.toString()


        binding.dugmePreporuka.setOnClickListener {

            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Izaberite korisnika")

            val view = LayoutInflater.from(this).inflate(R.layout.custom_sppiner, null)
            alertDialogBuilder.setView(view)
            val spinner: Spinner = view.findViewById(R.id.spinner)
            val alertDialog = alertDialogBuilder.create()
            spinner.adapter = ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                usernames
            )
            val button: Button = view.findViewById(R.id.preporuciKnjigu)
            button.setOnClickListener {
                val prep = Preporucene(1, k.id, preporuka, currentUser.username)
                preporuci(prep)
                Toast.makeText(this, "Uspesno preporucena knjiga!", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()

            }
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    preporuka = usernames[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }


            alertDialog.show()

        }

        binding.dugmeOcena.setOnClickListener {
            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Izaberite ocenu")
            val view = LayoutInflater.from(this).inflate(R.layout.star_rating, null)
            alertDialogBuilder.setView(view)
            val r: RatingBar = view.findViewById(R.id.rBar)
            val button = view.findViewById<Button>(R.id.button)
            val alertDialog = alertDialogBuilder.create()
            button.setOnClickListener {
                println(r.rating.toString())
                if(r.rating.toInt()!=0)
                {
                    if( k.ocene.find { it.username == getCurrentUser(this).username}==null)
                    {
                        val books = getBooks(this)
                        val ocena = Ocene(getCurrentUser(this).username, r.rating.toInt())

                        books[k.id - 1].ocene.add(ocena)
                        k.ocene =  books[k.id - 1].ocene
                        println(k.ocene)
                        saveBooks(books)
                        Toast.makeText(this, "Ocena dodata", Toast.LENGTH_SHORT).show()
                        binding.detaljiOcena.text = "Ocena:" + calcOcena(k.ocene).toString()
                        alertDialog.dismiss()
                    }else
                    {
                        Toast.makeText(this, "Vec ste ocenili knjigu!", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(this, "Izaberite ocenu!", Toast.LENGTH_SHORT).show()
                }

            }

            alertDialog.show()


        }

        binding.dugmeKomentar.setOnClickListener {
            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(R.layout.komentar_layout, null)
            alertDialogBuilder.setView(view)
            val t = view.findViewById<EditText>(R.id.tekstKomentara)
            val button = view.findViewById<Button>(R.id.buttonKomentar)
            val alertDialog = alertDialogBuilder.create()
            button.setOnClickListener {
                println(t.text.toString())
                if(!t.text.toString().equals(""))
                {
                    val books = getBooks(this)
                    val date = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("dd.MM.yyyy") //or use getDateInstance()
                    val formatedDate = formatter.format(date)
                    println(formatedDate)
                    val kom = Komentari(getCurrentUser(this).username, t.text.toString(), formatedDate)
                    books[k.id - 1].komentari.add(kom)
                    k.komentari =  books[k.id - 1].komentari
                    saveBooks(books)
                    Toast.makeText(this, "Komentar dodat", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }else
                {
                    Toast.makeText(this, "Unesite komentar", Toast.LENGTH_SHORT).show()
                }

            }

            alertDialog.show()

        }


        binding.prikaziKomentare.setOnClickListener {
            binding.tektPrikaza.text ="Komentari"
            binding.tektPrikaza.visibility = View.VISIBLE
            val listKomentari = ArrayList<Komentari>(k.komentari)
            println(k.komentari)
            binding.listViewDetalji.adapter = KomentariAdapter(this, listKomentari)
            binding.listViewDetalji.visibility = View.VISIBLE
            binding.tekstOpisa.visibility = View.GONE
            binding.naslovOpisa.visibility = View.GONE
        }

        binding.prikaziOcene.setOnClickListener {
            binding.tektPrikaza.text ="Ocene"
            binding.tektPrikaza.visibility = View.VISIBLE
            val listOcene = ArrayList<Ocene>(k.ocene)
            binding.listViewDetalji.adapter = OceneAdapter(this, listOcene)
            binding.tekstOpisa.visibility = View.GONE
            binding.naslovOpisa.visibility = View.GONE
            binding.listViewDetalji.visibility = View.VISIBLE
        }

        binding.prikaziOpis.setOnClickListener {
            binding.tektPrikaza.text = "Opis"
            binding.tektPrikaza.visibility = View.VISIBLE
            binding.naslovOpisa.text = "O knjizi: " + k.naslov
            binding.naslovOpisa.typeface = Typeface.DEFAULT_BOLD
            binding.naslovOpisa.visibility = View.VISIBLE
            binding.tekstOpisa.text =  k.opis
            binding.tekstOpisa.visibility = View.VISIBLE
            binding.listViewDetalji.visibility = View.GONE

        }
    }


    private fun calcOcena(ocene: MutableList<Ocene>): Double {

        val x = ocene.sumOf{ it.ocena }.toDouble()
        if(x==0.0) return 0.0
        val res : Double =x/ocene.count()
        return String.format("%.2f", res).toDouble()
    }

    private fun preporuci(p: Preporucene) {
        val sharedPref = getSharedPreferences("users", Context.MODE_PRIVATE)
        val jsonPreporucene = sharedPref.getString("preporucene", null);
        println(jsonPreporucene)
        var l = mutableListOf<Preporucene>()
        if (jsonPreporucene == null)
            l.add(p)
        else {
            l = Gson().fromJson(jsonPreporucene, Array<Preporucene>::class.java).toMutableList()
            l.add(p)
        }


        val jsonString = Gson().toJson(l)
        with(sharedPref.edit())
        {
            putString("preporucene", jsonString)
            apply()
        }

        println(sharedPref.getString("preporucene", null))

    }

    private fun saveBooks(books: List<Knjiga>) {
        val sharedPref = getSharedPreferences("users", Context.MODE_PRIVATE)
        val jsonString = Gson().toJson(books)

        with(sharedPref.edit())
        {
            putString("knjige", jsonString)
            apply()

        }
        val data = sharedPref.getString("knjige", null)
        println(data)


    }


}
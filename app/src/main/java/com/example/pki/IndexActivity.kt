package com.example.pki

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pki.databinding.ActivityIndexBinding
import com.google.gson.Gson



class IndexActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIndexBinding
    var list = arrayListOf<Knjiga>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.log)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))

       /*  val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE)
        sharedPref.edit().remove("knjige").apply()*/
        list = addBooks();

        println("ON Creaet called")

       // val myAdapter = KnjigaAdapter(this,list)
        binding.listView.adapter = KnjigaAdapter(this, list)
        binding.listView.setOnItemClickListener { parent, view, position, id ->

            val knjiga = list[position];
            val i = Intent(this, DetaljiKnjigaActivity::class.java)
            val k = Gson().toJson(knjiga)
            println(knjiga.ocene)
            i.putExtra("knjiga" , k)
            startActivity(i)

        }

        var bottomNav = binding.bottomNavigation
        bottomNav.selectedItemId = R.id.home

        bottomNav?.setOnItemSelectedListener {

            when(it.itemId)
            {
                R.id.suggestions -> {
                    var intent = Intent(this, PreporuceneActivity::class.java).apply {   };
                    startActivity(intent)

                }
                R.id.logout ->{

                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Da li ste sigurni da zelite da se odjavite?")
                    alertDialogBuilder.setPositiveButton("Odjavi me", {dialogInterface: DialogInterface, i:Int->
                        println("Logout")//LOGOUT
                        logout(this)
                    })

                    alertDialogBuilder.setNegativeButton("Nazad", { dialogInterface: DialogInterface, i: Int ->
                        println("Nazad")//BACK
                    })
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                R.id.userInfo->{
                    var intent = Intent(this, UserInfoActivity::class.java);
                    startActivity(intent)
                }

            }


            return@setOnItemSelectedListener true
        }


    }

    override fun onResume() {
        super.onResume()
        println("Resume called")
         list = addBooks();
    }

    private fun addBooks() : ArrayList<Knjiga>
    {


        val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE)

        if(sharedPref.getString("knjige" , null)==null)
        {
            val list = ArrayList<Knjiga>()
            list.add(   Knjiga(1,"Covek i njegovi simboli", "Karl Gustav Jung", 2018, 5,280,"Test Opisa", 0,
                mutableListOf(), mutableListOf(), R.drawable.covek))
            list.add( Knjiga(2,"Tako je govorio Zaratrusta", "Fridrih Nice", 2018, 5,280,"Test Opisa", 20,
                mutableListOf(), mutableListOf(), R.drawable.image1))

            list.add(Knjiga(3,"Suton Idola", "Fridrih Nice", 2018, 5,280,"Test Opisa", 0,
                mutableListOf(), mutableListOf(), R.drawable.suton))
            list.add(Knjiga(4,"MAJSTOR I MARGARITA", "Mihail Bulgakov", 2018, 5,280,"opis", 10,
                mutableListOf(), mutableListOf(), R.drawable.majstor))


            val jsonString = Gson().toJson(list);
            with(sharedPref.edit())
            {
                putString("knjige" , jsonString)
                apply()
            }

            return list
        }else
        {
            val a  = getBooks(this)
            val b  = ArrayList<Knjiga>(a)
            return b
        }

    }




}
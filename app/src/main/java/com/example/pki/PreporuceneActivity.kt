package com.example.pki

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.pki.databinding.ActivityPreporuceneBinding
import com.google.gson.Gson

class PreporuceneActivity : AppCompatActivity() {
    lateinit var binding : ActivityPreporuceneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreporuceneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.log)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))
        var bottomNav = binding.bottomNavigation
        bottomNav.selectedItemId = R.id.suggestions

        bottomNav?.setOnItemSelectedListener {

            when(it.itemId)
            {
                R.id.home -> {
                    var intent = Intent(this, IndexActivity::class.java);
                    startActivity(intent)

                }
                R.id.logout ->{

                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Da li ste sigurni da zelite da se odjavite?")
                    alertDialogBuilder.setPositiveButton("Odjavi me", { dialogInterface: DialogInterface, i:Int->
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

        var prep = getPreporucene()
        val user = getCurrentUser(this)
        prep = prep.filter { it.forUsername ==user.username }


        val list = ArrayList<Preporucene>(prep)

        val myAdapter = PreporuceneKnjigeAdapter(this,list)
        binding.listView.adapter = PreporuceneKnjigeAdapter(this, list)
        binding.listView.setOnItemClickListener { parent, view, position, id ->

            val knjiga = getBookById(this,  list[position].bookId);
            val i = Intent(this, DetaljiKnjigaActivity::class.java)
            val k = Gson().toJson(knjiga)
            i.putExtra("knjiga" , k)
            startActivity(i)

        }



    }

    private fun getPreporucene() : List<Preporucene>
    {
        val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE);
        val jsonString = sharedPref.getString("preporucene", null);
        return if(jsonString!=null)
        {
            val objectList = Gson().fromJson(jsonString, Array<Preporucene>::class.java).asList();
            return objectList;
        }

        else
            listOf()
    }
    }






package com.example.pki

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pki.databinding.ActivityMainBinding
import com.google.gson.Gson



class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.log)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))

        initUsers();


        binding.prijava.setOnClickListener()
        {
            var username : String;
            var password : String;
            username = binding.korIme.text.toString();
            password = binding.lozinka.text.toString();
            val users = getUsers(this);

            var u = users.find { it.username ==username }
            if(u==null) Toast.makeText(this, "Koriscniko ime ili lozinka su pogresni", Toast.LENGTH_SHORT)
                    .show()
            else if(u.password==password )
            {

                login(this,u)
                var intent = Intent(this, IndexActivity::class.java).apply { putExtra("message" , "test123") };
                startActivity(intent);


            }else Toast.makeText(this, "Koriscniko ime ili lozinka su pogresni", Toast.LENGTH_SHORT)
                .show()

        }
        binding.kreirajNalog.setOnClickListener()
        {
            var intent = Intent(this, RegistracijaActivity::class.java).apply { };
            startActivity(intent);
        }

    }


    private fun initUsers()
    {
        println("init called")
        val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE);
        if(sharedPref.getString("users", null)==null)
        {
            val usersToSave = listOf<User>(
                User(1,"nikola123", "123", "Nikola", "Ristic",
                    "Bulevara Kralja Aleksandra 73", "064123456", false),
                User(2,"admin", "admin", "N", "R",
                    "X", "064123456", true))
            val jsonString = Gson().toJson(usersToSave);
            with(sharedPref.edit())
            {
                putString("users" , jsonString)
                apply()
            }

        }
        println(sharedPref.getString("users", null))
    }




}
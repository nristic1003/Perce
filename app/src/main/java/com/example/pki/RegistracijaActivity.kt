package com.example.pki

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.pki.databinding.ActivityRegistracijaBinding
import com.google.gson.Gson


class RegistracijaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistracijaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistracijaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.noviNalog.setOnClickListener()
        {
            val username = binding.username.text.toString();
            val password = binding.password.text.toString();
            val firstName = binding.firstname.text.toString();
            val lastName = binding.lastname.text.toString();
            val contact = binding.contact.text.toString();
            val address = binding.address.text.toString();
            var id = 3;
            var user = User(id , username, password,firstName, lastName, address, contact, false );
            var u = getUsers(this).toMutableList();
            addNewUser(user, u);
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            Toast.makeText(this, "Nalog uspesno kreiran!", Toast.LENGTH_SHORT).show()

        }

    }

    private fun addNewUser( u: User, l : MutableList<User> )
    {
        val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE);
        l.add(u);
        val jsonString = Gson().toJson(l);
        with(sharedPref.edit())
        {
            putString("users", jsonString)
            apply();
        }
        val js = sharedPref.getString("users", null);
        println("Stampam ovde " + js)

    }



}
package com.example.pki

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pki.databinding.ActivityUserInfoBinding
import com.google.gson.Gson

class UserInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.log)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.DKGRAY))
        var u = getUser()

        binding.firstname.append(u.name)
        binding.lastname.append(u.lastname)
        binding.username.append(u.username)
        binding.password.append(u.password)
        binding.contact.append(u.contact)

        binding.userInfoSave.setOnClickListener {
            u.name = binding.firstname.text.toString()
            u.lastname = binding.lastname.text.toString()
            u.username = binding.username.text.toString()
            u.password = binding.password.text.toString()
            u.contact = binding.contact.text.toString()
            saveUser(u)
        }

        var bottomNav = binding.bottomNavigation
        bottomNav.selectedItemId = R.id.userInfo

        bottomNav?.setOnItemSelectedListener {

            when(it.itemId)
            {
                R.id.home -> {
                    var intent = Intent(this, IndexActivity::class.java).apply {  };
                    startActivity(intent)

                }
                R.id.logout ->{

                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Da li ste sigurni da zelite da se odjavite?")
                    alertDialogBuilder.setPositiveButton("Odjavi me", { dialogInterface: DialogInterface, i:Int->
                        println("Logout")//LOGOUT
                        logout()

                    })

                    alertDialogBuilder.setNegativeButton("Nazad", { dialogInterface: DialogInterface, i: Int ->
                        println("Nazad")//BACK
                    })
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                R.id.suggestions->{
                    var intent = Intent(this, PreporuceneActivity::class.java);
                    startActivity(intent)
                }

            }


            return@setOnItemSelectedListener true
        }


    }

    private fun logout()
    {
        val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString("currentUser", null)
        val u = Gson().fromJson(jsonString, User::class.java)
        with(sharedPref.edit())
        {
            remove("currentUser")
            apply()
        }

        var intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }



    private fun saveUser(user : User)
    {
        val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE)
        var jsonString = Gson().toJson(user)
        var jsonUsers = sharedPref.getString("users", null)
        var objectList = Gson().fromJson(jsonUsers, Array<User>::class.java).asList();

        val x : User  = objectList.filter { it.id == user.id }.first()
        val i = x.id-1

        val oldUsername = objectList[i].username

        objectList[i].contact = user.contact
        objectList[i].username = user.username
        objectList[i].name = user.name
        objectList[i].password = user.password
        objectList[i].lastname = user.lastname
        jsonUsers = Gson().toJson(objectList)
     //   println(jsonUsers)

        val l = getSuggestedBooks(this)



        var mut = mutableListOf<Preporucene>()
        mut = l.toMutableList()
        println("old username " + oldUsername)
        println("door " + mut[0].forUsername)

        mut.forEachIndexed { index, preporucena ->
            if(mut[index].forUsername==oldUsername)
            {
                mut[index].forUsername=user.username
            }
            if(mut[index].fromUsername ==oldUsername)
            {
                mut[index].fromUsername =user.username
            }
        }

        var jsonPreporucene = Gson().toJson(mut)


        with(sharedPref.edit())
        {
            putString("currentUser", jsonString)
            putString("users", jsonUsers)
            putString("preporucene" , jsonPreporucene)
            apply()
        }
        Toast.makeText(this, "Sacuvano", Toast.LENGTH_SHORT).show()

    }



    private fun getUser():User
    {
        val sharedPref = getSharedPreferences("users" , Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString("currentUser", null)
        val u = Gson().fromJson(jsonString, User::class.java)
        return u

    }

}
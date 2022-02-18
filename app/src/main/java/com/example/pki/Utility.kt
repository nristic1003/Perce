package com.example.pki

import android.content.Context
import android.content.Intent
import com.google.gson.Gson

    fun login(c : Context, u :User ) {
        val sharedPref = c.getSharedPreferences("users" , Context.MODE_PRIVATE)
        val jsonString = Gson().toJson(u)
        println(jsonString)
        with(sharedPref.edit())
        {
            putString("currentUser", jsonString)
            apply()
        }
    }

    fun logout(c : Context) {
    val sharedPref = c.getSharedPreferences("users" , Context.MODE_PRIVATE)
    val jsonString = sharedPref.getString("currentUser", null)
    val u = Gson().fromJson(jsonString, User::class.java)
    with(sharedPref.edit())
    {
        remove("currentUser")
        apply()
    }

    var intent = Intent(c, MainActivity::class.java);
    c.startActivity(intent);
    }

    fun getUsers(c : Context):List<User>{
        val sharedPref = c.getSharedPreferences("users" , Context.MODE_PRIVATE);
        val jsonString = sharedPref.getString("users", null);
        return if(jsonString!=null)
        {
            val objectList = Gson().fromJson(jsonString, Array<User>::class.java).asList();
            return objectList;
        }

        else
            listOf()
        }

    fun getBooks(c:Context):List<Knjiga>
    {
        val sharedPref = c.getSharedPreferences("users" , Context.MODE_PRIVATE);
        val jsonString = sharedPref.getString("knjige", null);
        return if(jsonString!=null)
        {
            val objectList = Gson().fromJson(jsonString, Array<Knjiga>::class.java).asList();
            return objectList;
        }

        else
            listOf()
    }

    fun getSuggestedBooks(c:Context):List<Preporucene>
    {
        val sharedPref = c.getSharedPreferences("users" , Context.MODE_PRIVATE);
        val jsonString = sharedPref.getString("preporucene", null);
        return if(jsonString!=null)
        {
            val objectList = Gson().fromJson(jsonString, Array<Preporucene>::class.java).asList();
            return objectList;
        }

        else
            listOf()
    }

    fun getBookById(c : Context, bookId : Int) : Knjiga {
        val books = getBooks(c)
        return books.filter { it.id == bookId }.first()
    }

    fun getCurrentUser(c : Context): User {
    val sharedPref = c.getSharedPreferences("users" , Context.MODE_PRIVATE)
    val jsonString = sharedPref.getString("currentUser", null)
    val u = Gson().fromJson(jsonString, User::class.java)
    return u
}





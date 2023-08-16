package com.mentari.kantinitdel.database

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mentari.kantinitdel.model.User

class SharedPreferences(activity: Activity){
    val mypref = "MAIN_PRF"
    val sp:SharedPreferences
    val statusLogin = "Login"

    val user = "user"
    val name = "name"
    val no_telp = "no_telp"
    val no_ktp = "no_ktp"
    val email = "email"

    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status:Boolean){
        sp.edit().putBoolean(statusLogin, status).apply()
    }

    fun getStatusLogin():Boolean{
        return sp.getBoolean(statusLogin, false)
    }

    fun setString(key:String,value:String){
        sp.edit().putString(key, value).apply()
    }

    fun setUser(value:User){
        val data:String = Gson().toJson(value, User::class.java)
        sp.edit().putString(user, data).apply()
    }

    fun getUser() : User? {
        val data:String = sp.getString(user, null) ?: return null
        val json = Gson().fromJson<User>(data, User::class.java)
        return json
    }

    fun getString(key:String): String{
        return sp.getString(key, "")!!
    }
}
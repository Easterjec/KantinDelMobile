package com.mentari.kantinitdel.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.SharedPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var btnLogin:Button
    private lateinit var btnRegistrasi:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sp = SharedPreferences(this)
        btnLogin = findViewById(R.id.btnMasuk)
        btnRegistrasi = findViewById(R.id.btnDaftar)

        mainButton()

    }

    private fun mainButton(){
        btnLogin.setOnClickListener{
            startActivity(Intent(this, MasukActivity::class.java))
        }

        btnRegistrasi.setOnClickListener{
            startActivity(Intent(this, DaftarActivity::class.java))
        }
    }
}
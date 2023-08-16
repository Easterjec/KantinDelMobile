package com.mentari.kantinitdel.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mentari.kantinitdel.MainActivity
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.model.ResponseMenu
import com.mentari.kantinitdel.model.User
import kotlinx.android.synthetic.main.activity_daftar.*
import kotlinx.android.synthetic.main.activity_masuk.*
import kotlinx.android.synthetic.main.activity_masuk.pb
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MasukActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        sp = SharedPreferences(this)

        btnLogin.setOnClickListener{
            login()
    }
}

    fun login() {
        if (edit_emailLogin.text.isEmpty()) {
            edit_emailLogin.error = "Kolom email tidak boleh kosong"
            edit_emailLogin.requestFocus()
            return
        } else if (edit_passwordLogin.text.isEmpty()) {
            edit_passwordLogin.error = "Kolom password tidak boleh kosong"
            edit_passwordLogin.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.login(edit_emailLogin.text.toString(), edit_passwordLogin.text.toString()).enqueue(object :
            Callback<ResponseMenu> {
            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                pb.visibility = View.GONE
                val respons = response.body()!!
                if (respons.success == 1){
                    sp.setStatusLogin(true)
                    sp.setUser(respons.user)
                    val intent = Intent(this@MasukActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@MasukActivity, "Selamat datang " + respons.user.name, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@MasukActivity, "Error: "+respons.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@MasukActivity, "Error: "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
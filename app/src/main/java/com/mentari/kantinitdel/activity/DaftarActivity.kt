package com.mentari.kantinitdel.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mentari.kantinitdel.MainActivity
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.model.ResponseMenu
import kotlinx.android.synthetic.main.activity_daftar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarActivity : AppCompatActivity() {

    private lateinit var btnDaftar: Button
    private lateinit var edit_nama: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_notelp: EditText
    private lateinit var edit_noktp: EditText
    private lateinit var edit_password: EditText
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)


        sp = SharedPreferences(this)

        btnDaftar = findViewById(R.id.btnRegister)
        edit_nama = findViewById(R.id.edit_nama)
        edit_email = findViewById(R.id.edit_email)
        edit_notelp = findViewById(R.id.edit_notelp)
        edit_noktp = findViewById(R.id.edit_noktp)
        edit_password = findViewById(R.id.edit_password)

        btnDaftar.setOnClickListener {
            registrasi()
        }

    }



    fun registrasi() {
        if (edit_nama.text.isEmpty()) {
            edit_nama.error = "Kolom nama tidak boleh kosong"
            edit_nama.requestFocus()
            return
        } else if (edit_email.text.isEmpty()) {
            edit_email.error = "Kolom email tidak boleh kosong"
            edit_email.requestFocus()
            return
        } else if (edit_notelp.text.isEmpty()) {
            edit_notelp.error = "Kolom nomor telepon tidak boleh kosong"
            edit_notelp.requestFocus()
            return
        } else if (edit_noktp.text.isEmpty()) {
            edit_noktp.error = "Kolom nomor telepon tidak boleh kosong"
            edit_noktp.requestFocus()
            return
        } else if (edit_password.text.isEmpty()) {
            edit_password.error = "Kolom password tidak boleh kosong"
            edit_password.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.registrasi(edit_nama.text.toString(), edit_email.text.toString(), edit_noktp.text.toString(), edit_notelp.text.toString(), edit_password.text.toString()).enqueue(object : Callback<ResponseMenu>{
            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                pb.visibility = View.GONE
                val respons = response.body()!!
                if (respons.success == 1){
                    sp.setStatusLogin(true)
                    sp.setUser(respons.user)
                    val intent = Intent(this@DaftarActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@DaftarActivity, "Success: "+respons.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DaftarActivity, "Error: "+respons.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@DaftarActivity, "Error: "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

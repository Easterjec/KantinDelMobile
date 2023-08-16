package com.mentari.kantinitdel.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mentari.kantinitdel.MainActivity
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.adapter.AdapterRiwayatSnack
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.model.PemesananSnack
import com.mentari.kantinitdel.model.ResponseMenu
import kotlinx.android.synthetic.main.activity_riwayat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)
    }

    fun getRiwayat() {
        val id = SharedPreferences(this).getUser()!!.id_user

        ApiConfig.instanceRetrofit.getRiwayatSnack(id).enqueue(object : Callback<ResponseMenu> {
            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                val res = response.body()!!
                if (res.success == 1) {
                    displayRiwayatSnack(res.pesansnacks)
                }
            }
        })
    }

    fun displayRiwayatSnack(pemesanansnack: ArrayList<PemesananSnack>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_riwayatsnack.adapter = AdapterRiwayatSnack(pemesanansnack, object : AdapterRiwayatSnack.Listeners {
            override fun onClicked(data: PemesananSnack) {
                val json = Gson().toJson(data, PemesananSnack::class.java)
                val intent = Intent(this@RiwayatActivity, RiwayatSnackDetail::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }
        })
        rv_riwayatsnack.layoutManager = layoutManager

    }

    override fun onResume() {
        getRiwayat()
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

}
package com.mentari.kantinitdel.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.model.PesanMakanan
import com.mentari.kantinitdel.model.PesanMinuman
import com.mentari.kantinitdel.model.ResponseMenu
import com.mentari.kantinitdel.room.MyDatabase
import com.mentari.kantinitdel.room.MyDatabaseMinuman
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengirimanMakananActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    var totalHarga = 0

    private lateinit var edt_nama : EditText
    private lateinit var edt_phone : EditText
    private lateinit var edt_catatan : EditText
    private lateinit var tv_total : TextView
    private lateinit var edt_total : EditText
    private lateinit var btn_pesan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman_makanan)
        edt_nama = findViewById(R.id.edt_nama)
        edt_phone = findViewById(R.id.edt_phone)
        edt_catatan = findViewById(R.id.edt_catatan)
        tv_total = findViewById(R.id.tv_total)
        edt_total = findViewById(R.id.edt_total)
        btn_pesan = findViewById(R.id.btn_pesan)


        myDb = MyDatabase.getInstance(this)!!

        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_total.text = Helper().gantiRupiah(totalHarga)

        mainButton()
    }

    private fun mainButton() {


        btn_pesan.setOnClickListener {
            bayar()
        }
    }

    private fun bayar() {
        if (totalHarga == Integer.valueOf(edt_total.text.toString())) {
        val user = SharedPreferences(this).getUser()!!
            intent.getStringExtra("jenis") == "makanan"
        val listMakanan = myDb.daoKeranjangMakanan().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        val makanans = ArrayList<PesanMakanan.Item>()
        for (p in listMakanan) {
            if (p.selected) {
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.harga))

                val makanan = PesanMakanan.Item()
                makanan.id_makanan = "" + p.id_makanan
                makanan.kuantitas = "" + p.jumlah
                makanan.total_harga = "" + (p.jumlah * Integer.valueOf(p.harga))
                makanans.add(makanan)
            }
        }

        val checkout = PesanMakanan()
        checkout.id_user = "" + user.id_user
        checkout.total_item = "" + totalItem
        checkout.nama_penerima = edt_nama.text.toString()
        checkout.nomor_hp = edt_phone.text.toString()
        checkout.catatan = edt_catatan.text.toString()
        checkout.total_pembayaran = edt_total.text.toString()
        checkout.makanan = makanans


        ApiConfig.instanceRetrofit.pesanmakanan(checkout).enqueue(object : Callback<ResponseMenu> {
            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                error(t.message.toString())
            }

            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                if (!response.isSuccessful) {
                    error(response.message())
                    return
                }
                val respon = response.body()!!
                if (respon.success == 1) {
                    val jsCheckout = Gson().toJson(checkout, PesanMakanan::class.java)
                    val intent1 =
                        Intent(this@PengirimanMakananActivity, PemesananSelesaiActivity::class.java)
                    intent1.putExtra("checkout", jsCheckout)
                    intent1.putExtra("extra", "" + totalHarga)
                    intent1.putExtra("jenis", "makanan")
                    startActivity(intent1)
                } else {
                    error(respon.message)
                    Toast.makeText(
                        this@PengirimanMakananActivity,
                        "Error:" + respon.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }else{
            Toast.makeText(this, "Total Pembayaran tidak sesuai" , Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

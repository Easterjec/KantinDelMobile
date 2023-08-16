package com.mentari.kantinitdel.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.mentari.kantinitdel.MainActivity
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.model.PesanMakanan
import com.mentari.kantinitdel.model.PesanSnack
import com.mentari.kantinitdel.room.MyDatabase
import com.mentari.kantinitdel.room.MyDatabaseSnack

class PemesananSelesaiActivity : AppCompatActivity() {
    private lateinit var tv_nominal : TextView
    private lateinit var btn_cekStatus : Button
    var nominal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan_selesai)

        tv_nominal = findViewById(R.id.tv_nominal)
        btn_cekStatus = findViewById(R.id.btn_cekStatus)

        setValues()
        mainButton()
    }

    fun mainButton() {

        btn_cekStatus.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }
    }

    fun setValues() {
        val jenis = intent.getStringExtra("jenis")

        if(jenis == "snack"){
            val jsCheckout = intent.getStringExtra("checkout")
            nominal = Integer.valueOf(intent.getStringExtra("extra")!!)
            tv_nominal.text = Helper().gantiRupiah(nominal)
            val PesanSnack = Gson().fromJson(jsCheckout, PesanSnack::class.java)
            // hapus keranjang
            val myDb = MyDatabaseSnack.getInstance(this)!!
            for (Snack in PesanSnack.snack){
                myDb.daoKeranjangSnack().deleteById(Snack.id_snack)
            }
        }else if(jenis == "makanan"){
            val jsCheckout = intent.getStringExtra("checkout")
            nominal = Integer.valueOf(intent.getStringExtra("extra")!!)
            tv_nominal.text = Helper().gantiRupiah(nominal)
            val PesanMakanan = Gson().fromJson(jsCheckout, PesanMakanan::class.java)
            // hapus keranjang
            val myDb = MyDatabase.getInstance(this)!!
            for (Makanan in PesanMakanan.makanan){
                myDb.daoKeranjangMakanan().deleteById(Makanan.id_makanan)
            }
//        }else if(jenis == "pulsa"){
//            nominal = Integer.valueOf(intent.getStringExtra("total_harga")!!)
//            tv_nominal.text = Helper().gantiRupiah(nominal)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}
package com.mentari.kantinitdel.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.model.Minuman
import com.mentari.kantinitdel.room.MyDatabaseMinuman
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.tv_harga
import kotlinx.android.synthetic.main.activity_detail.tv_nama
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailActivityMinuman : AppCompatActivity() {

    lateinit var minuman: Minuman
    lateinit var myDb: MyDatabaseMinuman

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_minuman)

        myDb = MyDatabaseMinuman.getInstance(this)!!

        getInfo()
        mainButton()
        checkKeranjang()
    }


    private fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjangMinuman().getMinuman(minuman.id_minuman)
            if (data == null) {
                insert()
            } else {
                data.jumlah =+ 1
                update(data)
            }
        }


        btn_toKeranjang.setOnClickListener{
            val idm = Intent(this, KeranjangMinumanActivity::class.java)
            startActivity(idm)
        }
    }

    private fun insert() {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMinuman().insert(minuman) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil menambah Produk", Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data: Minuman) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMinuman().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted update")
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang() {
        val dataKranjang = myDb.daoKeranjangMinuman().getAll()

        if (dataKranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        minuman = Gson().fromJson<Minuman>(data, Minuman::class.java)

        // set Value
        tv_nama.text = minuman.nama
        tv_harga.text = minuman.harga
        tv_harga.text = Helper().gantiRupiah(minuman.harga)
        tv_deskripsi.text = minuman.deskripsi
        stok.text = minuman.stok

        val img = "http://192.168.146.191/gbr/" + minuman.gambar
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.b2)
            .error(R.drawable.b2)
            .resize(400,400)
            .into(image)

        // setToolbar
        supportActionBar!!.title = minuman.nama
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
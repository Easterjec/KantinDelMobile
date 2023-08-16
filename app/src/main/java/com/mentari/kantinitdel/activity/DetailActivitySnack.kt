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
import com.mentari.kantinitdel.model.Snack
import com.mentari.kantinitdel.room.MyDatabaseSnack
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.tv_harga
import kotlinx.android.synthetic.main.activity_detail.tv_nama
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailActivitySnack : AppCompatActivity() {

    lateinit var snack: Snack
    lateinit var myDb: MyDatabaseSnack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_snack)

        myDb = MyDatabaseSnack.getInstance(this)!!

        getInfo()
        mainButton()
        checkKeranjang()
    }


    private fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjangSnack().getSnack(snack.id_snack)
            if (data == null) {
                insert()
            } else {
                data.jumlah =+ 1
                update(data)
            }
        }


        btn_toKeranjang.setOnClickListener{
            val intent = Intent(this, KeranjangSnackActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insert() {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangSnack().insert(snack) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil menambah Produk", Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data: Snack) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangSnack().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted update")
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang() {
        val dataKranjang = myDb.daoKeranjangSnack().getAll()

        if (dataKranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        snack = Gson().fromJson<Snack>(data, Snack::class.java)

        // set Value
        tv_nama.text = snack.nama
        tv_harga.text = snack.harga
        tv_harga.text = Helper().gantiRupiah(snack.harga)
        tv_deskripsi.text = snack.deskripsi
        stok.text = snack.stok

        val img = "http://192.168.146.191/gbr/" + snack.gambar
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.b3)
            .error(R.drawable.b3)
            .resize(400,400)
            .into(image)

        // setToolbar
        supportActionBar!!.title = snack.nama
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
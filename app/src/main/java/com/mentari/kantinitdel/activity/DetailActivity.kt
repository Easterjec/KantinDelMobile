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
import com.mentari.kantinitdel.model.Makanan
import com.mentari.kantinitdel.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.tv_harga
import kotlinx.android.synthetic.main.activity_detail.tv_nama
import kotlinx.android.synthetic.main.toolbar_custom.*


class DetailActivity : AppCompatActivity() {

    lateinit var makanan: Makanan
    lateinit var myDb: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        myDb = MyDatabase.getInstance(this)!!

        getInfo()
        mainButton()
        checkKeranjang()
    }


    private fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjangMakanan().getMakanan(makanan.id_makanan)
            if (data == null) {
                insert()
            } else {
                data.jumlah =+ 1
                update(data)
            }
        }

        btn_favorit.setOnClickListener {
            val myDb: MyDatabase = MyDatabase.getInstance(this)!!
            val listdata = myDb.daoKeranjangMakanan().getAll()// get All data
            for(note : Makanan in listdata){
                println("-----------------")
                println(note.nama)
                print(note.harga)
            }
        }



        btn_toKeranjang.setOnClickListener{
            val idmm = Intent(this, KeranjangMakananActivity::class.java)
            startActivity(idmm)
        }
    }

    private fun insert() {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMakanan().insert(makanan) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil menambah Produk", Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data: Makanan) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMakanan().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted update")
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
            })
    }


    private fun checkKeranjang() {
        val dataKranjang = myDb.daoKeranjangMakanan().getAll()

        if (dataKranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        makanan = Gson().fromJson<Makanan>(data, Makanan::class.java)

        // set Value
        tv_nama.text = makanan.nama
        tv_harga.text = makanan.harga
        tv_harga.text = Helper().gantiRupiah(makanan.harga)
        tv_deskripsi.text = makanan.deskripsi
        stok.text = makanan.stok

        val img = "http://192.168.146.191/gbr/" + makanan.gambar
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.b1)
            .error(R.drawable.b1)
            .resize(400,400)
            .into(image)

        // setToolbar
        supportActionBar!!.title = makanan.nama
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
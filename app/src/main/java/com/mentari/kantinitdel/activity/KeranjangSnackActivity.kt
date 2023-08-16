package com.mentari.kantinitdel.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.adapter.AdapterKeranjangSnack
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.model.Snack
import com.mentari.kantinitdel.room.MyDatabaseSnack
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_keranjang_snack.*
import kotlinx.android.synthetic.main.fragment_keranjang.*

class KeranjangSnackActivity : AppCompatActivity() {
    lateinit var myDb : MyDatabaseSnack
    lateinit var btnHapus : ImageView
    lateinit var rvSnack: RecyclerView
    lateinit var rvTotal : TextView
    lateinit var btnBayar: TextView
    lateinit var CbAll: CheckBox
    lateinit var s : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang_snack)

        myDb = MyDatabaseSnack.getInstance(this)!!
        s = SharedPreferences(this)

        btnHapus = findViewById(R.id.btn_deletesnack)
        rvSnack = findViewById(R.id.rv_snack)
        rvTotal = findViewById(R.id.tv_totalsnack)
        btnBayar = findViewById(R.id.btn_bayarsnack)
        CbAll = findViewById(R.id.cb_allsnack)


        mainButton()
    }


    lateinit var adapter : AdapterKeranjangSnack
    var listSnack = ArrayList<Snack>()
    private fun displaySnack(){
        listSnack = myDb.daoKeranjangSnack().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = AdapterKeranjangSnack(this,listSnack, object : AdapterKeranjangSnack.Listeners{
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listSnack.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }
        })

        rv_snack.adapter = adapter
        rv_snack.layoutManager = layoutManager
    }

    var totalHarga = 0
    fun hitungTotal() {
        val listSnack = myDb.daoKeranjangSnack().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (snack in listSnack) {
            if (snack.selected) {
                val harga = Integer.valueOf(snack.harga)
                totalHarga += (harga * snack.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        cb_allsnack.isChecked = isSelectedAll
        tv_totalsnack.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton() {
        btnHapus.setOnClickListener {
            val listDelete = ArrayList<Snack>()
            for (p in listSnack) {
                if (p.selected) listDelete.add(p)
            }

            delete(listDelete)
        }
        btnBayar.setOnClickListener {
            if (s.getStatusLogin()) {
                var isThereProduk = false
                for (p in listSnack) {
                    if (p.selected) isThereProduk = true
                }

                if (isThereProduk) {
                    val intent = Intent(this, PengirimanSnackActivity::class.java)
                    intent.putExtra("extra", "" + totalHarga)
                    intent.putExtra("jenis", "barang")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Tidak ada produk yg terpilih", Toast.LENGTH_SHORT).show()
                }
            } else {
                this.startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        CbAll.setOnClickListener {
            for (i in listSnack.indices) {
                val snack = listSnack[i]
                snack.selected = cb_allsnack.isChecked
                listSnack[i] = snack
            }
            adapter.notifyDataSetChanged()
        }

    }

    private fun delete(data: ArrayList<Snack>) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangSnack().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listSnack.clear()
                listSnack.addAll(myDb.daoKeranjangSnack().getAll() as ArrayList)
                adapter.notifyDataSetChanged()
            })
    }

    override fun onResume() {
        displaySnack()
        hitungTotal()
        super.onResume()
    }

}

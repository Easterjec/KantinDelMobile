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
import com.mentari.kantinitdel.adapter.AdapterKeranjangMinuman
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.model.Minuman
import com.mentari.kantinitdel.model.Snack
import com.mentari.kantinitdel.room.MyDatabaseMinuman
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_keranjang_minuman.*
import kotlinx.android.synthetic.main.fragment_keranjang.*

class KeranjangMinumanActivity : AppCompatActivity() {
    lateinit var myDb : MyDatabaseMinuman
    lateinit var btnHapus : ImageView
    lateinit var rvminuman: RecyclerView
    lateinit var rvTotal : TextView
    lateinit var btnBayar: TextView
    lateinit var CbAll: CheckBox
    lateinit var s : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang_minuman)

        myDb = MyDatabaseMinuman.getInstance(this)!!
        s = SharedPreferences(this)

        btnHapus = findViewById(R.id.btn_deleteminuman)
        rvminuman = findViewById(R.id.rv_minuman)
        rvTotal = findViewById(R.id.tv_totalminuman)
        btnBayar = findViewById(R.id.btn_bayarminuman)
        CbAll = findViewById(R.id.cb_allminuman)

        mainButton()
    }


    lateinit var adapter : AdapterKeranjangMinuman
    var listMinuman = ArrayList<Minuman>()
    private fun displayMinuman(){
        listMinuman = myDb.daoKeranjangMinuman().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = AdapterKeranjangMinuman(this,listMinuman, object : AdapterKeranjangMinuman.Listeners{
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listMinuman.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })

        rv_minuman.adapter = adapter
        rv_minuman.layoutManager = layoutManager
    }

    var totalHarga = 0
    fun hitungTotal() {
        val listMinuman = myDb.daoKeranjangMinuman().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (minum in listMinuman) {
            if (minum.selected) {
                val harga = Integer.valueOf(minum.harga)
                totalHarga += (harga * minum.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        cb_allminuman.isChecked = isSelectedAll
        tv_totalminuman.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton() {
        btnHapus.setOnClickListener {
            val listDelete = ArrayList<Minuman>()
            for (p in listMinuman) {
                if (p.selected) listDelete.add(p)
            }

            delete(listDelete)
        }
        btnBayar.setOnClickListener {
            if (s.getStatusLogin()) {
                var isThereProduk = false
                for (p in listMinuman) {
                    if (p.selected) isThereProduk = true
                }

                if (isThereProduk) {
                    val intent = Intent(this, PengirimanMinumanActivity::class.java)
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
            for (i in listMinuman.indices) {
                val minum = listMinuman[i]
                minum.selected = cb_allminuman.isChecked
                listMinuman[i] = minum
            }
            adapter.notifyDataSetChanged()
        }

    }

        private fun delete(data: ArrayList<Minuman>) {
            CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMinuman().delete(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listMinuman.clear()
                    listMinuman.addAll(myDb.daoKeranjangMinuman().getAll() as ArrayList)
                    adapter.notifyDataSetChanged()
                })
        }

    override fun onResume() {
        displayMinuman()
        hitungTotal()
        super.onResume()
    }

}

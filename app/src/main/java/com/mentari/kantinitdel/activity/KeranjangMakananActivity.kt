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
import com.mentari.kantinitdel.adapter.AdapterKeranjangMakanan
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.model.Makanan
import com.mentari.kantinitdel.model.Minuman
import com.mentari.kantinitdel.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.keranjang_makanan.*

class KeranjangMakananActivity : AppCompatActivity() {
    lateinit var myDb : MyDatabase
    lateinit var btnHapus : ImageView
    lateinit var rvMakananan: RecyclerView
    lateinit var rvTotal : TextView
    lateinit var btnBayar: TextView
    lateinit var CbAll: CheckBox
    lateinit var s : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang_makanan)

        myDb = MyDatabase.getInstance(this)!!
        s = SharedPreferences(this)

        btnHapus = findViewById(R.id.btn_deletemakanan)
        rvMakananan = findViewById(R.id.rv_makanan)
        rvTotal = findViewById(R.id.tv_totalmakanan)
        btnBayar = findViewById(R.id.btn_bayarmakanan)
        CbAll = findViewById(R.id.cb_allmakanan)

        mainButton()
    }


    lateinit var adapter : AdapterKeranjangMakanan
    var listMakanan = ArrayList<Makanan>()
    private fun displayMakanan(){
        listMakanan = myDb.daoKeranjangMakanan().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = AdapterKeranjangMakanan(this,listMakanan, object : AdapterKeranjangMakanan.Listeners{
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listMakanan.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })

        rv_makanan.adapter = adapter
        rv_makanan.layoutManager = layoutManager
    }

    var totalHarga = 0
    fun hitungTotal() {
        val listMakanan = myDb.daoKeranjangMakanan().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (makanan in listMakanan) {
            if (makanan.selected) {
                val harga = Integer.valueOf(makanan.harga)
                totalHarga += (harga * makanan.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        cb_allmakanan.isChecked = isSelectedAll
        tv_totalmakanan.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton() {
        btnHapus.setOnClickListener {
            val listDelete = ArrayList<Makanan>()
            for (p in listMakanan) {
                if (p.selected) listDelete.add(p)
            }

            delete(listDelete)
        }
        btnBayar.setOnClickListener {
            if (s.getStatusLogin()) {
                var isThereProduk = false
                for (p in listMakanan) {
                    if (p.selected) isThereProduk = true
                }

                if (isThereProduk) {
                    val intent = Intent(this, PengirimanMakananActivity::class.java)
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
            for (i in listMakanan.indices) {
                val makanan = listMakanan[i]
                makanan.selected = cb_allmakanan.isChecked
                listMakanan[i] = makanan
            }
            adapter.notifyDataSetChanged()
        }

    }

    private fun delete(data: ArrayList<Makanan>) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMakanan().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listMakanan.clear()
                listMakanan.addAll(myDb.daoKeranjangMakanan().getAll() as ArrayList)
                adapter.notifyDataSetChanged()
            })
    }

    override fun onResume() {
        displayMakanan()
        hitungTotal()
        super.onResume()
    }
}

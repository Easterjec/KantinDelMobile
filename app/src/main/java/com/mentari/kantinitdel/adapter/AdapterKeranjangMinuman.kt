package com.mentari.kantinitdel.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.model.Minuman
import com.mentari.kantinitdel.room.MyDatabaseMinuman
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class AdapterKeranjangMinuman(var activity: Activity, var data: ArrayList<Minuman>, var listener: Listeners): RecyclerView.Adapter<AdapterKeranjangMinuman.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgGambar = view.findViewById<ImageView>(R.id.img_m)
        val layout = view.findViewById<CardView>(R.id.layoutkeranjang)

        val btn_tambah = view.findViewById<ImageView>(R.id.btn_tambah)
        val btn_kurang = view.findViewById<ImageView>(R.id.btn_kurang)
        val btn_delete = view.findViewById<ImageView>(R.id.btn_delete)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val tv_jumlah = view.findViewById<TextView>(R.id.tv_jumlah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.keranjangggg, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val minuman = data[position]
        val harga = Integer.valueOf(minuman.harga)
        holder.tvNama.text = minuman.nama
        holder.tvHarga.text = Helper().gantiRupiah(minuman.harga)

        var jumlah = minuman.jumlah
        holder.tv_jumlah.text = jumlah.toString()

        holder.checkBox.isChecked = minuman.selected
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            minuman.selected = isChecked
            update(minuman)
        }

        val img = "http://192.168.146.191/gbr/" + data[position].gambar
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.b2)
            .error(R.drawable.b2)
            .into(holder.imgGambar)

        holder.btn_tambah.setOnClickListener {
            jumlah++
            minuman.jumlah = jumlah
            update(minuman)

            holder.tv_jumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btn_kurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--

            minuman.jumlah = jumlah
            update(minuman)
            holder.tv_jumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)

        }

        holder.btn_delete.setOnClickListener {
            delete(minuman)
            listener.onDelete(position)
        }

    }

    interface Listeners {
        fun onUpdate()
        fun onDelete(position: Int)
    }


    private fun update(data: Minuman) {
        val myDb = MyDatabaseMinuman.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangMinuman().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delete(data: Minuman) {
        val myDb = MyDatabaseMinuman.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangMinuman().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {})
    }
}

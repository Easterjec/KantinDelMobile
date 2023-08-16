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
import com.mentari.kantinitdel.model.Makanan
import com.mentari.kantinitdel.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class AdapterKeranjangMakanan(var activity: Activity, var data: ArrayList<Makanan>, var listener: Listeners): RecyclerView.Adapter<AdapterKeranjangMakanan.Holder>() {
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

        val makanan = data[position]
        val harga = Integer.valueOf(makanan.harga)
        holder.tvNama.text = makanan.nama
        holder.tvHarga.text = Helper().gantiRupiah(makanan.harga)

        var jumlah = makanan.jumlah
        holder.tv_jumlah.text = jumlah.toString()

        holder.checkBox.isChecked = makanan.selected
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            makanan.selected = isChecked
            update(makanan)
        }


        val img = "http://192.168.146.191/gbr/" + data[position].gambar
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.b1)
            .error(R.drawable.b1)
            .into(holder.imgGambar)


            holder.btn_tambah.setOnClickListener {
                jumlah++
                makanan.jumlah = jumlah
                update(makanan)

                holder.tv_jumlah.text = jumlah.toString()
                holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)
                }

            holder.btn_kurang.setOnClickListener {
                if (jumlah <= 1) return@setOnClickListener
                jumlah--

                makanan.jumlah = jumlah
                update(makanan)
                holder.tv_jumlah.text = jumlah.toString()
                holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)

            }

            holder.btn_delete.setOnClickListener {
                delete(makanan)
                listener.onDelete(position)
            }

    }

    interface Listeners {
        fun onUpdate()
        fun onDelete(position: Int)
    }


    private fun update(data: Makanan) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangMakanan().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delete(data: Makanan) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangMakanan().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {})
    }
}
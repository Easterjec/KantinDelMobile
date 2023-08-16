package com.mentari.kantinitdel.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.activity.DetailActivityMinuman
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.model.Minuman
import kotlin.collections.ArrayList

class AdapterMinuman(var activity: Activity, var data: ArrayList<Minuman>): RecyclerView.Adapter<AdapterMinuman.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_namamenu)
        val tvHarga = view.findViewById<TextView>(R.id.tv_hargamakanan)
        val imgGambar = view.findViewById<ImageView>(R.id.gambar_menu)
        val layout = view.findViewById<CardView>(R.id.layout_detail)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.menu, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].nama
        //holder.tvHarga.text = "Rp."+data[position].harga
       // holder.tvHarga.text = NumberFormat.getCurrencyInstance(Locale("in","ID")).format(Integer.valueOf(data[position].harga))
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)

        //holder.imgGambar.setImageResource(data[position].gambar)

        val img = "http://192.168.146.191/gbr/" + data[position].gambar
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.b2)
            .error(R.drawable.b2)
            .into(holder.imgGambar)

        holder.layout.setOnClickListener {
            val dm = Intent(activity, DetailActivityMinuman::class.java)
            val str = Gson().toJson(data[position], Minuman::class.java)
            dm.putExtra("extra", str)
            activity.startActivity(dm)
        }
    }

}
package com.mentari.kantinitdel.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.model.PemesananSnack


class AdapterRiwayatSnack(var data: ArrayList<PemesananSnack>, var listener: Listeners): RecyclerView.Adapter<AdapterRiwayatSnack.Holder>()  {

        class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_nama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)
        val tv_tanggal = view.findViewById<TextView>(R.id.tv_tgl)
        val tv_jumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val tv_status = view.findViewById<TextView>(R.id.tv_status)
        val btnDetail = view.findViewById<TextView>(R.id.btn_detail)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.riwayat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        val name = a.kode_transaksi
        holder.tv_nama.text = name
        holder.tv_harga.text = Helper().gantiRupiah(Integer.valueOf(a.total_pembayaran))
        holder.tv_jumlah.text = a.total_item + " Items"
        holder.tv_status.text = a.status

        // 2021-04-30 18:30:20 //24
        // jam 1   k || 01  kk
        // 09:20:20 am 12/pm/am
        val formatBaru = "d MMM yyyy"
        holder.tv_tanggal.text = Helper().convertTanggal(a.tanggal_pemesanan_snack, formatBaru)

//        var color = context.getColor(R.color.colorPrimary)
//        if  (a.status == "PERMINTAAN") color = context.getColor(R.color.purple_500)
//        else if (a.status == "VERIFIKASI") color = context.getColor(R.color.teal_700)
//        else if (a.status == "TERIMA") color = context.getColor(R.color.pulsa)
//
//       holder.tv_status.setTextColor(color)

        holder.layout.setOnClickListener {
            listener.onClicked(a)
        }
    }

    interface Listeners {
        fun onClicked(data: PemesananSnack)
    }
}
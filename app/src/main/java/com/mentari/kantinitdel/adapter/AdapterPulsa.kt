package com.mentari.kantinitdel.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.activity.PulsaActivity
import com.mentari.kantinitdel.model.Pulsa
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterPulsa(var activity:PulsaActivity, var pulsa: ArrayList<Pulsa>): RecyclerView.Adapter<AdapterPulsa.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNominal = view.findViewById<TextView>(R.id.tv_nominal)
        val tvHarga_Pulsa = view.findViewById<TextView>(R.id.tv_hargapulsa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pulsa, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNominal.text = pulsa[position].nominal
        //holder.tvHarga_Pulsa.text = pulsa[position].harga
        holder.tvHarga_Pulsa.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(pulsa[position].harga))
    }

    override fun getItemCount(): Int {
        return pulsa.size
    }
}
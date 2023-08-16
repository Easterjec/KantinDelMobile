package com.mentari.kantinitdel.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.adapter.AdapterKeranjangMakanan
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.activity.*
import com.mentari.kantinitdel.database.Helper
import com.mentari.kantinitdel.model.Makanan
import com.mentari.kantinitdel.room.MyDatabase
import kotlinx.android.synthetic.main.fragment_keranjang.*


class KeranjangFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)

        init(view)

        mainButton()
        return view
    }

    lateinit var btnKeranjangMakanan: Button
    lateinit var btnKeranjangMinuman: Button
    lateinit var btnKeranjangSnack: Button

    private fun init(view: View) {

        btnKeranjangMakanan = view.findViewById(R.id.btnKeranjangMakanan)
        btnKeranjangMinuman = view.findViewById(R.id.btnKeranjangMinuman)
        btnKeranjangSnack = view.findViewById(R.id.btnKeranjangSnack)
    }

    fun mainButton() {
        btnKeranjangMakanan.setOnClickListener {
            val intent = Intent(activity, KeranjangMakananActivity::class.java)
            startActivity(intent)
        }
        btnKeranjangMinuman.setOnClickListener {
            val intent = Intent(activity, KeranjangMinumanActivity::class.java)
            startActivity(intent)
        }
        btnKeranjangSnack.setOnClickListener {
            val intent = Intent(activity, KeranjangSnackActivity::class.java)
            startActivity(intent)
        }

    }

}

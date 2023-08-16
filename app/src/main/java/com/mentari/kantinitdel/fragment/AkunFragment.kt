package com.mentari.kantinitdel.fragment

import android.content.Intent
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.database.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.mentari.kantinitdel.activity.MasukActivity
import com.mentari.kantinitdel.activity.RiwayatActivity
import com.mentari.kantinitdel.model.Snack


/**
 * A simple [Fragment] subclass.
 */
class AkunFragment : Fragment() {

    lateinit var sp: SharedPreferences
    lateinit var tNama:TextView
    lateinit var temail:TextView
    lateinit var tnoktp:TextView
    lateinit var tnotlp:TextView
    lateinit var btnKeluar:Button
    lateinit var btnRiwayat:Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_akun, container, false)

        init(view)


        sp = SharedPreferences(requireActivity())

        btnKeluar.setOnClickListener{
            sp.setStatusLogin(false)
        }

        mainButton()
//        setData()
        return view
    }

    fun mainButton() {
        btnKeluar.setOnClickListener {
            sp.setStatusLogin(false)
        }
        btnRiwayat.setOnClickListener {
            startActivity(Intent(activity, RiwayatActivity::class.java))
        }

    }


    fun setData() {

        if (sp.getUser() == null) {
            return
        }
        val user = sp.getUser()!!
        tNama.text = user.name
        temail.text = user.email
//        tnotlp.text = user.no_tlp
        tnoktp.text = user.no_ktp

    }

    private fun init(view: View) {
        btnKeluar = view.findViewById(R.id.btnKeluar)
        btnRiwayat = view.findViewById(R.id.btnRiwayat)
        tNama = view.findViewById(R.id.text_nama)
        temail= view.findViewById(R.id.text_email)
        tnoktp= view.findViewById(R.id.text_noktp)
        tnotlp= view.findViewById(R.id.text_tlp)

    }

}

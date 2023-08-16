package com.mentari.kantinitdel.fragment

import android.content.Intent
import com.mentari.kantinitdel.adapter.AdapterMenu
import com.mentari.kantinitdel.adapter.AdapterSlider
import com.mentari.kantinitdel.R
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.mentari.kantinitdel.adapter.AdapterMinuman
import com.mentari.kantinitdel.adapter.AdapterSnack
import com.mentari.kantinitdel.activity.MakananActivity
import com.mentari.kantinitdel.activity.MinumanActivity
import com.mentari.kantinitdel.activity.PulsaActivity
import com.mentari.kantinitdel.activity.SnackActivity
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.model.Makanan
import com.mentari.kantinitdel.model.Minuman
import com.mentari.kantinitdel.model.ResponseMenu
import com.mentari.kantinitdel.model.Snack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class BerandaFragment : Fragment(){

    lateinit var vpSlider: ViewPager
    lateinit var rvMenu: RecyclerView
    lateinit var rvMenu1: RecyclerView
    lateinit var rvMenu2: RecyclerView
    lateinit var btnDaftarMakanan: Button
    lateinit var btnDaftarMinuman: Button
    lateinit var btnDaftarSnack: Button
    lateinit var btnDaftarPulsa: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_beranda, container, false)
        init(view)
        getMakanan()
        getMinuman()
        getSnack()
        mainButton()
        return view
    }

    fun mainButton() {
        btnDaftarMakanan.setOnClickListener {
            val intent = Intent(activity, MakananActivity::class.java)
            startActivity(intent)
        }
        btnDaftarMinuman.setOnClickListener {
            val intent = Intent(activity, MinumanActivity::class.java)
            startActivity(intent)
        }
        btnDaftarSnack.setOnClickListener {
            val intent = Intent(activity, SnackActivity::class.java)
            startActivity(intent)
        }
        btnDaftarPulsa.setOnClickListener {
            val intent = Intent(activity, PulsaActivity::class.java)
            startActivity(intent)
        }

    }

    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider)
        rvMenu = view.findViewById(R.id.rv_menu)
        rvMenu1 = view.findViewById(R.id.rv_menu1)
        rvMenu2 = view.findViewById(R.id.rv_menu2)
        btnDaftarMakanan = view.findViewById(R.id.btnDaftarMakanan)
        btnDaftarMinuman = view.findViewById(R.id.btnDaftarMinuman)
        btnDaftarSnack = view.findViewById(R.id.btnDaftarSnack)
        btnDaftarPulsa = view.findViewById(R.id.btnDaftarPulsa)
    }


    fun displayMakanan(){
        var arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.b1)
        arrSlider.add(R.drawable.b2)
        arrSlider.add(R.drawable.b3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvMenu.adapter = AdapterMenu(requireActivity(), listMakanan)
        rvMenu.layoutManager = layoutManager

    }

    fun displaySnack(){

        var arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.b1)
        arrSlider.add(R.drawable.b2)
        arrSlider.add(R.drawable.b3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        rvMenu2.adapter = AdapterSnack(requireActivity(),listSnack)
        rvMenu2.layoutManager = layoutManager2
    }

    fun displayMinuman(){

        var arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.b1)
        arrSlider.add(R.drawable.b2)
        arrSlider.add(R.drawable.b3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager1 = LinearLayoutManager(activity)
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL

        rvMenu1.adapter = AdapterMinuman(requireActivity(),listMinuman)
        rvMenu1.layoutManager = layoutManager1
    }


    private var listMakanan:ArrayList<Makanan> = ArrayList()
    fun getMakanan(){
        ApiConfig.instanceRetrofit.getMakanan().enqueue(object :
            Callback<ResponseMenu> {

            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                val res = response.body()!!
                if(res.success == 1){
                    listMakanan = res.makanan
                    displayMakanan()
                }
            }

            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
            }
        })
    }

    private var listMinuman:ArrayList<Minuman> = ArrayList()
    fun getMinuman(){
        ApiConfig.instanceRetrofit.getMinuman().enqueue(object :
            Callback<ResponseMenu> {

            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                val res = response.body()!!
                if(res.success == 1){
                    listMinuman = res.minuman
                    displayMinuman()
                }
            }

            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
            }
        })
    }

    private var listSnack:ArrayList<Snack> = ArrayList()
    fun getSnack(){
        ApiConfig.instanceRetrofit.getSnack().enqueue(object :
            Callback<ResponseMenu> {

            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                val res = response.body()!!
                if(res.success == 1){
                    listSnack = res.snack
                    displaySnack()
                }
            }

            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
            }
        })
    }

}
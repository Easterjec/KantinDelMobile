package com.mentari.kantinitdel.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.adapter.AdapterMenu
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.model.Makanan
import com.mentari.kantinitdel.model.ResponseMenu
import kotlinx.android.synthetic.main.activity_makanan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakananActivity : AppCompatActivity() {

    lateinit var rvMenu: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan)

        rvMenu = findViewById(R.id.rv_menu)
        getMakanan()
    }

    fun displayMakanan(){

        val layoutManager = LinearLayoutManager(this)

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvMenu.adapter = AdapterMenu(this, listMakanan)
        rvMenu.layoutManager = layoutManager
        rv_menu.layoutManager = GridLayoutManager(this,2)


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

}
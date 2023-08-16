package com.mentari.kantinitdel.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.adapter.AdapterMinuman
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.model.Minuman
import com.mentari.kantinitdel.model.ResponseMenu
import kotlinx.android.synthetic.main.activity_makanan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MinumanActivity : AppCompatActivity() {

    lateinit var rvMenu1: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minuman)

        rvMenu1 = findViewById(R.id.rv_menu1)
        getMinuman()
    }

    fun displayMinuman(){

        val layoutManager = LinearLayoutManager(this)

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvMenu1.adapter = AdapterMinuman(this, listMinuman)
        rvMenu1.layoutManager = layoutManager
        rvMenu1.layoutManager = GridLayoutManager(this,2)


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

}
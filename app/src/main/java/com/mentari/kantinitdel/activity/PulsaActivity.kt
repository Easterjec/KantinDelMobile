package com.mentari.kantinitdel.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.adapter.AdapterMenu
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.adapter.AdapterPulsa
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.model.Pulsa
import com.mentari.kantinitdel.model.ResponseMenu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PulsaActivity : AppCompatActivity() {

    lateinit var rvpulsa: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulsa)

        rvpulsa = findViewById(R.id.rvpulsa)
        getPulsa()
    }

    fun displayPulsa(){

        val layoutManager3 = LinearLayoutManager(this)

        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        rvpulsa.adapter = AdapterPulsa(this, listPulsa)
        rvpulsa.layoutManager = layoutManager3
        rvpulsa.layoutManager = GridLayoutManager(this,2)
    }


    private var listPulsa:ArrayList<Pulsa> = ArrayList()
    fun getPulsa(){
        ApiConfig.instanceRetrofit.getPulsa().enqueue(object :
            Callback<ResponseMenu> {

            override fun onResponse(call: Call<ResponseMenu>, response: Response<ResponseMenu>) {
                val res = response.body()!!
                if(res.success == 1){
                    listPulsa = res.pulsa
                    displayPulsa()
                }
            }

            override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
            }
        })
    }

}
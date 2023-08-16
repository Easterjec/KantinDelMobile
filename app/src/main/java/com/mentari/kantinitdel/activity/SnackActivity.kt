package com.mentari.kantinitdel.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentari.kantinitdel.adapter.AdapterMenu
import com.mentari.kantinitdel.adapter.AdapterSnack
import com.mentari.kantinitdel.R
import com.mentari.kantinitdel.api.ApiConfig
import com.mentari.kantinitdel.model.ResponseMenu
import com.mentari.kantinitdel.model.Snack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SnackActivity : AppCompatActivity() {
    lateinit var rvMenu2: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack)


        rvMenu2 = findViewById(R.id.rv_menu2)
        getSnack()
    }

    fun displaySnack(){

        val layoutManager2 = LinearLayoutManager(this)

        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        rvMenu2.adapter = AdapterSnack(this, listSnack)
        rvMenu2.layoutManager = layoutManager2
        rvMenu2.layoutManager = GridLayoutManager(this, 2)


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
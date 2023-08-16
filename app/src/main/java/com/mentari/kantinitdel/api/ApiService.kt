package com.mentari.kantinitdel.api

import com.mentari.kantinitdel.model.PesanMakanan
import com.mentari.kantinitdel.model.PesanMinuman
import com.mentari.kantinitdel.model.PesanSnack
import com.mentari.kantinitdel.model.ResponseMenu
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("registrasi") // "http://127.0.0.1:8000/api/registrasi"
    fun registrasi(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("no_ktp") no_ktp:String,
        @Field("no_tlp") no_tlp:String,
        @Field("password") password:String
    ):Call<ResponseMenu>

    @FormUrlEncoded
    @POST("login") // "http://127.0.0.1:8000/api/login"
    fun login(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<ResponseMenu>

    @GET("makanan") // "http://127.0.0.1:8000/api/makanan"
    fun getMakanan():Call<ResponseMenu>

    @GET("minuman") // "http://127.0.0.1:8000/api/minuman"
    fun getMinuman():Call<ResponseMenu>

    @GET("snack") // "http://127.0.0.1:8000/api/snack"
    fun getSnack():Call<ResponseMenu>

    @GET("pulsa") // "http://127.0.0.1:8000/api/pulsa"
    fun getPulsa():Call<ResponseMenu>

    @POST("pesansnack")
    fun pesansnack( @Body data: PesanSnack): Call<ResponseMenu>

    @POST("pesanminuman")
    fun pesanminuman( @Body data: PesanMinuman): Call<ResponseMenu>

    @POST("pesanmakanan")
    fun pesanmakanan( @Body data: PesanMakanan): Call<ResponseMenu>

    @GET("pesansnack/user/{id}")
    fun getRiwayatSnack(@Path("id") id: Int): Call<ResponseMenu>

}

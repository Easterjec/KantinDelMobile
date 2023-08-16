package com.mentari.kantinitdel.model

import java.io.Serializable

class Menu: Serializable {
    lateinit var nama:String
    lateinit var harga:String
    var gambar:Int = 0 //value di drawable selalu integer
}
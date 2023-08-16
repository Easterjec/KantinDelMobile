package com.mentari.kantinitdel.model

class ResponseMenu {
    var success = 0
    var user = User()
    lateinit var message:String
    var makanan:ArrayList<Makanan> = ArrayList()
    var minuman:ArrayList<Minuman> = ArrayList()
    var snack:ArrayList<Snack> = ArrayList()
    var pulsa:ArrayList<Pulsa> = ArrayList()

    var pesansnacks : ArrayList<PemesananSnack> = ArrayList()
    var pesansnack = PesanSnack()

    var pesanmakanans : ArrayList<PesanMinuman> = ArrayList()
    var pesanmakanan = PesanMinuman()

    var pesanminumans : ArrayList<PesanMakanan> = ArrayList()
    var pesanminuman = PesanMakanan()

}
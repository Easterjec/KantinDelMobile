package com.mentari.kantinitdel.model

class PesanMakanan {
    lateinit var id_user: String
    lateinit var total_item: String
    lateinit var nama_penerima: String
    lateinit var nomor_hp: String
    lateinit var total_pembayaran: String
    lateinit var catatan: String
    var makanan = ArrayList<Item>()

    class Item {
        lateinit var id_makanan: String
        lateinit var kuantitas: String
        lateinit var total_harga: String
        }
}

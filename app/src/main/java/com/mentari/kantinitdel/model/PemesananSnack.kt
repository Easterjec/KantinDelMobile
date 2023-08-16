package com.mentari.kantinitdel.model

class PemesananSnack {
    var id_pesansnack = 0
    var kode_transaksi = ""
    var tanggal_pemesanan_snack = ""
    var id_user = ""
    var total_item = ""
    var total_pembayaran = ""
    var nama_penerima = ""
    var nomor_hp = ""
    var catatan = ""
    var status = ""
    val details = ArrayList<PemesananSnackDetail>()
}
package com.mentari.kantinitdel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "keranjang_makanan")
public class Makanan implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_keranjang")
    public int id_keranjang;
    
    public int id_makanan;
    public String nama;
    public String harga;
    public String deskripsi;
    public String stok;
    public String gambar;

    public int jumlah = 1;
    public boolean selected = true;
}

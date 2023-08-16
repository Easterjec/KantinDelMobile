package com.mentari.kantinitdel.room

import android.text.TextUtils.isEmpty
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mentari.kantinitdel.model.Minuman


@Dao
interface DaoKeranjangMinuman {
    @Insert(onConflict = REPLACE)
    fun insert(data : Minuman)

    @Delete
    fun delete(data: Minuman)

    @Delete
    fun delete(data: List<Minuman>)

    @Update
    fun update(data: Minuman): Int

    @Query("SELECT * from keranjang_minuman ORDER BY id_keranjang ASC")
    fun getAll(): List<Minuman>

    @Query("SELECT * FROM keranjang_minuman WHERE id_minuman = :id LIMIT 1")
    fun getMinuman(id: Int): Minuman?

    @Query("DELETE FROM keranjang_minuman WHERE id_minuman = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM keranjang_minuman")
    fun deleteAll(): Int

}

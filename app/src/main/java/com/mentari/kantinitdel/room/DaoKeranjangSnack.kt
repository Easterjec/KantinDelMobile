package com.mentari.kantinitdel.room

import android.text.TextUtils.isEmpty
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mentari.kantinitdel.model.Snack

@Dao
interface DaoKeranjangSnack {
    @Insert(onConflict = REPLACE)
    fun insert(data : Snack)

    @Delete
    fun delete(data: Snack)

    @Delete
    fun delete(data: List<Snack>)

    @Update
    fun update(data: Snack): Int

    @Query("SELECT * from keranjang_snack ORDER BY id_keranjang ASC")
    fun getAll(): List<Snack>

    @Query("SELECT * FROM keranjang_snack WHERE id_snack = :id LIMIT 1")
    fun getSnack(id: Int): Snack?

    @Query("DELETE FROM keranjang_snack WHERE id_snack = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM keranjang_snack")
    fun deleteAll(): Int

}

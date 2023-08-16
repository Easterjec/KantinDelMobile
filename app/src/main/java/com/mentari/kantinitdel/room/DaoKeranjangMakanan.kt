package com.mentari.kantinitdel.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mentari.kantinitdel.model.Makanan

@Dao
interface DaoKeranjangMakanan {

    @Insert(onConflict = REPLACE)
    fun insert(data: Makanan)

    @Delete
    fun delete(data: Makanan)

    @Delete
    fun delete(data: List<Makanan>)

    @Update
    fun update(data: Makanan): Int

    @Query("SELECT * from keranjang_makanan ORDER BY id_keranjang ASC")
    fun getAll(): List<Makanan>

    @Query("SELECT * FROM keranjang_makanan WHERE id_makanan = :id LIMIT 1")
    fun getMakanan(id: Int): Makanan?

    @Query("DELETE FROM keranjang_makanan WHERE id_makanan = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM keranjang_makanan")
    fun deleteAll(): Int
}
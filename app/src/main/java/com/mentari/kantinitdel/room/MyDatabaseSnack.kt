package com.mentari.kantinitdel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mentari.kantinitdel.model.Snack

@Database(entities = [Snack::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabaseSnack : RoomDatabase() {
    abstract fun daoKeranjangSnack(): DaoKeranjangSnack // DaoKeranjang

    companion object {
        var INSTANCE: MyDatabaseSnack? = null

        fun getInstance(context: Context): MyDatabaseSnack? {
            if (INSTANCE == null) {
                synchronized(MyDatabaseSnack::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabaseSnack::class.java, "MyDatabaseSnack" // Database Name
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

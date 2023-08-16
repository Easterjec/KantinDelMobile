package com.mentari.kantinitdel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mentari.kantinitdel.model.Minuman

@Database(entities = [Minuman::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabaseMinuman : RoomDatabase() {
    abstract fun daoKeranjangMinuman(): DaoKeranjangMinuman // DaoKeranjang

    companion object {
        var INSTANCE: MyDatabaseMinuman? = null

        fun getInstance(context: Context): MyDatabaseMinuman? {
            if (INSTANCE == null) {
                synchronized(MyDatabaseMinuman::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabaseMinuman::class.java, "MyDatabaseMinuman" // Database Name
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

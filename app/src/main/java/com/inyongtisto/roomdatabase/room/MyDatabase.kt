package com.inyongtisto.roomdatabase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inyongtisto.roomdatabase.model.NoteModel
/* Daftar model,Contohnya:model catatan */
@Database(entities = [NoteModel::class] , version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun daoNote(): DaoNote // DaoNote

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MyDatabase::class.java, "MyDatabaseName" // Nama Database
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
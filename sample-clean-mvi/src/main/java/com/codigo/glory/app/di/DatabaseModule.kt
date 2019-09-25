package com.codigo.glory.app.di

import androidx.room.Room
import com.codigo.glory.data.database.CleanDatabase
import org.koin.dsl.module

/**
 * Created by khunzohn on 2019-09-11 at Codigo
 */
val databaseModule = module {

    single {
        Room.databaseBuilder(get(), CleanDatabase::class.java, "clean-db")
            .build()
    }

    single {
        val db: CleanDatabase = get()
        db.iPhoneDao()
    }

    single {
        val db: CleanDatabase = get()
        db.macDao()
    }
}
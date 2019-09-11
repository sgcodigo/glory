package com.codigo.glory.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codigo.glory.data.database.dao.IPhoneDao
import com.codigo.glory.data.database.dao.MacDao
import com.khunzohn.data.model.entity.IPhoneEntity
import com.khunzohn.data.model.entity.MacEntity

@Database(
    entities = [IPhoneEntity::class, MacEntity::class],
    version = 2, exportSchema = true
)
abstract class CleanDatabase : RoomDatabase() {

    abstract fun iPhoneDao(): IPhoneDao

    abstract fun macDao(): MacDao
}
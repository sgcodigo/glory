package com.codigo.glory.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE i_phones " + " ADD COLUMN is_new INTEGER NOT NULL"
        )

        database.execSQL(
            "ALTER TABLE macs " + " ADD COLUMN is_new INTEGER NOT NULL"
        )
    }
}
package com.codigo.glory.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khunzohn.data.model.entity.MacEntity
import io.reactivex.Observable

@Dao
interface MacDao {

    @Query("SELECT * FROM macs ORDER BY price")
    fun stream(): Observable<List<MacEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg macs: MacEntity)

    @Query("DELETE FROM macs")
    fun removeAll()

    @Query("UPDATE macs SET favourite = :favourite WHERE id = :macId")
    fun updateFavourite(macId: String, favourite: Boolean)
}
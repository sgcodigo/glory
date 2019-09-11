package com.codigo.glory.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khunzohn.data.model.entity.IPhoneEntity
import io.reactivex.Observable

@Dao
interface IPhoneDao {

    @Query("SELECT * FROM i_phones ORDER BY price")
    fun stream(): Observable<List<IPhoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg iPhones: IPhoneEntity)

    @Query("DELETE FROM macs")
    fun removeAll()

    @Query("UPDATE i_phones SET favourite = :favourite WHERE id = :iPhoneId")
    fun updateFavourite(iPhoneId: String, favourite: Boolean)
}
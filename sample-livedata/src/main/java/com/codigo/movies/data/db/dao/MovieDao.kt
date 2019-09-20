package com.codigo.movies.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codigo.movies.domain.model.Movie

@Dao
interface MovieDao {

    @Query("select * from movies where type = :type")
    fun streamMovies(type: String): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)
}
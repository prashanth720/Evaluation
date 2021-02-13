package com.example.evaluation.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.evaluation.network.models.Photo

@Dao
interface DBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo : Photo) : Long

    @Query("SELECT * FROM photos")
    fun getAllPhotos() : LiveData<List<Photo>>

    @Delete
    suspend fun delete(photo: Photo) : Int
}
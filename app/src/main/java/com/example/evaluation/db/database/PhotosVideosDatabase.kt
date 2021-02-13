package com.example.evaluation.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.evaluation.db.Dao.DBDao
import com.example.evaluation.db.converters.Converters
import com.example.evaluation.network.models.Photo


@Database(entities = [Photo::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class PhotosVideosDatabase : RoomDatabase() {

    abstract fun getDao() : DBDao

    companion object {
        @Volatile
        private var instance : PhotosVideosDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context:Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PhotosVideosDatabase::class.java,
                "photos_videos_db.db"
            ).build()
    }
}
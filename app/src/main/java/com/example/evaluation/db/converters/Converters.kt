package com.example.evaluation.db.converters

import androidx.room.TypeConverter
import com.example.evaluation.network.models.Src


class Converters {
    @TypeConverter
    fun fromSource(source : Src): String? {
        return source.original
    }

    @TypeConverter
    fun toSource(original : String) : Src {
        return Src(original)
    }
 }
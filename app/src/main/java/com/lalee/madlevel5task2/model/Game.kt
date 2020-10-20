package com.lalee.madlevel5task2.model

import androidx.room.*
import java.util.*

@Entity
data class Game(

    var titel: String,
    var platform: Platform,
    var releaseDate: Date,

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

)

enum class Platform {
    XBOX, PLAYSTATION, PC
}

class Converters {

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toPlatform(value: String) = enumValueOf<Platform>(value)

    @TypeConverter
    fun fromPlatform(value: Platform) = value.name
}
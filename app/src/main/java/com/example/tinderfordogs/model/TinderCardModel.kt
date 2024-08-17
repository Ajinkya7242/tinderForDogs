package com.example.tinderfordogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "pets_tbl")
data class TinderCardModel(

    @PrimaryKey
    @ColumnInfo(name="message")
    val message: String,
    @ColumnInfo(name="noteStatus")
    val status: String
)
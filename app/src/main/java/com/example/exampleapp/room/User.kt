package com.example.exampleapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "id")@PrimaryKey(autoGenerate = true) var uid: Int? = null,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo(name = "number") var number: Int
)


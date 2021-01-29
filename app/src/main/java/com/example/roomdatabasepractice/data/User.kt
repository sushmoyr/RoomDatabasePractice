package com.example.roomdatabasepractice.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var firstName: String,
    var lastName: String,
    var age: Int):Parcelable


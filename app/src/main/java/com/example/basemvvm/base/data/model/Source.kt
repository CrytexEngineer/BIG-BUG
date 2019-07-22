package com.example.basemvvm.base.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverter
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Source(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?
) : Serializable
package com.wira_fkom.food_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val name: String = "",
    val imageResId: Int,
    val description: List<String> = listOf(),
    val isFavorite: Boolean = false,
    val email: String = "",
    val telepon: String = "",
    val github: String = "",
    var profileImage: String = ""
): Parcelable

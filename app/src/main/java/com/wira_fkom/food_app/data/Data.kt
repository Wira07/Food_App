package com.wira_fkom.food_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val name: String = "",
    val imageResId: Int,
    val description: List<String> = listOf(),
    val fullName: String = "",
    val email: String = "",
    val linkedIn: String = "",
    val telepon: String = "",
    val github: String = "",
    var profileImage: String = ""
): Parcelable

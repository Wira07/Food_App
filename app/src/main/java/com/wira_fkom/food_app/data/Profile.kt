package com.wira_fkom.food_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val action: String,
    val id: Int? = null,
    val email: String? = null,
    val whatsapp: String? = null,
    val instagram: String? = null,
    val github: String? = null
): Parcelable
package com.wira_fkom.food_app.about

import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.data.ProfileDao
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileDao: ProfileDao) {

    suspend fun addProfile(profile: Profile): Long {
        return profileDao.addProfile(profile)
    }

    suspend fun getProfile(id: Int): Profile? {
        return profileDao.getProfile(id)
    }

    suspend fun updateProfile(profile: Profile): Int {
        return profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile(profile: Profile): Int {
        return profileDao.deleteProfile(profile)
    }

    fun getAllProfiles(): Flow<List<Profile>> {
        return profileDao.getAllProfiles()
    }
}
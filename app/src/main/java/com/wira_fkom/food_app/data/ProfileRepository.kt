package com.wira_fkom.food_app.data

import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileDao: ProfileDao) {

    val allProfiles: Flow<List<Profile>> = profileDao.getAllProfiles()

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
}

package com.wira_fkom.food_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(profile: Profile): Long

    @Query("SELECT * FROM profile_table WHERE id = :id")
    suspend fun getProfile(id: Int): Profile?

    @Update
    suspend fun updateProfile(profile: Profile): Int

    @Delete
    suspend fun deleteProfile(profile: Profile): Int

    @Query("SELECT * FROM profile_table")
    fun getAllProfiles(): Flow<List<Profile>>
}

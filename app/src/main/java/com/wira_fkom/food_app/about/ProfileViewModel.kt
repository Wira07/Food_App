package com.wira_fkom.food_app.about

import androidx.lifecycle.*
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.data.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    val allProfiles: LiveData<List<Profile>> = repository.allProfiles.asLiveData()

    fun addProfile(profile: Profile) {
        viewModelScope.launch {
            repository.addProfile(profile)
        }
    }

    fun getProfile(id: Int): LiveData<Profile?> {
        val profile = MutableLiveData<Profile?>()
        viewModelScope.launch {
            profile.value = repository.getProfile(id)
        }
        return profile
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            repository.updateProfile(profile)
        }
    }

    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            repository.deleteProfile(profile)
        }
    }
}

//class ProfileViewModelFactory(private val repository: ProfileRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ProfileViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

package com.tanum.app.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tanum.app.model.repository.ActivityRepository
import com.tanum.app.model.repository.ArticleRepository
import com.tanum.app.model.repository.LandRepository
import com.tanum.app.model.repository.UserRepository
import com.tanum.app.di.Injection

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val landRepository: LandRepository,
    private val activityRepository: ActivityRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(ProfilViewModel::class.java) -> ProfilViewModel(userRepository) as T
            modelClass.isAssignableFrom(ArtikelViewModel::class.java) -> ArtikelViewModel(userRepository, articleRepository) as T
            modelClass.isAssignableFrom(BeritaDetailViewModel::class.java) -> BeritaDetailViewModel(userRepository, articleRepository) as T
            modelClass.isAssignableFrom(BeritaViewModel::class.java) -> BeritaViewModel(userRepository, articleRepository) as T
            modelClass.isAssignableFrom(VideoViewModel::class.java) -> VideoViewModel(userRepository, articleRepository) as T
            modelClass.isAssignableFrom(BerandaViewModel::class.java) -> BerandaViewModel(userRepository, articleRepository, landRepository) as T
            modelClass.isAssignableFrom(FormLahanViewModel::class.java) -> FormLahanViewModel(userRepository, landRepository) as T
            modelClass.isAssignableFrom(LahanSayaViewModel::class.java) -> LahanSayaViewModel(userRepository, landRepository) as T
            modelClass.isAssignableFrom(DetailLahanViewModel::class.java) -> DetailLahanViewModel(userRepository, landRepository, activityRepository) as T
            modelClass.isAssignableFrom(FormAktivitasViewModel::class.java) -> FormAktivitasViewModel(userRepository, activityRepository) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(userRepository) as T
             else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context),
                    Injection.provideArticleRepository(),
                    Injection.provideLandRepository(context),
                    Injection.provideActivityRepository(context)
                )
            }.also { instance = it }
    }

}
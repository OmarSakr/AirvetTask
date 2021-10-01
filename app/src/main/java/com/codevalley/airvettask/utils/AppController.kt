package com.codevalley.airvettask.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
//import com.codevalley.airvettask.main.favourite.repository.FavouriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.*


class AppController : MultiDexApplication() {

    private var sharedPreferencesLanguage: SharedPreferences? = null
    private val applicationScope = CoroutineScope(SupervisorJob())


//    private val database by lazy { ArticleRoomDatabase.getDatabase(this,applicationScope) }
//    val repository by lazy { FavouriteRepository(database.articleDao()) }

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesLanguage = getSharedPreferences("language", MODE_PRIVATE)


    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }


    //
    private fun setLocale() {
        val locale = Locale(ParentClass.getLang(this))
        Locale.setDefault(locale)
        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }


}
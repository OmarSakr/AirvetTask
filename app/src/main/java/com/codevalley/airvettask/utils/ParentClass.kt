package com.codevalley.airvettask.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.codevalley.airvettask.R
import com.squareup.picasso.Picasso
import java.util.*

open class ParentClass : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration = resources.configuration
        configuration.setLayoutDirection(Locale(getLang(this)))
        resources.updateConfiguration(configuration, resources.displayMetrics)
        sharedPreferences = getSharedPreferences("title", MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {

        fun loadImageWithPicasso(url: String?, context: Context?, imageView: ImageView?) {
            if (url != "") {
                Picasso.with(context).load(url).error(R.drawable.default_image).into(imageView)
            } else {
                Picasso.with(context).load(R.drawable.default_image).error(R.drawable.default_image)
                    .into(imageView)
            }
        }

        fun getLang(context: Context): String {
            val value = "en"
            val prefs = context.getSharedPreferences(
                "language", 0
            )
            return if (prefs.getString("language", "language") != "language") {
                prefs.getString("language", "language")!!
            } else {
                value
            }
        }
    }

    /**
     * check network availability
     */
    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


    fun makeToast(context: Context, msg: Int) {
        Toast.makeText(context, context.resources.getString(msg), Toast.LENGTH_SHORT).show()
    }

    fun makeToast(context: Context?, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }


    fun dismiss_keyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }


    fun storeLang(ln: String?, context: Context) {
        val settings = context.getSharedPreferences(
            "language",
            0
        )
        val editor = settings.edit()
        editor.putString("language", ln)
        editor.apply()
    }


    fun setDefaultLang(ln: String, context: Context) {
        val res = context.resources
        val locale = Locale(ln)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        res.updateConfiguration(config, res.displayMetrics)
        storeLang(ln, context)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }

    private fun setLocale() {
        val locale = Locale(getLang(this))
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }
}
package com.yipl.labelstep.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(application: Application) {
    var preferences: SharedPreferences

    init {
        preferences = application.getSharedPreferences(Preference.PREFERENCE_NAME, Context
                .MODE_PRIVATE)
    }

    var example: String
        get() = preferences.getString(Preference.EXAMPLE_DATA, "Error")
        set(data) = preferences.edit().putString(Preference.EXAMPLE_DATA, data).apply()

}
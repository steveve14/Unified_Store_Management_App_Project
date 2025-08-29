package com.example.deliveryapp.data.local

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREFS_NAME = "delivery_app_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(context: Context, token: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_AUTH_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(context: Context): String? {
        return getPreferences(context).getString(KEY_AUTH_TOKEN, null)
    }

    fun saveUserInfo(context: Context, name: String, email: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_USER_NAME, name)
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }

    fun fetchUserName(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_NAME, "Guest")
    }

    fun fetchUserEmail(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_EMAIL, "guest@example.com")
    }

    fun clearData(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}
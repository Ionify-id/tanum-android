package com.tanum.app.data

import android.content.Context

class AuthPreferences(context: Context) {
    companion object {
        private const val AUTH_PREFS = "auth_prefs"
        private const val TOKEN_VALUE = "token_value"
    }

    private val preferences = context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN_VALUE, token)
        editor.apply()
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN_VALUE, "")
    }

    fun clearToken() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

//    fun saveProfile() {
//
//    }
}
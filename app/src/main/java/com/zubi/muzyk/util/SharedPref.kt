package com.zubi.muzyk.util

import android.content.Context


object SharedPref {

    private const val SHARED_PREF = "shared_preference"

    /**
     * method to save int value in shared preferences
     * @param key     - key for value to save
     * @param value   - value to save
     */
    fun saveIntValueInSharedPreference(key: String?, value: Int) {
        val pref = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * method to get int value from shared preferences
     *
     * @param key     - key for value
     * @return - value against the key
     */

    fun getIntValueFromSharedPreference(key: String?): Int {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return prefs.getInt(key, -1)
    }

    /**
     * method to save boolean value in shared preferences
     *
     * @param key     - key for value to save
     * @param value   - value to save
     */

    fun saveBooleanVal(key: String?, value: Boolean) {
        val pref = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * method to get boolean value from shared preferences
     *
     * @param key     - key for value
     * @return - value against the key
     */

    fun getBooleanValue(key: String?): Boolean {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return prefs.getBoolean(key, false)
    }


    fun getBooleanDefaultTrueValue(key: String?): Boolean {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return prefs.getBoolean(key, true)
    }

    /**
     * method to save long value in shared preferences
     *
     * @param key     - key for value to save
     * @param value   - value to save
     */
    fun saveLongValueInSharedPreference(key: String?, value: Long) {
        val pref = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * method to get long value from shared preferences
     *
     * @param key     - key for value to save
     */
    fun getLongValueFromSharedPreference(key: String?): Long {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return prefs.getLong(key, 0)
    }


    fun saveDoubleValueInSharedPreference(key: String?, value: Double) {
        val pref = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        editor.apply()
    }


    fun getDoubleValueFromSharedPreference(key: String?): Double {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return java.lang.Double.longBitsToDouble(prefs.getLong(key, 0))
    }

    /**
     * method to save a string value in shared preferences
     *
     * @param context - context of calling activity
     * @param key     - key for value
     * @param value   - value of the key
     */

    fun saveStringVal(key: String?, value: String?) {
        val pref = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * method to get a string value from shared preferences
     *
     * @param key     - key for value
     * @return - value against the key
     */

    fun getStringVal(key: String?): String? {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun removeKey(key: String?) {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }

    fun removeAllKeysFromSharedPreference() {
        val prefs = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun hasKeyExists(key: String?): Boolean {
        val pref = appContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return pref.contains(key)
    }

}

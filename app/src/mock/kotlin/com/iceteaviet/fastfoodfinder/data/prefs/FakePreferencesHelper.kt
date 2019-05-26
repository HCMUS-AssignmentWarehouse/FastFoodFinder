package com.iceteaviet.fastfoodfinder.data.prefs

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by tom on 7/24/18.
 *
 *
 * Entry point contains methods that help interacting with application's SharedPreferences
 */

class FakePreferencesHelper : PreferencesHelper {

    private val preferenceMap: HashMap<String, Any>

    init {
        preferenceMap = HashMap()
    }

    override fun putString(key: String, value: String) {
        preferenceMap.put(key, value)
    }

    override fun getString(key: String, defaultValue: String): String {
        val value = preferenceMap.get(key) as String?
        return value ?: defaultValue
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferenceMap.put(key, value)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val value = preferenceMap.get(key) as Boolean?
        return value ?: defaultValue
    }

    override fun putInt(key: String, value: Int) {
        preferenceMap.put(key, value)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val value = preferenceMap.get(key) as Int?
        return value ?: defaultValue
    }

    override fun setStringSet(key: String, set: MutableSet<String>) {
        // Small hack to fix bug cannot update StringSet in SharedPreferences
        // The getStringSet() returns a reference of the stored HashSet object inside the SharedPreferences
        // @see: https://stackoverflow.com/questions/14034803/misbehavior-when-trying-to-store-a-string-set-using-sharedpreferences
        putInt(key + "_size", set.size)

        // Then store the StringSet itself
        preferenceMap.put(key, set)
    }

    override fun getStringSet(key: String, defaultValue: MutableSet<String>): MutableSet<String> {
        val value = preferenceMap.get(key) as MutableSet<String>?
        return value ?: defaultValue
    }

    override fun getAppLaunchFirstTime(): Boolean {
        return getBoolean(KEY_APP_LAUNCH_FIRST_TIME, true)
    }

    override fun setAppLaunchFirstTime(isFirstTime: Boolean) {
        putBoolean(KEY_APP_LAUNCH_FIRST_TIME, isFirstTime)
    }

    override fun getNumberOfStores(): Int {
        return getInt(KEY_NUMBER_OF_STORES, 0)
    }

    override fun setNumberOfStores(numberOfStores: Int) {
        putInt(KEY_NUMBER_OF_STORES, numberOfStores)
    }

    override fun getSearchHistories(): MutableSet<String> {
        return getStringSet(KEY_SEARCH_HISTORIES, LinkedHashSet())
    }

    override fun setSearchHistories(set: MutableSet<String>) {
        setStringSet(KEY_SEARCH_HISTORIES, set)
    }

    override fun getIfLanguageIsVietnamese(): Boolean {
        return getBoolean(KEY_LANGUAGE, false)
    }

    override fun setIfLanguageIsVietnamese(isVietnamese: Boolean) {
        putBoolean(KEY_LANGUAGE, isVietnamese)
    }

    companion object {
        private const val PREFS_NAME = "english_now_android"
        private const val KEY_APP_LAUNCH_FIRST_TIME = "app_launch_first_time"
        private const val KEY_NUMBER_OF_STORES = "number_of_stores"
        private const val KEY_SEARCH_HISTORIES = "search_histories"
        private const val KEY_SEARCH_HISTORIES_SIZE = "search_histories_size"
        private const val KEY_LANGUAGE = "lang"
    }
}

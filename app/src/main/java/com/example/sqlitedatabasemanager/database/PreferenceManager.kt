package com.example.sqlitedatabasemanager.database

import android.content.Context

class PreferenceManager(context: Context) {
    private val name = "sharedPreferance"
    private val mode = Context.MODE_PRIVATE
    private val preferance = context.getSharedPreferences(name, mode)

    // constants
    private val databaseVersion = "DATABASE_VERSION"

    // database version
    fun setDatabaseVersion(version: Int) {
        val edit = preferance.edit()
        edit.putInt(databaseVersion, version)
    }

    fun getDatabaseVersion() : Int {
        return preferance.getInt(databaseVersion, 0)
    }
}
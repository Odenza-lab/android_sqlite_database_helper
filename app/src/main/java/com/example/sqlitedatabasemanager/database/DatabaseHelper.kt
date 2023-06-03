package com.example.sqlitedatabasemanager.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
import com.example.sqlitedatabasemanager.models.MainDataModel
import java.io.FileOutputStream

class DatabaseHelper : SQLiteOpenHelper {
    private constructor(context: Context) : super(context, DB_NAME, null, DB_VERSION)

    companion object {
        private val DB_NAME = "my_database.sqlite"
        private val ASSET_NAME = "my_database.sqlite"
        private val DB_VERSION = 1
        private val ASSET_COPY_BUFFER_SIZE = 8 * 1024

        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context) : DatabaseHelper {
            if (instance == null) {
                copyAssetDatabase(context)
                instance = DatabaseHelper(context)
            }
            return instance as DatabaseHelper
        }

        private fun copyAssetDatabase(context: Context) {
            val isDatabaseExists = isDatabaseExists(context)
            val manager = PreferenceManager(context)
            val isLatestVersion = DB_VERSION == manager.getDatabaseVersion()

            // update database
            if (isDatabaseExists && !isLatestVersion) {
                context.deleteDatabase(DB_NAME)
                manager.setDatabaseVersion(DB_VERSION)
            }

            if (isDatabaseExists) return

            context.assets.open(ASSET_NAME).copyTo(
                FileOutputStream(context.getDatabasePath(DB_NAME)),
                ASSET_COPY_BUFFER_SIZE
            )
        }

        private fun isDatabaseExists(context: Context) : Boolean {
            val dbFile = context.getDatabasePath(DB_NAME)
            if (dbFile.exists()) return true
            else if (!dbFile.parentFile.exists()) {
                dbFile.parentFile.mkdirs()
            }
            return false
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    // retrieve data
    @SuppressLint("Range")
    fun retrieveData() : List<MainDataModel> {
        val results = ArrayList<MainDataModel>()
        val db: SQLiteDatabase = writableDatabase
        val selectQuery = "SELECT * FROM main"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                    val kanji = cursor.getStringOrNull(cursor.getColumnIndex("kanji"))
                    val yomikata = cursor.getStringOrNull(cursor.getColumnIndex("yomikata"))
                    val english = cursor.getStringOrNull(cursor.getColumnIndex("english"))
                    val dataModel = MainDataModel(id, kanji, yomikata, english)
                    results.add(dataModel)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return results
    }
}
package com.example.desafioglobo.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.desafioglobo.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "results.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_RESULTS = "results"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ADULT = "adult"
        private const val COLUMN_BACKDROP_PATH = "backdrop_path"
        private const val COLUMN_GENRE_IDS = "genre_ids"
        private const val COLUMN_ORIGINAL_LANGUAGE = "original_language"
        private const val COLUMN_ORIGINAL_TITLE = "original_title"
        private const val COLUMN_OVERVIEW = "overview"
        private const val COLUMN_POPULARITY = "popularity"
        private const val COLUMN_POSTER_PATH = "poster_path"
        private const val COLUMN_RELEASE_DATE = "release_date"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_VIDEO = "video"
        private const val COLUMN_VOTE_AVERAGE = "vote_average"
        private const val COLUMN_VOTE_COUNT = "vote_count"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_RESULTS (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_ADULT INTEGER,
                $COLUMN_BACKDROP_PATH TEXT,
                $COLUMN_GENRE_IDS TEXT,
                $COLUMN_ORIGINAL_LANGUAGE TEXT,
                $COLUMN_ORIGINAL_TITLE TEXT,
                $COLUMN_OVERVIEW TEXT,
                $COLUMN_POPULARITY REAL,
                $COLUMN_POSTER_PATH TEXT,
                $COLUMN_RELEASE_DATE TEXT,
                $COLUMN_TITLE TEXT,
                $COLUMN_VIDEO INTEGER,
                $COLUMN_VOTE_AVERAGE REAL,
                $COLUMN_VOTE_COUNT INTEGER
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_RESULTS")
        onCreate(db)
    }

    fun insertResult(result: Result): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ID, result.id)
            put(COLUMN_ADULT, if (result.adult == true) 1 else 0)
            put(COLUMN_BACKDROP_PATH, result.backdrop_path)
            put(COLUMN_GENRE_IDS, result.genre_ids?.joinToString(",") ?: "")
            put(COLUMN_ORIGINAL_LANGUAGE, result.original_language)
            put(COLUMN_ORIGINAL_TITLE, result.original_title)
            put(COLUMN_OVERVIEW, result.overview)
            put(COLUMN_POPULARITY, result.popularity)
            put(COLUMN_POSTER_PATH, result.poster_path)
            put(COLUMN_RELEASE_DATE, result.release_date)
            put(COLUMN_TITLE, result.title)
            put(COLUMN_VIDEO, if (result.video == true) 1 else 0)
            put(COLUMN_VOTE_AVERAGE, result.vote_average)
            put(COLUMN_VOTE_COUNT, result.vote_count)
        }

        return db.insertWithOnConflict(
            TABLE_RESULTS,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun deleteResultById(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_RESULTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun getAllResults(): List<com.example.desafioglobo.model.Result> {
        val results = mutableListOf<com.example.desafioglobo.model.Result>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_RESULTS, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val genreIdsType = object : TypeToken<List<Int>>() {}.type
                val genreIds = Gson().fromJson<List<Int>>(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE_IDS)), genreIdsType)

                results.add(
                    com.example.desafioglobo.model.Result(
                        adult = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADULT)) == 1,
                        backdrop_path = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COLUMN_BACKDROP_PATH
                            )
                        ),
                        genre_ids = genreIds,
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        original_language = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COLUMN_ORIGINAL_LANGUAGE
                            )
                        ),
                        original_title = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COLUMN_ORIGINAL_TITLE
                            )
                        ),
                        overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)),
                        popularity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_POPULARITY)),
                        poster_path = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COLUMN_POSTER_PATH
                            )
                        ),
                        release_date = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COLUMN_RELEASE_DATE
                            )
                        ),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        video = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIDEO)) == 1,
                        vote_average = cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                COLUMN_VOTE_AVERAGE
                            )
                        ),
                        vote_count = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VOTE_COUNT))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return results
    }

    fun clearResults() {
        writableDatabase.execSQL("DELETE FROM $TABLE_RESULTS")
    }
}

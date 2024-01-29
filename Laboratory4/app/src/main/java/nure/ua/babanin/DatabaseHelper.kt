package nure.ua.babanin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "notes.db", null, 1){

    val TABLE_NAME = "notes"
    val COLUMN_ID = "_id"
    val COLUMN_TITLE = "title"
    val COLUMN_DESCRIPTION = "description"
    val COLUMN_IMPORTANCE = "importance"
    val COLUMN_CREATION_TIME = "creation_time"
    val COLUMN_IMAGE_URI = "image_uri"

    private val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME" + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TITLE + " TEXT," +
            COLUMN_DESCRIPTION + " TEXT," +
            COLUMN_IMPORTANCE + " TEXT," +
            COLUMN_CREATION_TIME + " INTEGER," +
            COLUMN_IMAGE_URI + " TEXT" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {

        if (db == null)
        {
            return;
        }

        db.execSQL(SQL_CREATE_TABLE);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // No-op
    }
}
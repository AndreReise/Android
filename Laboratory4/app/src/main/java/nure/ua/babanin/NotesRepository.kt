package nure.ua.babanin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri


class NotesRepository(var context: Context) {

    lateinit var helper: DatabaseHelper

    init {
        helper = DatabaseHelper(context)
    }

    fun addNote(note: Note) {
        val database = helper.writableDatabase

        val values = ContentValues()
        values.put(helper.COLUMN_TITLE, note.title)
        values.put(helper.COLUMN_DESCRIPTION, note.description)
        values.put(helper.COLUMN_IMPORTANCE, note.importance.toString())
        values.put(helper.COLUMN_CREATION_TIME, note.creationTime)
        values.put(helper.COLUMN_IMAGE_URI, note.imageUri.toString())

        database.insert(helper.TABLE_NAME, null, values)

        database.close();
    }

    fun updateNote(note: Note) {
        val database = helper.writableDatabase

        val values = ContentValues()
        values.put(helper.COLUMN_TITLE, note.title)
        values.put(helper.COLUMN_DESCRIPTION, note.description)
        values.put(helper.COLUMN_IMPORTANCE, note.importance.toString())
        values.put(helper.COLUMN_CREATION_TIME, note.creationTime)
        values.put(helper.COLUMN_IMAGE_URI, note.imageUri.toString())

        database.update(
            helper.TABLE_NAME,
            values,
            helper.COLUMN_ID + "=?", arrayOf<String>(note.id.toString()))

        database.close()
    }

    fun removeNote(noteId: Int) {
        val database = helper.writableDatabase

        database.delete(
            helper.TABLE_NAME,
            helper.COLUMN_ID + "=?",
            arrayOf( noteId.toString()))

        database.close()
    }

    @SuppressLint("Range")
    fun getById(noteId: Int): Note? {
        val database = helper.readableDatabase
        val selection = helper.COLUMN_ID + "=?"
        val selectionArgs = arrayOf(noteId.toString())

        val cursor = database.query(
            helper.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null)

        var result: Note? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(helper.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(helper.COLUMN_TITLE))
            val description =
                cursor.getString(cursor.getColumnIndex(helper.COLUMN_DESCRIPTION))
            val importanceValue =
                cursor.getInt(cursor.getColumnIndex(helper.COLUMN_IMPORTANCE))
            val creationTime =
                cursor.getLong(cursor.getColumnIndex(helper.COLUMN_CREATION_TIME))
            val imageUri =
                cursor.getString(cursor.getColumnIndex(helper.COLUMN_IMAGE_URI))
            val importance = Importance.entries[importanceValue]

            var parsedUri: Uri? = null

            if (imageUri != "null")
            {
                parsedUri = Uri.parse(imageUri)
            }

            result = Note(
                id,
                title,
                description,
                importance,
                creationTime,
                parsedUri)
        }

        cursor.close()
        database.close()

        return result
    }

    @SuppressLint("Range")
    fun getAllNotes(): List<Note> {
        val database = helper.readableDatabase
        val cursor = database.query(
            helper.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null)

        var notes = mutableListOf<Note>()

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(helper.COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(helper.COLUMN_TITLE))
                val description =
                    cursor.getString(cursor.getColumnIndex(helper.COLUMN_DESCRIPTION))
                val importanceValue =
                    cursor.getInt(cursor.getColumnIndex(helper.COLUMN_IMPORTANCE))
                val creationTime =
                    cursor.getLong(cursor.getColumnIndex(helper.COLUMN_CREATION_TIME))
                val imageUri =
                    cursor.getString(cursor.getColumnIndex(helper.COLUMN_IMAGE_URI))
                val importance = Importance.entries[importanceValue]

                var parsedUri: Uri? = null

                if (imageUri != "null")
                {
                    parsedUri = Uri.parse(imageUri)
                }


                val note = Note(
                    id,
                    title,
                    description,
                    importance,
                    creationTime,
                    parsedUri)

                notes.add(note)
            } while (cursor.moveToNext())

            cursor.close()
            database.close()
        }

        return notes.toList();
    }
}
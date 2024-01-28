package nure.ua.babanin

import java.time.Instant

object NotesRepository {

    private var incrementingCounter : Int = 3;

    private val noteList = mutableMapOf<Int, Note>().apply {
        put(
            1,
            Note(
            1,
            "Super high important note",
            "Here can be your description",
            Importance.High,
            Instant.now().toEpochMilli(),
            null))
        put(
            2,
            Note(
                2,
                "Not so important",
                "Here can be your description",
                Importance.Low,
                Instant.now().toEpochMilli(),
                null)
        )
    }

    fun addNote(note: Note) {
        note.id = incrementingCounter++;
        noteList.put(note.id, note)
    }

    fun updateNote(note: Note){
        noteList.replace(note.id, note)
    }
    fun removeNote(noteId: Int){
        noteList.remove(noteId)
    }

    fun getById(noteId: Int) : Note?{
        return noteList.get(noteId)
    }
    fun getAllNotes(): List<Note> {
        return noteList.values.toList()
    }
}
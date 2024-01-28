package nure.ua.babanin.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nure.ua.babanin.Importance
import nure.ua.babanin.Note
import nure.ua.babanin.NotesRepository
import java.time.Instant
import java.time.LocalDateTime

class NoteViewModel : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()

    val notes: LiveData<List<Note>> get() = _notes;

    fun loadData() {

        val data = NotesRepository.getAllNotes()
        _notes.value = data
    }
}
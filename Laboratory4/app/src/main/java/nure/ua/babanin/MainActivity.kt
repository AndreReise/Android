package nure.ua.babanin

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nure.ua.babanin.activity.NoteEditingActivity
import nure.ua.babanin.adapter.NotesArrayAdapter
import nure.ua.babanin.view.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createButton: Button = findViewById(R.id.create_note_btn)
            createButton.setOnClickListener { _ ->
                val creationIntent = Intent(this, NoteEditingActivity::class.java)

                 startActivity(creationIntent) }

        val languageSpinner: Spinner = findViewById(R.id.spinner_languages)

        ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_dropdown_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                languageSpinner.adapter = adapter
            }

        languageSpinner.onItemSelectedListener = LanguageSpinnerSelectionListener(this)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        noteViewModel.notes.observe(this, Observer { notes ->
            val recyclerView: RecyclerView = findViewById(R.id.notes_list_view)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = NotesArrayAdapter(this, notes)
        })

        noteViewModel.loadData()
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val view = item.actionView
        val noteId = (view?.tag as? NotesArrayAdapter.NoteViewHolder)?.assosiatedNote?.id ?: -1

        if (noteId == -1)
        {
            return true;
        }

        when (item.itemId) {
            1 -> {

                val editingIntent = Intent(this, NoteEditingActivity::class.java)
                editingIntent.putExtra("NOTE", NotesRepository.getById(noteId))

                startActivity(editingIntent)

                return true
            }
            2 -> {
                NotesRepository.removeNote(noteId)

                // Trigger refresh
                noteViewModel.loadData()

                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        noteViewModel.loadData()
    }
}
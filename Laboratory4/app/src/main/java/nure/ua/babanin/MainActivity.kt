package nure.ua.babanin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nure.ua.babanin.activity.NoteEditingActivity
import nure.ua.babanin.adapter.NotesArrayAdapter
import nure.ua.babanin.view.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    lateinit var repository: NotesRepository

    private var isDarkThemeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = NotesRepository(this.applicationContext)

        val themeFragment = ThemeSwitchFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.change_theme_container, themeFragment)
            .commit()

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

        noteViewModel = NoteViewModel(this)

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
                editingIntent.putExtra("NOTE", repository.getById(noteId))

                try {
                    startActivity(editingIntent)
                }
                catch (exception: Exception){
                    Log.d("lifecycle", exception.message!!)
                }

                return true
            }
            2 -> {
                repository.removeNote(noteId)

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
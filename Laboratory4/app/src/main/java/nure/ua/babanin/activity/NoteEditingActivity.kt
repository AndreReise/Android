package nure.ua.babanin.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import nure.ua.babanin.Importance
import nure.ua.babanin.Note
import nure.ua.babanin.NotesRepository
import nure.ua.babanin.R
import nure.ua.babanin.adapter.ImportanceAdapter
import java.time.Instant

class NoteEditingActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1

    lateinit var imageView: ImageView
    lateinit var titleEditText: EditText
    lateinit var descriptionEditText: EditText

    lateinit var importanceAdapter: ImportanceAdapter
    lateinit var importanceTextView: AutoCompleteTextView

    lateinit var submitButton: Button

    var selectedImageUri: Uri? = null

    private var noteToEdit: Note? = null

    lateinit var repository: NotesRepository

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editing)

        repository = NotesRepository(this.applicationContext)

        imageView = findViewById(R.id.image)
        imageView.setOnClickListener{
            openImagePicker()
        }

        titleEditText = findViewById(R.id.title_edit)
        descriptionEditText = findViewById(R.id.description_edit)

        val importances = arrayOf(Importance.Low, Importance.Medium, Importance.High)
        importanceAdapter = ImportanceAdapter(this, android.R.layout.simple_spinner_item, importances)
        importanceTextView = findViewById(R.id.importance_selector)
        importanceTextView.setAdapter(importanceAdapter)

        submitButton = findViewById(R.id.create_button)

        noteToEdit = intent.getParcelableExtra("NOTE", Note::class.java)

        if (noteToEdit != null)
        {
            titleEditText.setText(noteToEdit!!.title)
            descriptionEditText.setText(noteToEdit!!.description)

            val position =  arrayOf(Importance.Low, Importance.Medium, Importance.High)
                .indexOf(noteToEdit!!.importance)

            importanceTextView.setText(importanceAdapter.getItem(position).toString(), false)

            if (noteToEdit!!.imageUri != null)
            {
                this.selectedImageUri = noteToEdit!!.imageUri!!
                imageView.setImageURI(selectedImageUri)
            }
        }

        submitButton.setOnClickListener { view ->
            if (noteToEdit == null)
            {
                val newNote = Note(
                    id = 0,
                    titleEditText.text.toString(),
                    descriptionEditText.text.toString(),
                    Importance.Low,
                    Instant.now().toEpochMilli(),
                    imageUri = selectedImageUri
                )

                repository.addNote(newNote)
            }
            else
            {
                noteToEdit!!.creationTime = Instant.now().toEpochMilli()
                noteToEdit!!.description = descriptionEditText.text.toString()
                noteToEdit!!.title = titleEditText.text.toString()
                noteToEdit!!.imageUri = selectedImageUri

                repository.updateNote(noteToEdit!!)
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun openImagePicker() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            // Set the selected image to the ImageView
            imageView.setImageURI(selectedImageUri)
        }
    }
}
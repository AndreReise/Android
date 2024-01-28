package nure.ua.babanin.adapter

import android.content.Context
import android.media.Image
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nure.ua.babanin.Importance
import nure.ua.babanin.Note
import nure.ua.babanin.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.coroutines.coroutineContext


class NotesArrayAdapter(private val context: Context, private val notes: List<Note>) : RecyclerView.Adapter<NotesArrayAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener
    {
        val pictureImageView: ImageView = itemView.findViewById(R.id.image)
        val titleTextView: TextView = itemView.findViewById(R.id.title_text)
        val creationTimeView: TextView = itemView.findViewById(R.id.date)
        val importanceImageView: ImageView = itemView.findViewById(R.id.importance)

        lateinit var assosiatedNote: Note

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(note: Note)
        {
            this.assosiatedNote = note

            // Set the tag to associate the NoteViewHolder with the view
            itemView.tag = this
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(Menu.NONE, 1, Menu.NONE, R.string.context_menu_edit)?.apply { actionView = v}
            menu?.add(Menu.NONE, 2, Menu.NONE, R.string.context_menu_delete)?.apply { actionView = v}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note_short, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]

        if (currentNote.imageUri == null)
        {
            holder.pictureImageView.setImageResource(R.drawable.no_img)
        }
        else
        {
            holder.pictureImageView.setImageURI(currentNote.imageUri)
        }

        holder.titleTextView.text = currentNote.title
        holder.creationTimeView.text = buildDateFromUnixMilliseconds(currentNote.creationTime)
        holder.importanceImageView.setImageResource(resolveImportanceImage(currentNote.importance))

        holder.bind(currentNote)
    }

    fun buildDateFromUnixMilliseconds(unixMilliseconds: Long): String {
        val date = Date(unixMilliseconds)
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy",  this.context.resources.configuration.locale)
        return simpleDateFormat.format(date)
    }

    fun resolveImportanceImage(importance: Importance): Int
    {
        return when (importance){
            Importance.Low -> R.drawable.img_l
            Importance.Medium -> R.drawable.img_m
            Importance.High -> R.drawable.img_h
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
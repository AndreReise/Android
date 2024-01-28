package nure.ua.babanin

import android.net.Uri
import java.io.Serializable

data class Note(
    var id: Int,
    var title: String,
    var description: String,
    var importance: Importance,
    var creationTime: Long,
    var imageUri: Uri?
) : Serializable

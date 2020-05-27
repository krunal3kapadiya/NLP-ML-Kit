package app.krunal3kapadiya.nlp_mlkit.smartreply

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import app.krunal3kapadiya.nlp_mlkit.R

class Message(val text: String, val isLocalUser: Boolean, val timestamp: Long) {

    fun getIcon(context: Context): Drawable {
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_tag_faces_black_24dp)
            ?: throw IllegalStateException("Could not get drawable ic_tag_faces_black_24dp")

        if (isLocalUser) {
            DrawableCompat.setTint(drawable, Color.BLUE)
        } else {
            DrawableCompat.setTint(drawable, Color.RED)
        }

        return drawable
    }
}

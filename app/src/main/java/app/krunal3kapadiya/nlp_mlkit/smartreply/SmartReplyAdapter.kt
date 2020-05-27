package app.krunal3kapadiya.nlp_mlkit.smartreply

import androidx.core.content.ContextCompat
import app.krunal3kapadiya.nlp_mlkit.R

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class SmartReplyAdapter(private val context: Context, private val messagesList: List<Message>) : RecyclerView.Adapter<SmartReplyAdapter.SmartReplyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmartReplyViewHolder {
        return SmartReplyViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false))
    }
    var emulatingRemoteUser = false
        set(emulatingRemoteUser) {
            field = emulatingRemoteUser
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: SmartReplyViewHolder, position: Int) {
        val message = messagesList[position]
        holder.bind(message)
    }

    override fun getItemViewType(position: Int): Int {
        return if (
            messagesList[position].isLocalUser && !emulatingRemoteUser ||
            !messagesList[position].isLocalUser && emulatingRemoteUser) {
            R.layout.item_message_local
        } else {
            R.layout.item_message_remote
        }
    }

    override fun getItemCount() = messagesList.size

    class SmartReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: CircleImageView
        private val text: TextView

        init {
            icon = itemView.findViewById(R.id.messageAuthor)
            text = itemView.findViewById(R.id.messageText)
        }

        fun bind(message: Message) {
            icon.setImageDrawable(message.getIcon(icon.context))
            text.text = message.text
        }
    }
}
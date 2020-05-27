package app.krunal3kapadiya.nlp_mlkit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.krunal3kapadiya.nlp_mlkit.smartreply.SmartReplyAdapter
import app.krunal3kapadiya.nlp_mlkit.smartreply.SmartReplyConversationModel
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseTextMessage
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestionResult
import kotlinx.android.synthetic.main.activity_generate_smart_reply.*

class GenerateSmartReplyActivity : AppCompatActivity(), View.OnClickListener {
    private val smartReplyConversation = ArrayList<SmartReplyConversationModel>()
    private val firebaseSmartReplyConversation = ArrayList<FirebaseTextMessage>()

    companion object {
        val TAG = GenerateSmartReplyActivity::class.java.simpleName

        fun launch(context: Context) {
            context.startActivity(Intent(context, GenerateSmartReplyActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_smart_reply)

        smart_reply_recycler_view.adapter = SmartReplyAdapter(this, smartReplyConversation)

        smart_reply_him_her_send_button.setOnClickListener(this)
        smart_reply_you_send_button.setOnClickListener(this)
        smart_reply_reply_button1.setOnClickListener(this)
        smart_reply_reply_button2.setOnClickListener(this)
        smart_reply_reply_button3.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.smart_reply_him_her_send_button -> onSendButtonClicked(false, smart_reply_him_her_edit_text)
            R.id.smart_reply_you_send_button -> onSendButtonClicked(true, smart_reply_you_edit_text)
            R.id.smart_reply_reply_button1 -> onSmartReplyButtonClicked(smart_reply_reply_button1.text.trim().toString())
            R.id.smart_reply_reply_button2 -> onSmartReplyButtonClicked(smart_reply_reply_button2.text.trim().toString())
            R.id.smart_reply_reply_button3 -> onSmartReplyButtonClicked(smart_reply_reply_button3.text.trim().toString())
        }
    }

    private fun onSendButtonClicked(isLocalUser: Boolean, editText: EditText) {
        val message = editText.text.trim().toString()

        // add message to smartReplyConversation
        smartReplyConversation.add(SmartReplyConversationModel(isLocalUser, message))
        smart_reply_recycler_view.adapter?.notifyItemInserted(smartReplyConversation.size - 1)
        smart_reply_recycler_view.scrollToPosition(smartReplyConversation.size - 1)
        editText.text.clear()
        // add message to firebaseSmartReplyConversation
        if (isLocalUser)
            firebaseSmartReplyConversation.add(FirebaseTextMessage.createForLocalUser(message, System.currentTimeMillis()))
        else
            firebaseSmartReplyConversation.add(FirebaseTextMessage.createForRemoteUser(message, System.currentTimeMillis(), "him_her_007"))
        // get smart replies
        FirebaseNaturalLanguage.getInstance().smartReply.suggestReplies(firebaseSmartReplyConversation)
            .addOnSuccessListener { result ->
                if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS && result.suggestions.size == 3) {
                    smart_reply_replies_linear_layout.visibility = View.VISIBLE
                    smart_reply_reply_button1.text = result.suggestions[0].text
                    smart_reply_reply_button2.text = result.suggestions[1].text
                    smart_reply_reply_button3.text = result.suggestions[2].text
                }
            }

        smart_reply_replies_linear_layout.visibility = View.INVISIBLE
    }

    private fun onSmartReplyButtonClicked(message: String) {

        // add message to smartReplyConversation
        smartReplyConversation.add(SmartReplyConversationModel(true, message))
        smart_reply_recycler_view.adapter?.notifyItemInserted(smartReplyConversation.size - 1)
        smart_reply_recycler_view.scrollToPosition(smartReplyConversation.size - 1)
        // add message to firebaseSmartReplyConversation
        firebaseSmartReplyConversation.add(FirebaseTextMessage.createForLocalUser(message, System.currentTimeMillis()))
        // get smart replies
        FirebaseNaturalLanguage.getInstance().smartReply.suggestReplies(firebaseSmartReplyConversation)
            .addOnSuccessListener { result ->
                if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS && result.suggestions.size == 3) {
                    smart_reply_replies_linear_layout.visibility = View.VISIBLE
                    smart_reply_reply_button1.text = result.suggestions[0].text
                    smart_reply_reply_button2.text = result.suggestions[1].text
                    smart_reply_reply_button3.text = result.suggestions[2].text
                }
            }

        smart_reply_replies_linear_layout.visibility = View.INVISIBLE
    }
}
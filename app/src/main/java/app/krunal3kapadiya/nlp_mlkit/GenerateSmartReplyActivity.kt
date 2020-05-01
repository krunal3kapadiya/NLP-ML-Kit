package app.krunal3kapadiya.nlp_mlkit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GenerateSmartReplyActivity : AppCompatActivity() {

    companion object {
        val TAG = GenerateSmartReplyActivity::class.java.simpleName

        fun launch(context: Context) {
            context.startActivity(Intent(context, GenerateSmartReplyActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_smart_reply)
    }
}
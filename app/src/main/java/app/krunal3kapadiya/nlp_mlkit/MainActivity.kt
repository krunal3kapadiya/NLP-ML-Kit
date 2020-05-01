package app.krunal3kapadiya.nlp_mlkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionSet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_go_to_identify_language.setOnClickListener {
            IdentifyLanguageActivity.launch(this)
        }
        bt_go_to_smart_reply.setOnClickListener {
            GenerateSmartReplyActivity.launch(this)
        }
        bt_go_to_translate_text.setOnClickListener {
            TranslateTextActivity.launch(this)
        }
    }
}

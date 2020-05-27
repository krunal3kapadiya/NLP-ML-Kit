package app.krunal3kapadiya.nlp_mlkit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                AboutUsActivity.launch(this@MainActivity)
            }
        }
        return true
    }
}

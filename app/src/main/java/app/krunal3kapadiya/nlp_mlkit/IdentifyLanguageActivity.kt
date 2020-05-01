package app.krunal3kapadiya.nlp_mlkit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification
import kotlinx.android.synthetic.main.activity_identify_language.*


class IdentifyLanguageActivity : AppCompatActivity() {

    companion object {
        val TAG = IdentifyLanguageActivity::class.java.simpleName

        fun launch(context: Context) {
            context.startActivity(Intent(context, IdentifyLanguageActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identify_language)

        val languageIdentifier: FirebaseLanguageIdentification =
            FirebaseNaturalLanguage.getInstance().languageIdentification

        bt_identify_language.setOnClickListener {
            if (et_enter_languages.text!!.isEmpty()){
                et_enter_languages.error = getString(R.string.error_message_identify)
                return@setOnClickListener
            }
            languageIdentifier.identifyLanguage(et_enter_languages.text.toString())
                .addOnSuccessListener { languageCode ->
                    if (languageCode !== "und") {
                        txt_result.text = getString(R.string.identified_language_is, languageCode)
                        Log.i(TAG, "Language: $languageCode")
                    } else {
                        txt_result.text = getString(R.string.cannot_identify_language)
                        Log.i(TAG, "Can't identify language.")
                    }
                }
                .addOnFailureListener { e ->
                    txt_result.text = e.printStackTrace().toString()
                }
        }

        bt_show_all_possibility_language.setOnClickListener {
            if (et_enter_languages.text!!.isEmpty()){
                et_enter_languages.error = getString(R.string.error_message_identify)
                return@setOnClickListener
            }
            languageIdentifier.identifyPossibleLanguages(et_enter_languages.text.toString())
                .addOnSuccessListener {
                    val stringBuilder = StringBuilder()
                    it.map { identifiedLanguage ->
                        stringBuilder.append(
                            identifiedLanguage.languageCode.plus(
                                ", ".plus(
                                    identifiedLanguage.confidence
                                ).plus("\n")
                            )
                        )
                    }
                    txt_result.text = getString(R.string.all_possibilities_are, stringBuilder.toString())
                }
                .addOnFailureListener {
                    // Model couldn’t be loaded or other internal error.
                    // ...
                    txt_result.text = it.printStackTrace().toString()
                }
        }
    }
}

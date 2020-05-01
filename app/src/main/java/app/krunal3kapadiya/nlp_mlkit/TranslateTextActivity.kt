package app.krunal3kapadiya.nlp_mlkit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateRemoteModel
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.activity_translate_text.*

class TranslateTextActivity : AppCompatActivity() {

    companion object{
        val TAG = TranslateTextActivity::class.java.simpleName

        fun launch(context: Context){
            context.startActivity(Intent(context, TranslateTextActivity::class.java))
        }
    }

    val modelManager = FirebaseModelManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate_text)

        showAvailableModels()

        // Create an English-Hindi translator:
        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(FirebaseTranslateLanguage.EN)
            .setTargetLanguage(FirebaseTranslateLanguage.GU)
            .build()
        val englishGujaratiTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options)

        bt_download_gu_model.setOnClickListener {
            englishGujaratiTranslator.downloadModelIfNeeded()
                .addOnSuccessListener {
                    // Model downloaded successfully. Okay to start translating.
                    showDialog(getString(R.string.gu_model_download_success))
                    showAvailableModels()
                }
                .addOnFailureListener {
                    // Model couldn’t be downloaded or other internal error.
                    showDialog(getString(R.string.model_not_downloaded))
                }
        }


        bt_translate.setOnClickListener {
            if (et_translate_text.text?.isEmpty()!!){
                et_translate_text.error = getString(R.string.enter_text_to_translate)
                return@setOnClickListener
            }
            englishGujaratiTranslator.translate(et_translate_text.text.toString())
                .addOnSuccessListener {
                    // Translation successful.
                    txt_translate_text_result.text = it
                }
                .addOnFailureListener {
                    // Error.
                    txt_translate_text_result.text = "Error occurred $it"
                }
        }

        bt_delete_gu_model.setOnClickListener {
            val guModel = FirebaseTranslateRemoteModel.Builder(FirebaseTranslateLanguage.GU).build()
            modelManager.deleteDownloadedModel(guModel)
                .addOnSuccessListener {
                    // Model downloaded.
                    showDialog(getString(R.string.model_deleted_message))
                    showAvailableModels()
                }
                .addOnFailureListener {
                    // Error.
                    Log.d(TAG, "Error occurred while deleting model")
                }
        }
    }

    private fun showAvailableModels() {
        // Get translation models stored on the device.
        modelManager.getDownloadedModels(FirebaseTranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                val stringBuffer = StringBuffer()
                models.map {
                    stringBuffer.append(it.languageCode.plus("\n"))
                }
                text_available_models.text = stringBuffer.toString()
            }
            .addOnFailureListener {
                // Error.
                Log.d(TAG, "Error while fetching available models")
            }
    }

    private fun showDialog(message:String){
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                // do nothing
            }
            .create()
            .show()
    }
}

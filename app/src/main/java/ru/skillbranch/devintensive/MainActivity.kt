package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var messageEt: EditText
    lateinit var textTxt: TextView
    lateinit var benderImage: ImageView
    lateinit var sendImg: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageEt = et_message
        textTxt = tv_text
        benderImage = iv_bender
        sendImg = iv_send

        sendImg.setOnClickListener {handleClick()}
        messageEt.setOnEditorActionListener { _, actionId, _ ->
            when(actionId){
                EditorInfo.IME_ACTION_DONE -> {
                    handleClick()
                    hideKeyboard()
                    true
                }
                else -> false
            }
        }

        val status = savedInstanceState?.getString("status") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("question") ?: Bender.Question.NAME.name

        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        val (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = benderObj.askQuestion()
    }

    private fun handleClick(){
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase(Locale.ROOT))
        messageEt.setText("")
        val(r,g,b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("status", benderObj.status.name)
        outState?.putString("question", benderObj.question.name)
    }
}

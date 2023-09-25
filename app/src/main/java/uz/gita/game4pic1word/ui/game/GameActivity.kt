package uz.gita.game4pic1word.ui.game

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import uz.gita.game4pic1word.R
import uz.gita.game4pic1word.data.Question
import uz.gita.game4pic1word.repository.Settings

class GameActivity : AppCompatActivity(), GameContract.View {
    private val images = ArrayList<AppCompatImageView>(4)
    private val variants = ArrayList<AppCompatTextView>(16)
    private val answer = ArrayList<AppCompatTextView>(8)
    private lateinit var backButton: AppCompatImageView

    private lateinit var txtCoin: TextView
    private lateinit var txtLevel: TextView
    private lateinit var sos: TextView

    private lateinit var presenter:GamePresenter
    private lateinit var settings: Settings
    private lateinit var dialog: Dialog
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        settings = Settings.getINstance()
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        loadViews()
        presenter = GamePresenter(this)
        restoreScore()
        attachClickListeners()
    }
    private fun restoreScore(){
        val coin = settings.coins
        val level = settings.level
        txtCoin.text = "$coin"
        txtLevel.text = "$level"
    }

    private fun loadViews() {
        images.add(findViewById(R.id.imageOne))
        images.add(findViewById(R.id.imageTwo))
        images.add(findViewById(R.id.imageThree))
        images.add(findViewById(R.id.imageFour))

        txtCoin = findViewById(R.id.coin)
        txtLevel = findViewById(R.id.level)
        sos = findViewById(R.id.btn_help)

        val variantLine1: LinearLayoutCompat = findViewById(R.id.variantLine1)
        val variantLine2: LinearLayoutCompat = findViewById(R.id.variantLine2)

        for (i in 0 until variantLine1.childCount){
            variants.add(variantLine1.getChildAt(i) as AppCompatTextView)
        }
        for (i in 0 until variantLine2.childCount){
            variants.add(variantLine2.getChildAt(i) as AppCompatTextView)
        }
        variants.shuffle()

        val answeLine: LinearLayoutCompat = findViewById(R.id.answerLine)
        for (i in 0  until answeLine.childCount){
            answer.add(answeLine.getChildAt(i) as AppCompatTextView)
        }

        backButton = findViewById(R.id.btn_back_in_game)


    }

    override fun showDialog(header:String, msg: String, txtYes:String, txtNo:String){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_item)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val title: TextView = dialog.findViewById(R.id.txt_title)
        val message: TextView = dialog.findViewById(R.id.txt_message)
        val btnYes:AppCompatButton = dialog.findViewById(R.id.btn_no)
        val btnNo:AppCompatButton = dialog.findViewById(R.id.btn_yes)
        val container: ConstraintLayout = dialog.findViewById(R.id.container_dialog)
        title.text = header
        message.text = msg
        val color: Int
        if (txtYes == "Retry"){
            color = Color.WHITE
            container.setBackgroundResource(R.drawable.wrong)
        }else{
            color = Color.BLACK
            container.setBackgroundResource(R.drawable.correct)
        }
        title.setTextColor(color)
        message.setTextColor(color)
        btnNo.text = txtNo
        btnYes.text = txtYes

        btnYes.setOnClickListener {
            if (txtYes == "Retry"){
                presenter.describeRetry()
                dialog.cancel()
            }else{
                if (presenter.btnNextClicked()){
                settings.levelUp()
                restoreScore()
                dialog.cancel()
                }else{
                    btnNo.visibility = View.GONE
                    btnYes.visibility = View.GONE
                    title.text = "Congratulations🎉"
                    message.text = "You have completely won this game!😊 Thanks for playing❤🥹🫂️"
                    dialog.setCancelable(false)
                    settings.level = 1
                    settings.saveToPref()
                   }
            }
        }

        btnNo.setOnClickListener {
            dialog.cancel()
            finish()
        }
        dialog.show()
    }

    override fun inCorrect() {
        for (i in 0 until answer.size) {
            answer[i].setBackgroundResource(R.drawable.bg_wrong)
        }
    }

    override fun correct() {
            for (i in 0 until answer.size){
                answer[i].setBackgroundResource(R.drawable.bg_variant)
            }
        }

    override fun vibratelong() {
        vibrator.vibrate(1000)
    }

    private fun attachClickListeners() {
        backButton.setOnClickListener { finish() }
        for (i in variants.indices){
            variants[i].setOnClickListener {
                correct()
                presenter.btnVariantClicked(variants[i].text.toString(), i)
                presenter.checkUserAnswer(getUserFullAnswer())
            }
        }
        for (i in answer.indices){
            answer[i].setOnClickListener {
                val tagPos = answer[i].tag as Int
//                Toast.makeText(this, "tag : $tagPos", Toast.LENGTH_SHORT).show()
                presenter.btnAnswerClicked(answer[i].text.toString(),i,tagPos)
            }
        }
        sos.setOnClickListener {
            if (getFirstEmptyPos() != -1) {
                if (settings.findOneLetter()){
                    answer[getFirstEmptyPos()].isEnabled = true
                    answer[getFirstEmptyPos()].isClickable = false
                    answer[getFirstEmptyPos()].text = presenter.getLetterInThisPos(getFirstEmptyPos())
                    presenter.checkUserAnswer(getUserFullAnswer())
                    Toast.makeText(this@GameActivity, "You known 1 letter for 5 KHAN coins 😁 ", Toast.LENGTH_SHORT).show()
                    restoreScore()
                }else{
                    Toast.makeText(this@GameActivity, "Not enough coins available 😒", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
    private fun getFirstEmptyPos():Int {
        for (i in answer.indices){
            if (answer[i].text.toString().isEmpty() && answer[i].visibility == View.VISIBLE){
                return i
            }
        }
        return -1
    }

    private fun getUserFullAnswer(): String {
        val sb = StringBuilder()

        for (i in answer.indices){
            sb.append(answer[i].text.toString())
        }
        return sb.toString()
    }

    override fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }

    override fun showValueToVariant(text: String, pos: Int, tag: Int) {
        variants[tag].text = text
        variants[tag].isEnabled = true
        answer[pos].text = ""
        answer[pos].isEnabled = false
    }

    override fun showValueToAnswer(text: String, pos: Int) {
        val index = getFirstEmptyPos()
        if (index != -1) {
            answer[index].text = text
            answer[index].tag = pos
            variants[pos].text = ""
            variants[pos].isEnabled = false
            answer[index].isEnabled = true
        }
    }

    override fun resizeAnswerButtons(length: Int) {
        for (i in 0 until length){
            answer[i].visibility = View.VISIBLE
        }

        for (i in length until answer.size){
            answer[i].visibility = View.GONE
        }
    }

    override fun describeQuestion(data: Question) {
        images[0].setImageResource(data.image1)
        images[1].setImageResource(data.image2)
        images[2].setImageResource(data.image3)
        images[3].setImageResource(data.image4)

        for (i in variants.indices){
            variants[i].text = data.variants[i].toString()
            variants[i].isEnabled = true
        }

        for (i in answer.indices){
            answer[i].text = ""
            answer[i].isEnabled = false
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}
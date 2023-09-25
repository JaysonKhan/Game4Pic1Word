package uz.gita.game4pic1word.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import uz.gita.game4pic1word.R
import uz.gita.game4pic1word.repository.Settings
import uz.gita.game4pic1word.ui.game.GameActivity
import uz.gita.game4pic1word.ui.info.InfoActivity

class HomeActivity : AppCompatActivity(){
    private lateinit var play: AppCompatButton
    private lateinit var info: AppCompatButton
    private lateinit var settings: Settings
    private lateinit var level: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        settings = Settings.getINstance()

        level = findViewById(R.id.txt_level)
        level.text = settings.level.toString()

        play = findViewById(R.id.btn_play_in_home)

        info = findViewById(R.id.btn_info_in_home)

        play.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        info.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        level.text = settings.level.toString()
    }
}
package uz.gita.game4pic1word.ui.info

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import uz.gita.game4pic1word.R

class InfoActivity : AppCompatActivity() {
   private lateinit var home: AppCompatButton
    private lateinit var telegram: View
    private lateinit var instagram:View
    private lateinit var pinterest:View
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        home = findViewById(R.id.buttonHomeInAbout)

        telegram = findViewById(R.id.telegram)
        instagram = findViewById(R.id.instagram)
        pinterest = findViewById(R.id.pinterest)
        home.setOnClickListener { view: View? -> finish() }

        telegram.setOnClickListener { view: View? ->
            val uri = Uri.parse("https://t.me/J_khan347")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        instagram.setOnClickListener { view: View? ->
            val uri = Uri.parse("https://www.instagram.com/gita.uzofficial/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        pinterest.setOnClickListener { view: View? ->
            val uri = Uri.parse("https://www.pinterest.com/betta347/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
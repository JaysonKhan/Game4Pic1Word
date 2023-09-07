package uz.gita.game4pic1word

import android.app.Application
import uz.gita.game4pic1word.repository.Settings

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        try {
            Settings.init(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

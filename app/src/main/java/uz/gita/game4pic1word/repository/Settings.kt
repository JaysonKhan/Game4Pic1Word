package uz.gita.game4pic1word.repository

import android.content.Context
import android.content.SharedPreferences

class Settings private constructor(private val context:Context) {

    companion object{
        private var INSTANCE: Settings? = null
        fun init(context: Context){
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Settings(context)
                    INSTANCE = instance
                }
            }
        }
        fun getINstance():Settings{
            val instance = INSTANCE
            require(instance!=null)
            return instance
        }
    }
private val sharedPreferences:SharedPreferences = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
var coins = sharedPreferences.getInt("COINS", 0)
var level = sharedPreferences.getInt("LEVEL", 1)

    fun getSharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    fun levelUp() {
        level++
        if (level == 999) {
            level = 0
        }
        coins += 10
        saveToPref()
    }

    fun saveToPref() {
        val editor = sharedPreferences.edit()
        editor.putInt("LEVEL", level)
        editor.putInt("COINS", coins)
        editor.apply()
    }

    fun findOneLetter():Boolean {
        if (coins>=5){
            coins -= 5
            saveToPref()
            return true
        }
        else{
            return false
        }

    }
}

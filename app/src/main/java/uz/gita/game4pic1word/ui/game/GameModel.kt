package uz.gita.game4pic1word.ui.game

import uz.gita.game4pic1word.data.Question
import uz.gita.game4pic1word.repository.Repository

class GameModel:GameContract.Model {
    private val repository = Repository.getInstance()
    private var list = ArrayList<Question>()
    private var currentPos = 0
    init {
        loadListOfQuestions()
    }

    private fun loadListOfQuestions(){
        list.addAll(repository.getListOfData())
    }

    override fun getNextQuestion(): Question {
        return list[currentPos++]
    }

    override fun getAnswer(): String {
        return list[currentPos-1].answer
    }

    override fun hasQuestion(): Boolean {
        return currentPos < list.size
    }

    override fun getCurrentQuestion(): Question {
        return list[currentPos-1]
    }
}
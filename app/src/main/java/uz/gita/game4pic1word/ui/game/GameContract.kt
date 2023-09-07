package uz.gita.game4pic1word.ui.game

import uz.gita.game4pic1word.data.Question

interface GameContract {
    interface Model {
        fun getNextQuestion(): Question
        fun getAnswer():String
        fun hasQuestion():Boolean
        fun getCurrentQuestion(): Question
    }

    interface View {
        fun describeQuestion(data:Question)
        fun resizeAnswerButtons(length:Int)
        fun showValueToAnswer(text: String,pos: Int)
        fun showValueToVariant(text: String,pos: Int,tag: Int)
        fun showToast(toast:String)
        fun showDialog(header:String, msg: String, txtYes:String, txtNo:String)
        fun inCorrect()
        fun correct()
        fun vibratelong()
    }

    interface Presenter{
        fun btnVariantClicked(text:String,pos:Int)
        fun btnAnswerClicked(text: String,pos: Int,tag:Int)
        fun checkUserAnswer(answer: String)
        fun btnNextClicked():Boolean
        fun describeRetry()
        fun getLetterInThisPos(firstEmptyPos: Int): String
    }
}
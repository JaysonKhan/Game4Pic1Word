package uz.gita.game4pic1word.ui.game

class GamePresenter(private var view: GameContract.View) : GameContract.Presenter {
    private val model: GameContract.Model = GameModel()

    init {
        val data = model.getNextQuestion()
        view.describeQuestion(data)
        view.resizeAnswerButtons(data.answer.length)
    }

    override fun btnVariantClicked(text: String, pos: Int) {
        view.showValueToAnswer(text, pos)
    }

    override fun btnAnswerClicked(text: String, pos: Int, tag: Int) {
        view.showValueToVariant(text, pos, tag)
    }

    override fun checkUserAnswer(answer: String) {
        if (model.getAnswer().equals(answer, false)) {
            view.showDialog("Well Done😁!!!", "Your answer: $answer is Correct✅", "Next", "Home")
        } else if (model.getAnswer().length == answer.length) {
            view.vibratelong()
            view.inCorrect()
        }
    }

    override fun btnNextClicked(): Boolean {
        if (model.hasQuestion()) {
            val data = model.getNextQuestion()
            view.resizeAnswerButtons(data.answer.length)
            view.describeQuestion(data)
            return true
        }
        return false
    }

    override fun describeRetry() {
        view.describeQuestion(model.getCurrentQuestion())
    }

    override fun getLetterInThisPos(firstEmptyPos: Int): String {
        return model.getAnswer()[firstEmptyPos].toString()
    }
}
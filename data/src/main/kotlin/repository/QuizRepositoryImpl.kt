package repository

import entity.QuizWord
import entity.QuizWordStats
import http.LangTestApi
import state.DataState

class QuizRepositoryImpl(
    private val langTestApi: LangTestApi
) : QuizRepository {

    override suspend fun getNextQuizWord(chatId: Long): DataState<QuizWord> {
        return try {
            val response = langTestApi.getNextQuizWord(chatId).execute()
            if (response.body() == null) {
                DataState.Empty
            } else {
                DataState.Success(response.body()!!)
            }
        } catch (e: Exception) {
            DataState.Failure(e)
        }
    }

    override suspend fun createQuizWords(chatId: Long): Boolean {
        val callback = langTestApi.createQuizWords(chatId)
        val response = callback.execute()
        return response.code() == 200
    }

    override suspend fun setCorrectAnswerForQuizWord(chatId: Long, wordId: Long): DataState<QuizWordStats> {
        return try {
            val response = langTestApi.setCorrectAnswerForQuizWord(chatId, wordId).execute()
            if (response.body() == null) {
                DataState.Empty
            } else {
                DataState.Success(response.body()!!)
            }
        } catch (e: Exception) {
            DataState.Failure(e)
        }
    }

    override suspend fun setIncorrectAnswerForQuizWord(chatId: Long, wordId: Long): DataState<QuizWord> {
        return try {
            val response = langTestApi.setIncorrectAnswerForQuizWord(chatId, wordId).execute()
            if (response.body() == null) {
                DataState.Empty
            } else {
                DataState.Success(response.body()!!)
            }
        } catch (e: Exception) {
            DataState.Failure(e)
        }
    }

    override suspend fun resetQuiz(chatId: Long): Boolean {
        val response = langTestApi.resetQuiz(chatId).execute()
        return response.code() == 200
    }

    override suspend fun resetQuizWordNumber(chatId: Long): Boolean {
        val callback = langTestApi.resetQuizWordNumber(chatId)
        val response = callback.execute()
        return response.code() == 200
    }
}

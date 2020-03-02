package com.oostaoo.org.oostaoocodingadventure.ui.listQuestions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.oostaoo.org.oostaoocodingadventure.database.AppDatabase
import com.oostaoo.org.oostaoocodingadventure.database.DataRepository
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import kotlinx.coroutines.launch

class ListQuestionsViewModel(technologiesName: ArrayList<String>, application: Application) : AndroidViewModel(application) {

    private var mTechnologiesName = technologiesName
    private val repository: DataRepository = DataRepository().getInstance(AppDatabase.getDatabase(application))!!

    fun getSelectedTechnologies() : ArrayList<String> {
        return  mTechnologiesName
    }

    fun getTechnologies() : LiveData<List<Technology>> {

        /*val listQuestions = ArrayList<Question>()
        for(technologyName in mTechnologiesName) {
            val questions = repository.getTechnology(technologyName)*/
        return repository.getTechnologies()
    }

    fun insertQuestion(question: Question) = viewModelScope.launch {
        repository.insertQuestion(question)
    }
}
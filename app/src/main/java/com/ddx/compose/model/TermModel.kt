package com.ddx.compose.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TermModel : ViewModel() {
    private val TAG: String = "TermModel"

    // 数据库，初识生产提供 terms
    private val repository: Repository = ServiceLocator.provideRepository()

    // term 列表，可变列表≠可变状态列表，前者只是列表元素可变，但后者元素和状态均可变，用于自动更新
    private val termList = mutableStateListOf<Term>()

    // 对应 termList 的 liveData
    private val termListLiveData: MutableLiveData<List<Term>> = MutableLiveData()

    // 对应选中term 的 liveData
    private val termLiveData: MutableLiveData<Term> = MutableLiveData()

    init {
        // 初始化数据
        viewModelScope.launch {
            termList.addAll(
                //处理耗时操作
                repository.getTermList()
            )
            Log.d(
                TAG, "TermModel init termList " +
                        "size = ${termList.size}"
            )
            // 更新数据
            termListLiveData.postValue(termList)
        }
    }

    fun getTermLiveData(): MutableLiveData<List<Term>> {
        return termListLiveData
    }

    fun initSelectedTerm(position: Int) {
        Log.d(TAG, "TermModel initSelectedTerm")
        val selectedDog = termList[position]
        termLiveData.postValue(selectedDog)
    }

    fun updateTerm(term: Term, position: Int) {
        Log.d(TAG, "TermModel updateTerm")
        termList[position] = term
        // 更新数据
        termListLiveData.postValue(termList)
    }

    fun deleteTerm(position: Int) {
        Log.d(TAG, "TermModel deleteTerm$position")
        if (position >= termList.count()) {
            throw Exception("position illegal!")
        }
        termList.removeAt(position)
        // 删除数据
        termListLiveData.postValue(termList)
    }

    fun deleteTermsWithIndex(list: List<Int>) {
        Log.d(TAG, "TermModel deleteTerm${list.toList()}")
        val ll = termList.filterIndexed { index, _ ->
            !list.contains(index)
        }
        termList.clear()
        termList.addAll(ll)
        // 删除数据
        termListLiveData.postValue(termList)
    }

    fun deleteLastTerm() {
        Log.d(TAG, "TermModel deleteLastTerm")
        termList.removeLast()
        Log.d(TAG, "size:" + termList.count())
        // 删除数据
        termListLiveData.postValue(termList)
    }
}
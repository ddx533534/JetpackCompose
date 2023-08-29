package com.ddx.compose.model

/**
 * 增加 Repository 层，新增类 Repository，repository 从 DataHelper 获取数据，采用协程的方式
 */
class Repository(private var dataHelper: DataHelper) {
    suspend fun getTermList() : List<Term> {
        return dataHelper.getTermList()
    }
}
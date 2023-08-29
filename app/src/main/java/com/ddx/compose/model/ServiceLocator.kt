package com.ddx.compose.model

/**
 * 定义一个 ServiceLocator，使用 object 修饰，获得一个单例
 */
object ServiceLocator {
    /**
     * Provide the Repository instance that ViewModel should depend on.
     */
    fun provideRepository() = Repository(provideDataHelper())

    /**
     * Provide the DataHelper instance that Repository should depend on.
     */
    private fun provideDataHelper() = DataHelper()
}
package com.ddx.compose.database

import androidx.room.*

//创建数据实体类
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,
    var title: String = "",
    var subTitle: String = "2021/1/1",
    var content: DetailType = DetailType(),
    var tag: String = "2021/1/1"

)

//复杂数据
data class DetailType(
    val name: String = "无",
    val triad: Boolean = true
)

//由于数据库无法添加除了规定类型外的所有类型,要写一个转化器来指导数据库进行复杂数据的转化
class DetailTypeConverters {
    @TypeConverter
    fun stringToObject(value: String?): DetailType? {
        val it = object : TypeToken<DetailType>() {}.type
        return Gson().fromJson(value, it)
    }

    @TypeConverter
    fun objectToString(value: DetailType?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }
}

@Dao
interface DetailDao {
    @Query("SELECT * FROM details")
    fun queryAll(): Flow<List<Detail>>

    @Insert
    fun insert(detail: Detail?)

    @Query("DELETE FROM details")
    fun deleteAll()

    @Update
    fun updata(detail: Detail)

    @Delete
    fun delete(detail: Detail)


    //[Detail::class]为你创建的数据实体类
    //@return Flow<List<Detail>> 配合 jetpack compose 可以在数据变化时自动更新ui
    @RawQuery(observedEntities = [Detail::class])
    fun execsql(query: SupportSQLiteQuery) : Flow<List<Detail>>
}

//@Database(entities = [Detail::class], version = 1)entities为数据类,数据结构变化时version要增加1
//@TypeConverters(DetailTypeConverters::class,...)指定复杂数据格式转换器,多个转换器之间用逗号隔开
@Database(entities = [Article::class], version = 1)
@TypeConverters(DetailTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDetailDao(): DetailDao
}


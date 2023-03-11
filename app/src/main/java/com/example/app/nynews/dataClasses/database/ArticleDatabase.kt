package com.example.app.nynews.dataClasses.database

//@Database(
//    entities = [ResponseArticlesList.Response.NewsList::class],
//    version = 1
//)
//abstract class ArticleDatabase : RoomDatabase() {
//
//    abstract fun getArticleDao(): ArticleDao
//
//    companion object {
//        @Volatile
//        private var instance: ArticleDatabase? = null
//        private val LOCK = Any()
//
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                ArticleDatabase::class.java, "Article_bd.db"
//            ).build()
//    }
//}
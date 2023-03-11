package com.example.app.nynews.diModule
//
//import android.content.Context
//import androidx.room.Room
//import com.example.app.nynews.dataClasses.database.ArticleDatabase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RoomDatabaseModule {
//
//    @Provides
//    fun provideDatabase(@ApplicationContext context: Context): ArticleDatabase {
//        return Room.databaseBuilder(context, ArticleDatabase::class.java, "app-database")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//}

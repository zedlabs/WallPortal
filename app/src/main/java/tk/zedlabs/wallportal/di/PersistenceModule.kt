package tk.zedlabs.wallportal.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import tk.zedlabs.wallportal.repository.BookmarkDao
import tk.zedlabs.wallportal.repository.BookmarkDatabase
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): BookmarkDatabase {
        return Room
            .databaseBuilder(application, BookmarkDatabase::class.java, "bookmark-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(bookmarkDatabase: BookmarkDatabase) : BookmarkDao{
        return bookmarkDatabase.bookmarkDao()
    }
}
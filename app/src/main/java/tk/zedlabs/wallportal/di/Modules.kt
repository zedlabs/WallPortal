package tk.zedlabs.wallportal.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tk.zedlabs.wallportal.repository.BookmarkDatabase
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel
import tk.zedlabs.wallportal.viewmodel.ImageDetailViewModel
import tk.zedlabs.wallportal.viewmodel.PostViewModel

val databaseModule = module {
    single {
        val db = Room.databaseBuilder(
            androidApplication().applicationContext,
            BookmarkDatabase::class.java,
            "bookmark-database"
        ).build()

        db.bookmarkDao()
    }
}

val viewModelModule = module {
    viewModel {
        BookmarkViewModel(get())
    }
    viewModel {
        ImageDetailViewModel(androidApplication(), get())
    }
    viewModel {
        PostViewModel()
    }
}
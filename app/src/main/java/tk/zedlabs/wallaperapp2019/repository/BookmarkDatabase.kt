package tk.zedlabs.wallaperapp2019.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.zedlabs.wallaperapp2019.repository.BookmarkDao
import tk.zedlabs.wallaperapp2019.repository.BookmarkImage

@Database(entities = arrayOf(BookmarkImage::class),version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao
}
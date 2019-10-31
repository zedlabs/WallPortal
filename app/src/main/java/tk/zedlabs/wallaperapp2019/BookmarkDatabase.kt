package tk.zedlabs.wallaperapp2019

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(BookmarkImage::class),version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao
}
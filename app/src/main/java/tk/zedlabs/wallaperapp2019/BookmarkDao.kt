package tk.zedlabs.wallaperapp2019

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM BookmarkImage")
    fun getAll(): List<BookmarkImage>

    @Query("SELECT imageUrlFull FROM BookmarkImage")
    fun getListFull(): List<String>

    @Query("SELECT imageUrlRegular FROM BookmarkImage")
    fun getListRegular(): List<String>

    @Insert
    fun insertAll(vararg bookmarkImage: BookmarkImage)

    @Insert
    fun insert(bookmarkImage: BookmarkImage)

    @Delete
    fun delete(bookmarkImage: BookmarkImage)
}
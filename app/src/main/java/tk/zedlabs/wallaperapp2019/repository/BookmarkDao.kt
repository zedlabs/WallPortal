package tk.zedlabs.wallaperapp2019.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import tk.zedlabs.wallaperapp2019.repository.BookmarkImage

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM BookmarkImage")
    suspend fun getAll(): List<BookmarkImage>

    @Query("SELECT imageUrlFull FROM BookmarkImage")
    suspend fun getListFull(): List<String>

    @Query("SELECT imageUrlRegular FROM BookmarkImage")
    suspend fun getListRegular(): List<String>

    @Query("SELECT imageName FROM BookmarkImage")
    suspend fun getId(): List<String>

    @Insert
    suspend fun insertAll(vararg bookmarkImage: BookmarkImage)

    @Insert
    suspend fun insert(bookmarkImage: BookmarkImage)

    @Delete
    suspend fun delete(bookmarkImage: BookmarkImage)
}
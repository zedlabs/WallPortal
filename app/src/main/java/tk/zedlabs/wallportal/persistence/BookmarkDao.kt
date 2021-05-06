package tk.zedlabs.wallportal.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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

    @Query("SELECT * FROM BookmarkImage WHERE imageName==:name")
    suspend fun getItemByName(name: String): List<BookmarkImage>

    @Insert
    suspend fun insertAll(vararg bookmarkImage: BookmarkImage)

    @Insert
    suspend fun insert(bookmarkImage: BookmarkImage)

    @Delete
    suspend fun delete(bookmarkImage: BookmarkImage)
}
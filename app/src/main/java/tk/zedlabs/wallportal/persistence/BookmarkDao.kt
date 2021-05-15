package tk.zedlabs.wallportal.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM BookmarkImage")
    fun getAll(): Flow<List<BookmarkImage>>

    @Query("SELECT * FROM BookmarkImage WHERE imageName==:name")
    suspend fun getItemByName(name: String): List<BookmarkImage>

    @Query("DELETE FROM BookmarkImage WHERE imageName==:id")
    suspend fun deleteBookmark(id: String)

    @Insert
    suspend fun insertAll(vararg bookmarkImage: BookmarkImage)

    @Insert
    suspend fun insert(bookmarkImage: BookmarkImage)

    @Delete
    suspend fun delete(bookmarkImage: BookmarkImage)
}
package tk.zedlabs.wallportal.persistence

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class BookmarkDaoTest {

    private lateinit var bookmarkDatabase: BookmarkDatabase
    private lateinit var bookmarkDao: BookmarkDao

    @Before
    fun setup(){
        bookmarkDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookmarkDatabase::class.java
        ).allowMainThreadQueries().build()

        bookmarkDao = bookmarkDatabase.bookmarkDao()
    }

    @After
    fun teardown(){
        bookmarkDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertIntoBookmarkList() = runBlocking{
        val bookmarkImage = BookmarkImage("testImage", "https://i.redd.it/bg54rhtg5ot51.jpg", "https://i.redd.it/bg54rhtg5ot51.jpg")

        bookmarkDao.insert(bookmarkImage)

        val imageList = bookmarkDao.getAll()
        assertThat(imageList).contains(bookmarkImage)
    }

    @Test
    @Throws(Exception::class)
    fun deleteFromBookmarkList() = runBlocking {

        val bookmarkImage = BookmarkImage("testImage2", "https://i.redd.it/bg54rhtg5ot51.jpg", "https://i.redd.it/bg54rhtg5ot51.jpg")
        bookmarkDao.insert(bookmarkImage)
        bookmarkDao.delete(bookmarkImage)
        val imageList = bookmarkDao.getAll()
        assertThat(imageList).doesNotContain(bookmarkImage)

    }
}
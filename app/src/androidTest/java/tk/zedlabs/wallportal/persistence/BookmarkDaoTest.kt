package tk.zedlabs.wallportal.persistence

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class BookmarkDaoTest {

    @Inject
    @Named("test-db")
    lateinit var bookmarkDatabase: BookmarkDatabase

    private lateinit var bookmarkDao: BookmarkDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){

        hiltRule.inject()
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
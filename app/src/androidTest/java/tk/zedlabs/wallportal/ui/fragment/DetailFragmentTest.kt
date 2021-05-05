package tk.zedlabs.wallportal.ui.fragment

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.launchFragmentInHiltContainer

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DetailFragmentTest {
//this test does not work if the navigation component has navArgs
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickOriginalResolutionButton_navigateToOriginalResolutionFragment(){
        //has all the objects of the navController w/o any implementation(i.e mock)
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        //using espresso to click button
        onView(withId(R.id.original_resolution_button)).perform(click())

        //verifying correct navigation
        verify(navController).navigate(
            DetailFragmentDirections.actionDetailsToOriginalRes("")
        )

    }

}
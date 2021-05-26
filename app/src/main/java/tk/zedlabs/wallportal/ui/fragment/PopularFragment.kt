package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.ui.util.LoadingBox
import tk.zedlabs.wallportal.ui.util.TopBar
import tk.zedlabs.wallportal.ui.wallpaperLists.WallpaperListItem
import tk.zedlabs.wallportal.util.Constants

//make popular for selected period of time similar to reddit community posts
@ExperimentalFoundationApi
@AndroidEntryPoint
class PopularFragment : Fragment() {
    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = { TopBar() },
                    backgroundColor = colorResource(R.color.listBackground)
                ) {
                    WallpaperList()
                }
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun WallpaperList() {
        val newWallpapers = postViewModel.popList.value
        val loading = postViewModel.loadingPop.value
        val page = postViewModel.pagePopular.value
        if (loading && page == 1) {
            LoadingBox()
        }
        if (newWallpapers.isEmpty() && !loading) {
            Text(text = "No Data", color = Color.Red)
        }
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
            itemsIndexed(
                items = newWallpapers
            ) { index, item ->
                postViewModel.onChangePopularScrollPosition(index)
                if ((index + 1) >= (page * Constants.PAGE_SIZE) && !loading) {
                    postViewModel.nextPagePop()
                }
                WallpaperListItem(item) {
                    findNavController().navigate(
                        PopularFragmentDirections.popularToDetails(item.id!!)
                    )
                }
            }
        }

    }
}

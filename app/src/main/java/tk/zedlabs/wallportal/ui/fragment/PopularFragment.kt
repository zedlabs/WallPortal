package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.zedlabs.wallportal.ui.wallpaperLists.WallpaperListItem
import tk.zedlabs.wallportal.util.Constants
import tk.zedlabs.wallportal.viewmodel.PostViewModel

//make popular for selected period of time similar to reddit community posts
@AndroidEntryPoint
class PopularFragment : Fragment() {
    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WallpaperList()
            }
        }
    }

    @Composable
    fun WallpaperList() {
        val newWallpapers = postViewModel.popList.value
        val loading = postViewModel.loading.value
        val page = postViewModel.pagePopular.value
        LazyColumn {
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

//        viewAdapter = MainAdapter{
//            findNavController().navigate(
//                PopularFragmentDirections.actionPopularToDetails(it, "PopularActivity")
//            )
//        }

}

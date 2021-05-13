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
import tk.zedlabs.wallportal.util.Constants.PAGE_SIZE
import tk.zedlabs.wallportal.viewmodel.PostViewModel

// --refactor to search element and provide chips and search bar for selection
@AndroidEntryPoint
class NewFragment : Fragment() {

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
        val newWallpapers = postViewModel.newList.value
        val loading = postViewModel.loading.value
        val page = postViewModel.pageNew.value
        LazyColumn {
            itemsIndexed(
                items = newWallpapers
            ) { index, item ->
                postViewModel.onChangeNewScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    postViewModel.nextPageNew()
                }
                WallpaperListItem(item) {
                    findNavController().navigate(
                        NewFragmentDirections.newToDetails(item.id!!)
                    )
                }
            }
        }
    }

}

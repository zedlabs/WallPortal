package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.ui.util.LoadingBox
import tk.zedlabs.wallportal.ui.util.TopBar
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
                Scaffold(
                    topBar = { TopBar() },
                    backgroundColor = colorResource(R.color.listBackground)
                ) {
                    WallpaperList()
                }
            }
        }
    }

    @Composable
    fun WallpaperList() {
        val newWallpapers = postViewModel.newList.value
        val loading = postViewModel.loadingNew.value
        val page = postViewModel.pageNew.value
        if (loading && page == 1) {
            LoadingBox()
        }
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

package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.ui.util.LoadingBox
import tk.zedlabs.wallportal.ui.util.TopBarNew
import tk.zedlabs.wallportal.ui.wallpaperLists.WallpaperListItem
import tk.zedlabs.wallportal.util.Constants.PAGE_SIZE

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class NewFragment : Fragment() {

    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val searchQuery = remember { mutableStateOf("") }
                Scaffold(
                    topBar = {
                        TopBarNew(
                            searchQuery.value,
                            {
                                postViewModel.updateSearch(it)
                                searchQuery.value = it
                            },
                            { postViewModel.loadInitData() },
                            postViewModel.searchProgress.value
                        )
                    },
                    backgroundColor = colorResource(R.color.listBackground)
                ) {
                    WallpaperListSetup()
                }
            }
        }
    }

    @Composable
    fun WallpaperListSetup() {
        val newWallpapers = postViewModel.newList.value
        val loading = postViewModel.loadingNew.value
        val page = postViewModel.pageNew.value

        if (loading && page == 1) {
            LoadingBox()
        }
        if (newWallpapers.isEmpty() && !loading) {
            Text(
                text = "No Images!",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {

            itemsIndexed(
                items = newWallpapers
            ) { index, item ->
                Log.e("LVG", "1.WallpaperListSetup : $index")
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

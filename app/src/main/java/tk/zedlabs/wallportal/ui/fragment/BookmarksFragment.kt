package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.databinding.FragmentSavedBinding
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.ui.wallpaperLists.BookmarkListItem
import tk.zedlabs.wallportal.ui.wallpaperLists.WallpaperListItem
import tk.zedlabs.wallportal.util.BaseFragment
import tk.zedlabs.wallportal.util.Constants
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel

@AndroidEntryPoint
class BookmarksFragment : BaseFragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val bookmarkImages by bookmarkViewModel.bookmarkList.observeAsState()
                LazyColumn {
                    itemsIndexed(
                        items = bookmarkImages.orEmpty()
                    ) { _, item ->
                        BookmarkListItem(item) {
                            findNavController().navigate(
                                BookmarksFragmentDirections.bookmarkToDet(item.imageName)
                            )
                        }
                    }
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        bookmarkViewModel.getBookmarks()
//    }
}

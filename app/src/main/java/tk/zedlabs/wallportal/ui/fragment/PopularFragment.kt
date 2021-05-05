package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.databinding.FragmentPopularBinding
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.PostViewModel

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private lateinit var viewAdapter: MainAdapter
    private val postViewModel: PostViewModel by viewModels()
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewConnectivityPop.apply {
            when (context?.isConnectedToNetwork()) {
                true -> if (this.isVisible) this.visibility = View.GONE
                false -> this.visibility = View.VISIBLE
            }
        }

        viewAdapter = MainAdapter{
            findNavController().navigate(
                PopularFragmentDirections.actionPopularToDetails(it, "PopularActivity")
            )
        }

        lifecycleScope.launch {
            postViewModel.postList.collectLatest {
                viewAdapter.submitData(it)
            }
        }

        binding.recyclerViewPopular.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = viewAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

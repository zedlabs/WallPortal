package tk.zedlabs.wallaperapp2019.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.zedlabs.wallaperapp2019.MainAdapter

import tk.zedlabs.wallaperapp2019.R

class BookmarksFragment : Fragment(),MainAdapter.OnImageListener {

    override fun onImageClick(position: Int) {
        val i = Intent(activity ,ImageDetails::class.java)
        startActivity(i)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }
}

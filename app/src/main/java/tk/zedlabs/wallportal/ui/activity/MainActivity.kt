package tk.zedlabs.wallportal.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.util.makeFadeTransition

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )
        }

        toolbar.title = getString(R.string.app_name)
        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailActivity, R.id.originalResolutionFragment -> {
                    bottomNavigation.makeFadeTransition(300)
                    bottomNavigation.visibility = View.GONE
                }
                else -> bottomNavigation.visibility = View.VISIBLE
            }

        }

        //todo add settings bottom navigation tab
    }
}

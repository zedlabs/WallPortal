package tk.zedlabs.wallportal.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.ColorHolder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.util.ConnectivityHelper

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        }


        toolbar.title = getString(R.string.app_name)
        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavigation.setupWithNavController(navController)

        val item1 = SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_popular).apply {
            this.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)
            this.selectedTextColor = ColorHolder.fromColorRes(R.color.md_white_1000)
        }
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.curated).apply {
            this.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)
            this.selectedTextColor = ColorHolder.fromColorRes(R.color.md_white_1000)
        }
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_bookmarks).apply {
            this.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)
            this.selectedTextColor = ColorHolder.fromColorRes(R.color.md_white_1000)
        }
        val item4 = SecondaryDrawerItem().withIdentifier(4).withName(R.string.About_string).apply {
            this.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)
            this.selectedTextColor = ColorHolder.fromColorRes(R.color.md_white_1000)
        }

        val headerResult = AccountHeaderBuilder().withActivity(this)
            .withHeaderBackground(R.drawable.header_art)
            .build()

        DrawerBuilder().withActivity(this)
            .withToolbar(toolbar)
            .addDrawerItems(item1, DividerDrawerItem(),item2, DividerDrawerItem(), item3,DividerDrawerItem(),item4)
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(view: View?,position: Int,drawerItem: IDrawerItem<*>): Boolean {
                    when (drawerItem.identifier.toInt()) {
                        1 -> navController.navigate(R.id.popular_bottom)
                        2 -> navController.navigate(R.id.new_bottom)
                        3 -> navController.navigate(R.id.bookmarks_bottom)
                        4 -> { }//TODO: implement about page
                    }
                    return false
                }
            })
            .withAccountHeader(headerResult)
            .withSliderBackgroundColorRes(R.color.colorPrimary)
            .withSelectedItem(-1)
            .build()
    }
}

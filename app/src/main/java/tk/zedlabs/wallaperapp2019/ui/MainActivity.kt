package tk.zedlabs.wallaperapp2019.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import tk.zedlabs.wallaperapp2019.*

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


        //change to app name and remove this
        toolbar.title = "WALLPORTAL"
        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavigation.setupWithNavController(navController)

        val item1 = SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_popular)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_new)
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_bookmarks)
        val item4 = SecondaryDrawerItem().withIdentifier(4).withName(getString(R.string.About_string))
        item1.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)
        item2.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)
        item3.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)
        item4.textColor = ColorHolder.fromColorRes(R.color.md_white_1000)

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
                        4 -> {startActivity(Intent(this@MainActivity, Main2Activity::class.java))}
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

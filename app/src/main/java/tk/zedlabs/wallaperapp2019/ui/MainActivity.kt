package tk.zedlabs.wallaperapp2019.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
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
        //change to app name and remove this
        toolbar.title = "WALLPORTAL"
        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavigation.setupWithNavController(navController)

        val item1 = SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_popular)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_new)
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_downloads)

        DrawerBuilder().withActivity(this)
            .withToolbar(toolbar)
            .addDrawerItems(
                item1, DividerDrawerItem(),
                item2, DividerDrawerItem(), item3
            )
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when (drawerItem.identifier.toInt()) {
                        1 -> navController.navigate(R.id.popular_bottom)
                        2 -> navController.navigate(R.id.new_bottom)
                        3 -> navController.navigate(R.id.saved_bottom)
                    }
                    return false
                }
            })
            .withSelectedItem(-1)
            .build()
    }
}

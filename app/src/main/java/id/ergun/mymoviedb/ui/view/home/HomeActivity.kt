package id.ergun.mymoviedb.ui.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.HomeActivityBinding


/**
 * Created by alfacart on 21/10/20.
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        fun newIntent(
            context: Context
        ): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbarView.toolbar)
        supportActionBar?.run {
            elevation = 0F
            setDisplayShowTitleEnabled(false)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.movieFragment, R.id.tvShowFragment, R.id.favoriteFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        setupWithNavController(binding.bnvMain, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.movieFragment,
                R.id.tvShowFragment,
                R.id.favoriteFragment-> {
                    showBottomNav()
                }
                else -> {
                    hideBottomNav()
                }
            }
        }
    }

    private fun showBottomNav() {
        binding.bnvMain.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bnvMain.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.activity_main_nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.activity_main_nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
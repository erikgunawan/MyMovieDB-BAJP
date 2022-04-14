package id.ergun.mymoviedb.ui.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
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
            title = getString(R.string.app_name)
            elevation = 0F
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupWithNavController(binding.bnvMain, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
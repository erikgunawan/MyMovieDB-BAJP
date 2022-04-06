package id.ergun.mymoviedb.ui.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
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

//        loadFragment(MovieFragment())

        val navController = findNavController(this, R.id.activity_main_nav_host_fragment)
//        val bottomNavigationView =
//            findViewById<BottomNavigationView>(R.id.bnv_main)
        setupWithNavController(binding.bnvMain, navController)
//        binding.bnvMain.setOnNavigationItemSelectedListener(this)
    }


    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_view, fragment)
                .commit()
            return true
        }
        return false
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        var fragment: Fragment? = null
//
//        if  (binding.bnvMain.selectedItemId == item.itemId) return false
//
//        when (item.itemId) {
//            R.id.action_movies -> fragment = MovieFragment.newInstance()
//            R.id.action_tv_shows -> fragment = TvShowFragment.newInstance()
//            R.id.action_favorites -> fragment = FavoriteFragment.newInstance()
//        }
//        return loadFragment(fragment)
//    }
}
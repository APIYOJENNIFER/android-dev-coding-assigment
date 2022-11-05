package com.ensibuuko.android_dev_coding_assigment.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.ActivityMainBinding
import com.ensibuuko.android_dev_coding_assigment.databinding.NavHeaderBinding
import com.ensibuuko.android_dev_coding_assigment.ui.profile.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderBinding: NavHeaderBinding
    private lateinit var navController: NavController
    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val header = navView.getHeaderView(0)
            navHeaderBinding = NavHeaderBinding.bind(header)
            val navHostFrag =
                supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            navController = navHostFrag.findNavController()

            val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

            toolbar.setupWithNavController(navController, appBarConfiguration)

            navView.setupWithNavController(navController)

            viewModel.currentUser.observe(this@MainActivity) {
                navHeaderBinding.tvName.text = it.name
                navHeaderBinding.tvUsername.text = it.username
                navHeaderBinding.tvEmail.text = it.email
                navHeaderBinding.tvUserPhone.text = it.phone
                navHeaderBinding.tvWebsite.text = it.website
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_frag)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            super.onBackPressed()
        }
    }
}
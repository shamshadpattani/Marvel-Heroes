package muhammedshamshadp.hope.marvelapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import muhammedshamshadp.hope.marvelapp.databinding.ActivityMainBinding
import muhammedshamshadp.hope.marvelworld.utils.NetworkUtils

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)

        handleNetworkChanges()

    }
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            if (!isConnected) {
                binding.netView.visibility = View.VISIBLE
                binding.netStatus.text = getString(R.string.no_net)
                binding.netView.setBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorDangerDark
                    )
                )
            } else {
                binding.netStatus.text = getString(R.string.back_net)
                binding.netView.apply {
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.colorSuccessDark
                        )
                    )
                    animate()
                        .alpha(1f)
                        .setStartDelay(1000.toLong())
                        .setDuration(500)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.netView.visibility = View.GONE
                            }
                        })
                }

            }
        })
    }
}
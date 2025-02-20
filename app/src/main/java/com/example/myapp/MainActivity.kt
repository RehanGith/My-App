package com.example.myapp

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.myapp.broadcast.AirPlaneBroadcastReciver
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: ImageViewModel by viewModels()
    private val airPlaneBroadcastReciver=  AirPlaneBroadcastReciver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("uri", "uri")
        handleIntent(intent)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerReceiver(
            airPlaneBroadcastReciver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        binding.click.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("john.c.calhoun@examplepetstore.com"))
                putExtra(Intent.EXTRA_SUBJECT, "This is my First Email")
                putExtra(Intent.EXTRA_TEXT, "This is my First Email. to john")
                startActivity(this)
            }
            if(intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        viewModel.uri.observe(this) {
            Glide.with(this).load(it).into(binding.image)
        }

    }



    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        Log.d("Intent", intent.toString())
        when (intent.type) {
            "image/*" -> {
                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
                } else {
                    intent.getParcelableExtra(Intent.EXTRA_STREAM)
                }
                uri?.let {
                    viewModel.setUri(it)
                    Log.d("Image URI", it.toString())
                }
            }
            "text/plain" -> {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT)
                text?.let {
                    Log.d("Shared Text", it)
                }
            }
            else -> {
                Log.d("Unhandled", "Unhandled intent type: ${intent.type}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlaneBroadcastReciver)
    }

}
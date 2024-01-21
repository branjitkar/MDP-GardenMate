package com.miu.gardenmate

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.miu.gardenmate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

fun Context?.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context?.snackBar(view: View, message: String) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
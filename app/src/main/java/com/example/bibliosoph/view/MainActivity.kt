package com.example.bibliosoph.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bibliosoph.R
import com.example.bibliosoph.databinding.ActivityMainBinding
import com.example.bibliosoph.model.repository.GoogleBooksRepository
import com.example.bibliosoph.viewmodel.GoogleBooksViewModel
import com.example.bibliosoph.viewmodel.GoogleBooksViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: GoogleBooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val booksRepository = GoogleBooksRepository()
        val viewModelProviderFactory = GoogleBooksViewModelProviderFactory(booksRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[GoogleBooksViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.booksNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
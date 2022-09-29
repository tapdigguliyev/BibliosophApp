package com.example.bibliosoph.view

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
        val viewModelProviderFactory = GoogleBooksViewModelProviderFactory(application, booksRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[GoogleBooksViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.booksNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view: View? = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
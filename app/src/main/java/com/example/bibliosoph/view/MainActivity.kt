package com.example.bibliosoph.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bibliosoph.R
import com.example.bibliosoph.databinding.ActivityMainBinding
import com.example.bibliosoph.view.books.BooksFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var booksFragment: BooksFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            displayFragment(menuItem.itemId)
            true
        }
    }

    private fun displayFragment(itemId: Int) {
        when (itemId) {
            R.id.home -> {
                Toast.makeText(this, "Hey, implement Google Books Api!", Toast.LENGTH_SHORT).show()
            }

            R.id.books -> {
                if (booksFragment == null) {
                    booksFragment = BooksFragment()
                }
                loadFragment(booksFragment!!)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, null)
            .commit()
    }
}
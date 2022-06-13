package com.example.bibliosoph.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.app.gone
import com.example.bibliosoph.app.visible
import com.example.bibliosoph.databinding.ActivityBooksBinding
import kotlinx.coroutines.launch

class BooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooksBinding

    private val repository by lazy { BibliosophApplication.repository }
    private val adapter by lazy { BooksAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        binding.booksRecyclerView.adapter = adapter
        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            if(getBooks().isEmpty()) {
                showNoBooksView()
            } else {
                showBooksView()
                loadBooks()
            }
        }
    }

    private fun loadBooks() = lifecycleScope.launch{
        val books = getBooks()
        adapter.setData(books)
    }

    private suspend fun getBooks() = repository.getBooks()

    private fun showNoBooksView() {
        binding.booksRecyclerView.gone()
        binding.noBooks.visible()
    }
    private fun showBooksView() {
        binding.booksRecyclerView.visible()
        binding.noBooks.gone()
    }
}
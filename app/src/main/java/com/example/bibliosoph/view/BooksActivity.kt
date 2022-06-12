package com.example.bibliosoph.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.app.invisible
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

            binding.booksRecyclerView.adapter = adapter
            binding.booksRecyclerView.layoutManager = LinearLayoutManager(this@BooksActivity)

        lifecycleScope.launch {
            if(repository.getBooks().isEmpty()) {
                binding.booksRecyclerView.invisible()
                binding.noBooks.visible()
            }
        }
    }
}
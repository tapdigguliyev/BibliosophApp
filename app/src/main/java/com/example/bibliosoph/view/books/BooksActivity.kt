package com.example.bibliosoph.view.books

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.app.gone
import com.example.bibliosoph.app.visible
import com.example.bibliosoph.databinding.ActivityBooksBinding
import com.example.bibliosoph.view.addbooks.AddBookActivity
import kotlinx.coroutines.launch

class BooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooksBinding

    private val repository by lazy { BibliosophApplication.repository }
    private val adapter by lazy { BooksAdapter() }

    companion object{
        fun intent(context: Context): Intent {
            return Intent(context, AddBookActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initUi()
    }

    private fun initUi() {
        setupRecyclerView()

        lifecycleScope.launch {
            if(getBooks().isEmpty()) {
                showNoBooksView()
            } else {
                showBooksView()
                loadBooks()
            }
        }

        binding.addBookFloatingActionButton.setOnClickListener {
            startActivity(intent(this))
        }
    }

    private fun setupRecyclerView() {
        binding.booksRecyclerView.adapter = adapter
        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)

        configureSwipeToDeleteHandler()
    }

    private fun configureSwipeToDeleteHandler() {
        val swipeHandler = object : SwipeHelperCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                lifecycleScope.launch {
                    val booksAdapter = binding.booksRecyclerView.adapter as BooksAdapter
                    val booksAndGenres = repository.getBooks()

                    repository.removeBook(booksAndGenres[viewHolder.adapterPosition].book)
                    booksAdapter.removeBookAtPosition(viewHolder.adapterPosition)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.booksRecyclerView)
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
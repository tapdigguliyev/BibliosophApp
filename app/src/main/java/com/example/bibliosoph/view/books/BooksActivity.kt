package com.example.bibliosoph.view.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.app.gone
import com.example.bibliosoph.app.visible
import com.example.bibliosoph.databinding.ActivityBooksBinding
import com.example.bibliosoph.view.addbooks.AddBookActivity
import com.example.bibliosoph.viewmodel.BooksActivityViewModel
import kotlinx.coroutines.launch

class BooksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBooksBinding
    private lateinit var viewModel: BooksActivityViewModel
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

        viewModel = ViewModelProvider(this)[BooksActivityViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        initUi()
    }

    private fun initUi() {
        setupRecyclerView()

        lifecycleScope.launch {
            if(viewModel.getBooks().isEmpty()) {
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

        binding.booksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 1 && binding.addBookFloatingActionButton.isShown) {
                    binding.addBookFloatingActionButton.hide()
                } else if (dy < -1 && !binding.addBookFloatingActionButton.isShown) {
                    binding.addBookFloatingActionButton.show()
                } else if (binding.booksRecyclerView.canScrollVertically(-1)) {
                    binding.addBookFloatingActionButton.show()
                }
            }
        })

        configureSwipeToDeleteHandler()
    }

    private fun configureSwipeToDeleteHandler() {
        val swipeHandler = object : SwipeHelperCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                lifecycleScope.launch {
                    val booksAdapter = binding.booksRecyclerView.adapter as BooksAdapter
                    val booksAndGenres = viewModel.getBooks()

                    viewModel.removeBook(booksAndGenres[viewHolder.adapterPosition].book)
                    booksAdapter.removeBookAtPosition(viewHolder.adapterPosition)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.booksRecyclerView)
    }

    private fun loadBooks() = lifecycleScope.launch{
        val books = viewModel.getBooks()
        adapter.setData(books)
    }

    private fun showNoBooksView() {
        binding.booksRecyclerView.gone()
        binding.noBooks.visible()
    }

    private fun showBooksView() {
        binding.booksRecyclerView.visible()
        binding.noBooks.gone()
    }
}
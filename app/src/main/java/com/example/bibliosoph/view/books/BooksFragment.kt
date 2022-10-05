package com.example.bibliosoph.view.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.app.gone
import com.example.bibliosoph.app.visible
import com.example.bibliosoph.databinding.FragmentBooksBinding
import com.example.bibliosoph.view.MainActivity
import com.example.bibliosoph.view.addbooks.AddBookActivity
import com.example.bibliosoph.view.books.adapter.BooksAdapter
import com.example.bibliosoph.viewmodel.BooksFragmentViewModel
import kotlinx.coroutines.launch

class BooksFragment : Fragment() {
    private lateinit var binding: FragmentBooksBinding
    private lateinit var viewModel: BooksFragmentViewModel
    private val adapter by lazy { BooksAdapter() }

    companion object{
        fun intent(context: Context): Intent {
            return Intent(context, AddBookActivity::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).booksFragmentViewModel
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
            activity?.let {
                startActivity(intent(it))
            }
        }
    }

    private fun setupRecyclerView() {
        binding.booksRecyclerView.adapter = adapter
        binding.booksRecyclerView.layoutManager = LinearLayoutManager(activity)

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
        activity?.let {
            val swipeHandler = object : SwipeHelperCallback(it) {
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
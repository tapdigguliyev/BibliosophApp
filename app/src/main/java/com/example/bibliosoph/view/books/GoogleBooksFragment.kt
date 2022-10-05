package com.example.bibliosoph.view.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.databinding.FragmentGoogleBooksBinding
import com.example.bibliosoph.other.Constants.Companion.QUERY_PAGE_SIZE
import com.example.bibliosoph.other.Constants.Companion.SEARCH_BOOKS_TIME_DELAY
import com.example.bibliosoph.other.Resource
import com.example.bibliosoph.view.MainActivity
import com.example.bibliosoph.view.books.adapter.GoogleBooksAdapter
import com.example.bibliosoph.viewmodel.GoogleBooksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GoogleBooksFragment : Fragment() {
    private lateinit var binding: FragmentGoogleBooksBinding
    private lateinit var viewModel: GoogleBooksViewModel
    private lateinit var googleBooksAdapter: GoogleBooksAdapter

    companion object{
        const val TAG = "GoogleBooksFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGoogleBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        var job: Job? = null
        binding.etSearchBooks.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch(Dispatchers.Main) {
                delay(SEARCH_BOOKS_TIME_DELAY)
                editable?.let {
                    if (it.toString().isNotEmpty()) {
//                        Log.d(TAG, "start index in googleBooksFragment: ${viewModel.startIndex}")
                        viewModel.searchForBooks(it.toString())
                    } else {
                        viewModel.searchBooks.postValue(null)
                        googleBooksAdapter.differ.submitList(emptyList())
                    }
                }
            }

        }

        viewModel.searchBooks.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { googleBooksResponse ->
                        googleBooksAdapter.differ.submitList(googleBooksResponse.items.toList())
//                        val totalPages = googleBooksResponse.totalItems / QUERY_PAGE_SIZE + 2
//                        isLastPage = viewModel.searchBooksPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                        Toast.makeText(activity, "An error occurred:$message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    var isLoading = false
//    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndIsNotLastPage = !isLoading  //&& !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
//            Log.d(TAG, "isAtLastItem $isAtLastItem")
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndIsNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
//            Log.d(TAG, "shouldPaginate $shouldPaginate")

            if (shouldPaginate) {
                viewModel.searchBooks.observe(viewLifecycleOwner) {response ->
                    Log.d(TAG, "size of items:${response.data?.items?.size}")}
                viewModel.searchForBooks(binding.etSearchBooks.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun setupRecyclerView() {
        googleBooksAdapter = GoogleBooksAdapter()
        binding.rvSearchBooks.apply {
            adapter = googleBooksAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@GoogleBooksFragment.scrollListener)
        }
    }
}
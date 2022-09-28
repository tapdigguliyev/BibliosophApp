package com.example.bibliosoph.view.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bibliosoph.databinding.FragmentGoogleBooksBinding
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
                        googleBooksAdapter.differ.submitList(googleBooksResponse.items)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        googleBooksAdapter = GoogleBooksAdapter()
        binding.rvSearchBooks.apply {
            adapter = googleBooksAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
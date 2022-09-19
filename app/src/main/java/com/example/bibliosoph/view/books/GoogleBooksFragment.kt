package com.example.bibliosoph.view.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bibliosoph.databinding.FragmentGoogleBooksBinding

class GoogleBooksFragment : Fragment() {
    private lateinit var binding: FragmentGoogleBooksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGoogleBooksBinding.inflate(inflater, container, false)
        return binding.root
    }
}
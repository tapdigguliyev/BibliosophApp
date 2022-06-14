package com.example.bibliosoph.view.addbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bibliosoph.databinding.ActivityAddBookBinding

class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
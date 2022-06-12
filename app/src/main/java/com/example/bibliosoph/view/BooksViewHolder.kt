package com.example.bibliosoph.view

import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.databinding.ItemBookBinding
import com.example.bibliosoph.model.room.relations.BookAndGenre

class BooksViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root) {

    private lateinit var bookAndGenre: BookAndGenre

    fun bind(bookAndGenre: BookAndGenre) {
        this.bookAndGenre = bookAndGenre

        binding.bookId.text = bookAndGenre.book.id
        binding.bookName.text = bookAndGenre.book.name
        binding.pageCount.text = bookAndGenre.book.pageNumber
        binding.genre.text = bookAndGenre.genre.name
        binding.startDate.text = bookAndGenre.book.startDate.toString()
        binding.endDate.text = bookAndGenre.book.endDate.toString()
    }
}
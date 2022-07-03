package com.example.bibliosoph.view

import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.databinding.ItemBookBinding
import com.example.bibliosoph.model.room.relations.BookAndGenre

class BooksViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(bookAndGenre: BookAndGenre) {
        val (book, genre) = bookAndGenre

        binding.bookId.text = book.id
        binding.bookName.text = book.name
        binding.startDate.text = book.startDate
        binding.endDate.text = book.endDate
        binding.pageCount.text = book.pageNumber
        binding.genre.text = genre.name
        binding.writerName.text = book.writerName
    }
}
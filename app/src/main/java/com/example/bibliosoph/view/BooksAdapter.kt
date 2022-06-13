package com.example.bibliosoph.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.databinding.ItemBookBinding
import com.example.bibliosoph.model.room.relations.BookAndGenre

class BooksAdapter : RecyclerView.Adapter<BooksViewHolder>() {
    private val booksAndGenres = mutableListOf<BookAndGenre>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(booksAndGenres[position])
    }

    override fun getItemCount() = booksAndGenres.size

    fun setData(books: List<BookAndGenre>) {
        booksAndGenres.clear()
        booksAndGenres.addAll(books)
        notifyDataSetChanged()
    }
}
package com.example.bibliosoph.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.databinding.ItemBookBinding
import com.example.bibliosoph.model.room.relations.BookAndGenre
import com.example.bibliosoph.view.books.ItemTouchHelperListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BooksAdapter : RecyclerView.Adapter<BooksViewHolder>(), ItemTouchHelperListener {
    private val booksAndGenres = mutableListOf<BookAndGenre>()
    private val repository by lazy { BibliosophApplication.repository }

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

    override fun onItemDismiss(position: Int) {

        GlobalScope.launch(Dispatchers.Main) {
            repository.removeBook(booksAndGenres[position].book)
            booksAndGenres.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
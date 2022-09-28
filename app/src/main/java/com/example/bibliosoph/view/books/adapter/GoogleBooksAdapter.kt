package com.example.bibliosoph.view.books.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bibliosoph.R
import com.example.bibliosoph.databinding.ItemGoogleBookBinding
import com.example.bibliosoph.model.googlebook.Item

class GoogleBooksAdapter : RecyclerView.Adapter<GoogleBooksAdapter.GoogleBookViewHolder>() {

    inner class GoogleBookViewHolder(val binding: ItemGoogleBookBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object :DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.volumeInfo == newItem.volumeInfo
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoogleBookViewHolder {
        return GoogleBookViewHolder(ItemGoogleBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GoogleBookViewHolder, position: Int) {
        val googleBookItem = differ.currentList[position]
        holder.itemView.apply {
            googleBookItem.volumeInfo?.imageLinks?.thumbnail.let {
                Glide.with(this)
                    .load(it)
                    .error(R.drawable.ic_default_book_icon)
                    .into(holder.binding.ivGoogleBookImage)
            }
            holder.binding.tvGoogleBookName.text = googleBookItem.volumeInfo?.title ?: "No book name"

            holder.binding.tvGoogleBookAuthor.text = setAuthorsNames(googleBookItem)

            holder.binding.tvGoogleBookLanguage.text = googleBookItem.volumeInfo?.language ?: "No language"

            holder.binding.tvGoogleBookPageCount.text = googleBookItem.volumeInfo?.pageCount?.toString() ?: "No page count"
        }
    }

    override fun getItemCount() = differ.currentList.size

    private fun setAuthorsNames(googleBookItem: Item): String {
        val authors = googleBookItem.volumeInfo?.authors

        if (authors.isNullOrEmpty()) {
            return "No author"
        }

        authors.let {
            return if (it.size == 1) {
                it[0]
            } else if (it.size > 1) {
                val builder = StringBuilder()
                for (i in it.indices) {
                    builder.apply {
                        this.append(it[i])
                        if (i < it.size - 1) {
                            this.append(", ")
                        }
                    }
                }
                builder.toString()
            } else {
                "No author"
            }
        }
    }
}
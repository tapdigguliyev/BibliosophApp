package com.example.bibliosoph.view.addbooks

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bibliosoph.R
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.app.stringToDate
import com.example.bibliosoph.databinding.ActivityAddBookBinding
import com.example.bibliosoph.model.Book
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding
    private val repository by lazy { BibliosophApplication.repository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() = lifecycleScope.launch {
        configureDatePickerDialog(binding.addStartDate)
        configureDatePickerDialog(binding.addEndDate)
        configureGenreSelectionSpinner()
        binding.addBook.setOnClickListener {
                generateBook()
            }
        }

    private fun generateBook() = lifecycleScope.launch {
        val bookId = binding.addBookId.text.toString()
        val bookName = binding.addBookName.text.toString()
        val startDate = binding.addStartDate.text.toString().stringToDate()
        val endDate = binding.addEndDate.text.toString().stringToDate()
        val pageCount = binding.addPageCount.text.toString()
        val genreId = repository.getGenres().firstOrNull { it.name == binding.addGenreSpinner.selectedItem }?.id
        val writerName = binding.addWriterName.text.toString()

        if (bookId.isNotBlank() && bookName.isNotBlank() && !genreId.isNullOrBlank()) {
            repository.addBook(
                Book(
                    id = bookId,
                    name = bookName,
                    startDate = startDate,
                    endDate = endDate,
                    pageNumber = pageCount,
                    writerName = writerName,
                    genreId = genreId
                )
            )
        }
        finish()
    }

    private fun configureGenreSelectionSpinner() = lifecycleScope.launch {
        val genres = repository.getGenres().map{ it.name }

        val adapter = ArrayAdapter(this@AddBookActivity, android.R.layout.simple_spinner_item, genres)
        binding.addGenreSpinner.adapter = adapter
    }

    private fun configureDatePickerDialog(view: TextView) {
        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel(myCalendar, view)
        }

        view.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
    }

    private fun updateDateLabel(myCalendar: Calendar, view: TextView) {
        val myFormat = getString(R.string.date_label)
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
        view.text = simpleDateFormat.format(myCalendar.time)
    }
}
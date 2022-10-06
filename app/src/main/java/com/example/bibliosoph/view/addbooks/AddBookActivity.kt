package com.example.bibliosoph.view.addbooks

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.bibliosoph.R
import com.example.bibliosoph.app.stringToDate
import com.example.bibliosoph.databinding.ActivityAddBookBinding
import com.example.bibliosoph.viewmodel.AddBookActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding
    private val viewModel: AddBookActivityViewModel by viewModels()

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

    private fun generateBook() = viewModel.viewModelScope.launch {
        viewModel.bookId = binding.addBookId.text.toString()
        viewModel.bookName = binding.addBookName.text.toString()
        viewModel.startDate = binding.addStartDate.text.toString().stringToDate()
        viewModel.endDate = binding.addEndDate.text.toString().stringToDate()
        viewModel.pageCount = binding.addPageCount.text.toString()
        viewModel.genreId = viewModel.getGenres().firstOrNull { it.name == binding.addGenreSpinner.selectedItem }?.id
        viewModel.writerName = binding.addWriterName.text.toString()

        viewModel.addBook()
        finish()
    }

    private fun configureGenreSelectionSpinner() = lifecycleScope.launch {
        val genres = viewModel.getGenres().map{ it.name }

        val adapter = ArrayAdapter(this@AddBookActivity, android.R.layout.simple_spinner_item, genres)
        binding.addGenreSpinner.adapter = adapter
        binding.addGenreSpinner.setSelection(viewModel.selectedSpinnerItemPosition)

        binding.addGenreSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectedSpinnerItemPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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
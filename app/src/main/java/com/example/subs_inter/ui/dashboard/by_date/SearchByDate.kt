package com.example.subs_inter.ui.dashboard.by_date

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.subs_inter.databinding.ActivitySearchByDateBinding
import com.example.subs_inter.ui.dashboard.by_date.ResultSearchByDate.Companion.END_DATE
import com.example.subs_inter.ui.dashboard.by_date.ResultSearchByDate.Companion.START_DATE
import java.util.Calendar

class SearchByDate : AppCompatActivity() {

    private lateinit var binding: ActivitySearchByDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatePicker(binding.editTextStartDate)
        setupDatePicker(binding.editTextEndDate)

        binding.buttonGetAll.setOnClickListener {
            // Retrieve the dates from the TextInputEditText fields
            val startDate = binding.editTextStartDate.text.toString()
            val endDate = binding.editTextEndDate.text.toString()

            // Create an Intent to start ResultSearchByDate Activity
            val intent = Intent(this@SearchByDate, ResultSearchByDate::class.java)
            // Pass the dates as extras in the Intent
            intent.putExtra(START_DATE, startDate)
            intent.putExtra(END_DATE, endDate)
            startActivity(intent)  // Start the activity with the intent
        }
    }

    @SuppressLint("DefaultLocale")
    private fun setupDatePicker(editText: com.google.android.material.textfield.TextInputEditText) {
        editText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                // Set the date in the format "YYYY-MM-DD"
                editText.setText(String.format("%d-%02d-%02d", year, month + 1, dayOfMonth))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}

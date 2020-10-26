package com.lalee.madlevel5task2.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.lalee.madlevel5task2.R
import kotlinx.android.synthetic.main.fragment_add_game.*
import kotlinx.android.synthetic.main.item_fab.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@RequiresApi(Build.VERSION_CODES.N)
class AddGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setImageResource(R.drawable.ic_baseline_save_24)
        btn_date_ok.setOnClickListener { openDateDialog() }
    }

    private fun addGameToBackLog(){

    }

    private fun openDateDialog() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val dpd = context?.let { it2 ->
            DatePickerDialog(it2, { _, yearr, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                tv_date_output.text =
                    String.format("Chosen release date: %s-%s-%s ", dayOfMonth, monthOfYear, yearr)
            }, year, month, day)
        }
        dpd!!.show()
    }
}
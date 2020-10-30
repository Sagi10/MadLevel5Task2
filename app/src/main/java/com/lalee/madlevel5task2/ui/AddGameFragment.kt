package com.lalee.madlevel5task2.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lalee.madlevel5task2.R
import com.lalee.madlevel5task2.model.Game
import com.lalee.madlevel5task2.model.Platform
import com.lalee.madlevel5task2.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.fragment_add_game.*
import kotlinx.android.synthetic.main.item_fab.*
import java.lang.Error
import java.time.LocalDate
import java.time.LocalDate.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@RequiresApi(Build.VERSION_CODES.N)
class AddGameFragment : Fragment() {

    private val gameViewModel: GameViewModel by viewModels()

    private var platform: Platform? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setImageResource(R.drawable.ic_baseline_save_24)

        btn_date_ok.setOnClickListener {
            //openDateDialog()
        }

        radio_group.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            val checkedRadioButton = radioGroup.findViewById<RadioButton>(i)
            checkedRadioButton?.let {
                platform = onRadioButtonClicked(it)!!
            }
        }
        fab.setOnClickListener {
            Toast.makeText(activity, "CLICKED", Toast.LENGTH_SHORT).show()
          //  addGameToBackLog(input_title.text.toString(), platform!!)
        }
        observeInputFields()
    }

    /**
     * Observe if the input field are correct.
     */
    fun observeInputFields() {
        gameViewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        gameViewModel.succes.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
    }

    private fun addGameToBackLog(title: String, platform: Platform) {
        gameViewModel.insertGame(Game(title, platform, Date()))
    }

    private fun onRadioButtonClicked(view: View): Platform? {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_xbox ->
                    if (checked) {
                        return Platform.XBOX
                    }
                R.id.radio_playstation ->
                    if (checked) {
                        return Platform.PLAYSTATION
                    }
                R.id.radio_pc ->
                    if (checked) {
                        return Platform.PC
                    }
            }
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openDateDialog(): LocalDate? {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        var dateString: String
        var date: LocalDate? = null

        val dpd = context?.let { it2 ->
            DatePickerDialog(it2, { _, yearr, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                tv_date_output.text =
                    String.format("Chosen release date: %s-%s-%s ", yearr, monthOfYear, dayOfMonth)
                dateString = String.format("%s %s %s", yearr, monthOfYear, dayOfMonth)
                date = parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
                Toast.makeText(activity, date.toString(), Toast.LENGTH_SHORT).show()
            }, year, month, day)
        }
        dpd!!.show()
        return date
    }
}
package com.jpmc.nycschools.ui.schoolslist

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jpmc.nycschools.R
import com.jpmc.nycschools.data.repository.NycSchoolSatScores

@BindingAdapter("satScore")
fun bindSatScoresToTextView(textView: TextView, data: NycSchoolSatScores?) {
    data?.let {
        val scores = "Average SAT scores: \n\t\t" +
                "Writing Score: ${it.satWritingAvgScore} \n\t\t" +
                "Reading Score: ${it.satCriticalReadingAvgScore} \n\t\t" +
                "Math Score: ${it.satMathAvgScore}"
        textView.text = scores
    } ?: run {
        val string = textView.context.resources.getText(R.string.score_details_not_available)
        Log.d("tarana", "data is null - $string")
        textView.text = string
    }
}
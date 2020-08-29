package com.gahlot.workmanagersmaple

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val GET_TABLE_NUMBER = "number"
    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest
    var number = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputNumber = input_number
        val startButton = startTable

        // in case we want to trigger any work based on a condition
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        if (inputNumber.text.toString().isEmpty()) {
            number  = 0
        } else {
            number = inputNumber.text.toString().toInt()
        }

        oneTimeWorkRequest = OneTimeWorkRequest.Builder(SampleWorker::class.java).build()

        startButton.setOnClickListener {

            number = inputNumber.text.toString().toInt()
            oneTimeWorkRequest = OneTimeWorkRequest.Builder(SampleWorker::class.java)
                .setInputData(createInputData(number))
                .build()
            WorkManager.getInstance().enqueue(oneTimeWorkRequest!!)
        }
    }

    private fun createInputData(n : Int) : Data {

        return Data.Builder()
            .putInt(GET_TABLE_NUMBER,n)
            .build()
    }
}

package com.example.flashify

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class AddCardActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        //canceling and returning to main activity
        val cancelBack = findViewById<ImageButton>(R.id.cancel)
        val saveCard = findViewById<ImageButton>(R.id.saveAnswer)
        val questionInput = findViewById<EditText>(R.id.question_input).text.toString()
        val answerInput = findViewById<EditText>(R.id.answer_input).text.toString()
        val s1 = intent.getStringExtra("stringKey1"); // this string will be current question (edit)
        val s2 = intent.getStringExtra("stringKey2"); // this string will be current answer (edit)
        val s3 = intent.getStringExtra("stringKey3"); // this string will be current multi 1 (edit)
        val s4 = intent.getStringExtra("stringKey4"); // this string will be current multi 2 (edit)
        val s5 = intent.getStringExtra("stringKey5"); // this string will be current multi 3 (edit)
        val s6 = intent.getStringExtra("stringKey6"); // this string will be current multi 4 (edit)
        val oneInput = findViewById<EditText>(R.id.one).text.toString()
        val twoInput = findViewById<EditText>(R.id.two).text.toString()
        val threeInput = findViewById<EditText>(R.id.three).text.toString()
        val fourInput = findViewById<EditText>(R.id.four).text.toString()

        //cancel button - exit activity
        cancelBack.setOnClickListener {
            finish()
        }

        //placing saved question data into input place
        if (s1 != null) {
            findViewById<EditText>(R.id.question_input).setText(s1)
        }

        //placing saved answer data into input place
        if (s2 != null) {
            findViewById<EditText>(R.id.answer_input).setText(s2)
        }

        //multi 1
        if (s3 != null) {
            findViewById<EditText>(R.id.one).setText(s3)
        }

        //multi 2
        if (s4 != null) {
            findViewById<EditText>(R.id.two).setText(s4)
        }

        //multi 3
        if (s5 != null) {
            findViewById<EditText>(R.id.three).setText(s5)
        }

        //multi 4
        if (s6 != null) {
            findViewById<EditText>(R.id.four).setText(s6)
        }


        //pushing save data for question and answer to main activity
        saveCard.setOnClickListener {
            val data = Intent() // create a new Intent, this is where we will put our data

            data.putExtra(
                "string1",
                findViewById<EditText>(R.id.question_input).text.toString()
            ) // puts one string into the Intent, with the key as 'string1'

            //creating new test value for question
            val questionTest = findViewById<EditText>(R.id.question_input).text.toString()

            data.putExtra(
                "string2",
                findViewById<EditText>(R.id.answer_input).text.toString()
            ) // puts another string into the Intent, with the key as 'string2'

            //creating new test value for answer
            val answerTest = findViewById<EditText>(R.id.answer_input).text.toString()

            data.putExtra(
                "string3",
                findViewById<EditText>(R.id.one).text.toString()
            ) // puts another string into the Intent, with the key as 'string3'

            data.putExtra(
                "string4",
                findViewById<EditText>(R.id.two).text.toString()
            ) // puts another string into the Intent, with the key as 'string4'

            data.putExtra(
                "string5",
                findViewById<EditText>(R.id.three).text.toString()
            ) // puts another string into the Intent, with the key as 'string5'

            data.putExtra(
                "string6",
                findViewById<EditText>(R.id.four).text.toString()
            ) // puts another string into the Intent, with the key as 'string6'

            //if question or answer input null, error message
            if (questionTest.isEmpty() || answerTest.isEmpty())
            {
                val toast = Toast.makeText(applicationContext, "Unable to create new flashcard. Please input both question and answer to proceed.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }

            //if answer and question input NOT null, proceed
            if (questionTest.isNotEmpty() && answerTest.isNotEmpty())
            {
                setResult(RESULT_OK, data) // set result code and bundle data for response

                finish() // closes this activity and pass data to the original activity that launched this activity
            }

        }

    }
}
package com.example.flashify

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcards = mutableListOf<Flashcard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)

        if (allFlashcards.size > 0) {
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
        }

        val answerOne = findViewById<TextView>(R.id.answer_one)
        val answerTwo = findViewById<TextView>(R.id.answer_two)
        val answerThree = findViewById<TextView>(R.id.answer_three)
        val answerFour= findViewById<TextView>(R.id.answer_four)
        val toggleOff = findViewById<ImageButton>(R.id.invisible)
        val toggleOn = findViewById<ImageButton>(R.id.visibility)
        val resChoices = findViewById<Button>(R.id.reset)
        var showChoices = true
        val newCard = findViewById<ImageButton>(R.id.add)
        val editCard = findViewById<ImageButton>(R.id.edit)
        val nextCard = findViewById<ImageButton>(R.id.next)
        var currentCardDisplayedIndex = 0

        //data retrieval from AddCardActivity, inputting data into flashcard
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val extras = data?.extras

            if (extras != null) { // Check that we have data returned
                val string1 = extras.getString("string1") // 'string1' needs to match the key we used when we put the string in the Intent
                val string2 = extras.getString("string2")
                val string3 = extras.getString("string3")
                val string4 = extras.getString("string4")
                val string5 = extras.getString("string5")
                val string6 = extras.getString("string6")

                // Log the value of the strings for easier debugging
                Log.i("MainActivity", "string1: $string1")
                Log.i("MainActivity", "string2: $string2")
                Log.i("MainActivity", "string3: $string3")
                Log.i("MainActivity", "string3: $string4")
                Log.i("MainActivity", "string3: $string5")
                Log.i("MainActivity", "string3: $string6")

                //displaying newly created flashcard and multiple choices
                flashcardQuestion.text = string1
                flashcardAnswer.text = string2
                answerOne.text = string3
                answerTwo.text = string4
                answerThree.text = string5
                answerFour.text = string6

                //save newly created flashcard to database
                if (string1 != null && string2 != null) {
                    flashcardDatabase.insertCard(Flashcard(string1, string2))
                    // Update set of flashcards to include new card
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                } else {
                    Log.e("TAG", "Missing question or answer to input into database. Question is $string1 and answer is $string2")
                }
                //snackbar msg
                Snackbar.make(findViewById(R.id.flashcard_question),
                    "New flashcard successfully created!",
                    Snackbar.LENGTH_SHORT)
                    .show()

            } else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }
        }

        //when add button clicked, change to AddCardActivity
        newCard.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

        editCard.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("stringKey1", findViewById<TextView>(R.id.flashcard_question).text.toString());
            intent.putExtra("stringKey2", findViewById<TextView>(R.id.flashcard_answer).text.toString());
            intent.putExtra("stringKey3", findViewById<TextView>(R.id.answer_one).text.toString());
            intent.putExtra("stringKey4", findViewById<TextView>(R.id.answer_two).text.toString());
            intent.putExtra("stringKey5", findViewById<TextView>(R.id.answer_three).text.toString());
            intent.putExtra("stringKey6", findViewById<TextView>(R.id.answer_four).text.toString());
            resultLauncher.launch(intent)
        }

        //setting visible icon to not display at beginning
        toggleOn.visibility = View.INVISIBLE

        //setting answer to not display at beginning
        flashcardAnswer.visibility = View.INVISIBLE

        //setting answer to display when question clicked, and hide question
        flashcardQuestion.setOnClickListener {
            flashcardQuestion.visibility = View.INVISIBLE
            flashcardAnswer.visibility = View.VISIBLE
        }

        //setting question to display when answer clicked, and hide answer again
        flashcardAnswer.setOnClickListener {
            flashcardQuestion.visibility = View.VISIBLE
            flashcardAnswer.visibility = View.INVISIBLE
        }

        //choosing answer one -- colors
        answerOne.setOnClickListener {
            answerOne.setBackgroundResource(R.drawable.card_bg4)
            answerThree.setBackgroundResource(R.drawable.card_bg3)
        }

        //choosing answer two -- colors
        answerTwo.setOnClickListener {
            answerTwo.setBackgroundResource(R.drawable.card_bg4)
            answerThree.setBackgroundResource(R.drawable.card_bg3)
        }

        //choosing answer three -- colors
        answerThree.setOnClickListener {
            answerThree.setBackgroundResource(R.drawable.card_bg3)
        }

        //choosing answer four -- colors
        answerFour.setOnClickListener {
            answerFour.setBackgroundResource(R.drawable.card_bg4)
            answerThree.setBackgroundResource(R.drawable.card_bg3)
        }

        //hiding choices when invisible icon clicked, changing icon
        toggleOff.setOnClickListener {
            showChoices = false
            answerFour.visibility = View.INVISIBLE
            answerThree.visibility = View.INVISIBLE
            answerTwo.visibility = View.INVISIBLE
            answerOne.visibility = View.INVISIBLE
            toggleOff.visibility = View.INVISIBLE
            toggleOn.visibility = View.VISIBLE
        }

        //showing choices when visible icon clicked, changing icon back
        toggleOn.setOnClickListener {
            showChoices = true
            answerFour.visibility = View.VISIBLE
            answerThree.visibility = View.VISIBLE
            answerTwo.visibility = View.VISIBLE
            answerOne.visibility = View.VISIBLE
            toggleOff.visibility = View.VISIBLE
            toggleOn.visibility = View.INVISIBLE
        }

        //reset button
        resChoices.setOnClickListener {
            answerFour.setBackgroundResource(R.drawable.card_bg2)
            answerThree.setBackgroundResource(R.drawable.card_bg2)
            answerTwo.setBackgroundResource(R.drawable.card_bg2)
            answerOne.setBackgroundResource(R.drawable.card_bg2)
            flashcardQuestion.setText(R.string.questionOriginal)
            flashcardAnswer.setText(R.string.answerOriginal)
            answerOne.setText(R.string.answerOne)
            answerTwo.setText(R.string.answerTwo)
            answerThree.setText(R.string.answerThree)
            answerFour.setText(R.string.answerFour)
        }

        //next button
        nextCard.setOnClickListener {
            // do not proceed to next if no other cards exist
            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener
            }

            // advance our pointer index so we can show the next card
            currentCardDisplayedIndex++

            // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
            if(currentCardDisplayedIndex >= allFlashcards.size) {
                Snackbar.make(
                    findViewById<TextView>(R.id.flashcard_question), // This should be the TextView for displaying your flashcard question
                    "End of flashcards reached, going back to starting flashcard.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                currentCardDisplayedIndex = 0
            }

            // set the question and answer TextViews with data from the database
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()
            val (question, answer) = allFlashcards[currentCardDisplayedIndex]

            flashcardAnswer.text = answer
            flashcardQuestion.text = question
        }

    }
}
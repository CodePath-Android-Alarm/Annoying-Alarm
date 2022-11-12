package com.example.annoyingalarm

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MatchingGameFragment : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<Card>
    private var indexOfSingleSelectedCard: Int? = null
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_matching_game)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        mp = MediaPlayer.create(applicationContext,R.raw.delta5)
        mp.start()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId)
            {
                R.id.matchingGame -> true
                R.id.alarm -> startActivity(Intent(this@MatchingGameFragment,MainActivity::class.java))
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.matchingGame

        val imageButton1 = findViewById<ImageButton>(R.id.imageButton1)
        val imageButton2 = findViewById<ImageButton>(R.id.imageButton2)
        val imageButton3 = findViewById<ImageButton>(R.id.imageButton3)
        val imageButton4 = findViewById<ImageButton>(R.id.imageButton4)
        val imageButton5 = findViewById<ImageButton>(R.id.imageButton5)
        val imageButton6 = findViewById<ImageButton>(R.id.imageButton6)
        val imageButton7 = findViewById<ImageButton>(R.id.imageButton7)
        val imageButton8 = findViewById<ImageButton>(R.id.imageButton8)
        val imageButton9 = findViewById<ImageButton>(R.id.imageButton9)
        val imageButton10 = findViewById<ImageButton>(R.id.imageButton10)
        val imageButton11 = findViewById<ImageButton>(R.id.imageButton11)
        val imageButton12 = findViewById<ImageButton>(R.id.imageButton12)

        // Add buttons to list
        buttons = listOf(imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,
            imageButton7,imageButton8,imageButton9,imageButton10,imageButton11,imageButton12)

        // Add images to list
        val images = mutableListOf(R.drawable.ic_android,R.drawable.ic_bike,R.drawable.ic_fire,
            R.drawable.ic_music,R.drawable.ic_star,R.drawable.ic_target)
        // Double all images in list
        images.addAll(images)
        // Randomize card order
        images.shuffle()

        cards = buttons.indices.map { index ->
            Card(images[index])
        }

        buttons.forEachIndexed{index, button ->
            button.setOnClickListener{
                button.isClickable = false
                button.animate().apply {
                    duration = 500
                    rotationYBy(180f)
                }.withEndAction {
                    button.isClickable = true
                    updateModels(index)
                    updateViews()
                }.start()
            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if(card.isMatched)
            {
                button.alpha = 0.2f
            }
            button.setImageResource(if(card.isFaceUp) card.identifier else R.drawable.ic_code)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        if (card.isFaceUp)
        {
            // TODO: make card flip and sparkle
            Toast.makeText(this,"✨Already Matched✨",Toast.LENGTH_SHORT).show()
            return
        }

        if(indexOfSingleSelectedCard == null)
        {
            restoreCards()
            indexOfSingleSelectedCard = position
        }else{
            checkForMatch(indexOfSingleSelectedCard!!,position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if(!card.isMatched and card.isFaceUp)
            {
                button.isClickable = false
                button.animate().apply {
                    duration = 500
                    rotationYBy(180f)
                }.withEndAction{button.isClickable = true}.start()
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if(cards[position1].identifier == cards[position2].identifier)
        {
            cards[position1].isMatched = true
            cards[position2].isMatched = true

            if(cards.all{ it.isMatched })
            {
                for (button in buttons)
                {
                    button.isClickable = false
                    button.animate().apply {
                        duration = 500
                        rotationXBy(360f)
                    }.withEndAction{
                        button.animate().apply {
                            duration = 500
                            rotationYBy(360f)
                        }.withEndAction{
                            mp.stop()
                            this.finish()
                        }
                    }.start()
                }
            }
        }
    }

}

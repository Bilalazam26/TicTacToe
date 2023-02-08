package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tictactoe.databinding.ActivityAddPlayersBinding

class AddPlayersActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddPlayersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGameButton.setOnClickListener {
            val playerOne = binding.playerOne.text.toString()
            val playerTwo = binding.playerTwo.text.toString()

            if (playerOne.isNullOrEmpty() || playerTwo.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter player name", Toast.LENGTH_SHORT).show();
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("playerOne", playerOne);
                intent.putExtra("playerTwo", playerTwo);
                startActivity(intent);
            }
        }

    }
}
package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ActivityMainBinding
    private val combinationList = arrayListOf<Array<Int>>()
    private var boxPositions = arrayOf(0,0,0,0,0,0,0,0,0)
    private lateinit var boxes:Array<ImageView>
    private var playerTurn = 1
    private var totalSelectedBoxes = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boxes = arrayOf(binding.image1,
            binding.image2,
            binding.image3,
            binding.image4,
            binding.image5,
            binding.image6,
            binding.image7,
            binding.image8,
            binding.image9)
        boxes.forEach {
            it.setOnClickListener(this)
        }

        combinationList.add(arrayOf(0, 1, 2))
        combinationList.add(arrayOf(3, 4, 5))
        combinationList.add(arrayOf(6, 7, 8))
        combinationList.add(arrayOf(0, 3, 6))
        combinationList.add(arrayOf(1, 4, 7))
        combinationList.add(arrayOf(2, 5, 8))
        combinationList.add(arrayOf(2, 4, 6))
        combinationList.add(arrayOf(0, 4, 8))

        val playerOneName = intent.getStringExtra("playerOne")
        val playerTwoName = intent.getStringExtra("playerTwo")

        binding.playerOneName.text = playerOneName
        binding.playerTwoName.text = playerTwoName

    }

    private fun performAction(imageView: ImageView, selectedBoxPosition: Int) {
        boxPositions[selectedBoxPosition] = playerTurn
        if (playerTurn == 1) {
            setBoardAfterAction(R.drawable.ximage, imageView, 2)
        } else {
            setBoardAfterAction(R.drawable.oimage, imageView, 1)
        }
    }

    private fun setBoardAfterAction(image: Int, imageView: ImageView, nextPlayerTurn: Int) {
        imageView.setImageResource(image)
        val playername = if (nextPlayerTurn == 2) binding.playerOneName.text.toString()
                            else binding.playerTwoName.text.toString()
        if (checkResult()) {
            showDialog("The Winner is : $playername")
        } else if (totalSelectedBoxes == 9) {
            showDialog("Game Draw")
        } else {
            changePlayerTurn(nextPlayerTurn)
            totalSelectedBoxes++
        }
    }

    private fun showDialog(message: String) {
        val resultDialog = ResultDialog(this, message, this@MainActivity)
        resultDialog.setCancelable(false)
        resultDialog.show()
    }

    private fun changePlayerTurn(currentPlayerTurn: Int) {
        playerTurn = currentPlayerTurn
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.black_border)
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box)
        } else {

            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border)
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_box)
        }
    }

    private fun checkResult(): Boolean {
        var isWon = false
        for (i in 0 until combinationList.size) {
            val combination = combinationList[i]

            if (boxPositions[combination[0]] == playerTurn &&
                boxPositions[combination[1]] == playerTurn &&
                boxPositions[combination[2]] == playerTurn) {
                isWon = true
            }
        }
        return isWon
    }

    private fun isBoxSelectable(boxPosition: Int): Boolean {
        var isSelectable = false
        if (boxPositions[boxPosition] == 0) {
            isSelectable = true
        }
        return isSelectable
    }

    fun restartGame() {
        boxPositions = arrayOf(0,0,0,0,0,0,0,0,0)
        playerTurn = 1
        totalSelectedBoxes = 1
        boxes.forEach {
            it.setImageResource(R.drawable.white_box)
        }
    }

    override fun onClick(view: View?) {
        /*when (view?.id) {
            binding.image1.id -> if (isBoxSelectable(0)) performAction(binding.image1, 0)
            binding.image2.id -> if (isBoxSelectable(1)) performAction(binding.image2, 1)
            binding.image3.id -> if (isBoxSelectable(2)) performAction(binding.image3, 2)
            binding.image4.id -> if (isBoxSelectable(3)) performAction(binding.image4, 3)
            binding.image5.id -> if (isBoxSelectable(4)) performAction(binding.image5, 4)
            binding.image6.id -> if (isBoxSelectable(5)) performAction(binding.image6, 5)
            binding.image7.id -> if (isBoxSelectable(6)) performAction(binding.image7, 6)
            binding.image8.id -> if (isBoxSelectable(7)) performAction(binding.image8, 7)
            binding.image9.id -> if (isBoxSelectable(8)) performAction(binding.image9, 8)
        }*/
        val image = view as ImageView
        val index = boxes.indexOf(image)
        if (isBoxSelectable(index)) performAction(image, index)

    }
}
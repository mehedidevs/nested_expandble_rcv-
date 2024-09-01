package com.example.nested_expandable_recyclerview

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nested_expandable_recyclerview.databinding.ActivityMainBinding
import com.example.nested_expandable_recyclerview.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val colors = listOf(
            ColorItem("#FF0000"), // Red
            ColorItem("#00FF00"), // Green
            ColorItem("#0000FF"), // Blue
            ColorItem("#FFFF00"), // Yellow
            ColorItem("#800080"), // Purple
            ColorItem("#008080"), // Teal
            ColorItem("#FFA500"), // Orange
            ColorItem("#FFC0CB")  // Pink
        )

        val adapter = ColorSelectionAdapter(colors)
        binding.selectionRcv.adapter = adapter

        adapter.setOnColorSelectedListener { colorHex ->
            // Handle color selection
            findViewById<View>(R.id.colorPreview).setBackgroundColor(
                Color.parseColor(
                    colorHex
                )
            )
            Toast.makeText(this, "Selected color: $colorHex", Toast.LENGTH_SHORT).show()
        }


    }
}
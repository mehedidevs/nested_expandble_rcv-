package com.example.nested_expandable_recyclerview

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setMargins
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nested_expandable_recyclerview.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity(), OnParentItemClickListener, OnChildItemClickListener,
    OnSubChildItemClickListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var selectedColor = ""

    // Variables to keep track of the currently selected color and size
    private var selectedColorButton: View? = null
    private var selectedSizeTextView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Get the display width
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels

// Set the drawer's width to the full screen width
        val layoutParams = binding.endDrawer.layoutParams
        layoutParams.width = screenWidth
        binding.endDrawer.layoutParams = layoutParams


        binding.btnNavigate.setOnClickListener {
            handleDrawer(binding)
        }

        // Set up headers to toggle visibility of containers with animation
        setupExpandableSections()

        // Load mock JSON data and display filters
        val filterResponse = loadMockJson()
        filterResponse?.let {
            displayFilters(it)
        }

        // Set Apply button listener
        binding.applyButton.setOnClickListener {
            val selectedFiltersSummary = getSelectedFilters()
            Toast.makeText(this, selectedFiltersSummary, Toast.LENGTH_LONG).show()
        }
        dataSetToUi(binding)
    }

    private fun setupExpandableSections() {
        // Layout header click listener
        binding.layoutHeader.setOnClickListener {
            binding.layoutContainer.toggleExpandCollapse()
        }

        // Availability header click listener
        binding.availabilityHeader.setOnClickListener {
            binding.availabilityContainer.toggleExpandCollapse()
        }

        // Product Type header click listener
        binding.productTypeHeader.setOnClickListener {
            binding.productTypeRadioGroup.toggleExpandCollapse()
        }

        // Brand header click listener
        binding.brandHeader.setOnClickListener {
            binding.brandContainer.toggleExpandCollapse()
        }

        // Color header click listener
        binding.colorHeader.setOnClickListener {
          //  binding.colorContainer.toggleExpandCollapse()
            binding.sizeContainer.toggleExpandCollapse()
        }

        // Size header click listener
        binding.sizeHeader.setOnClickListener {
            binding.sizeContainer.toggleExpandCollapse()
        }

        // Tags header click listener
        binding.tagsHeader.setOnClickListener {
            binding.tagsContainer.toggleExpandCollapse()
        }
    }




    private fun createSizeTextView(sizeOption: String): View {
        return TextView(this).apply {
            text = sizeOption
            setPadding(16, 8, 16, 8)
            setBackgroundResource(R.drawable.tag_background)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 8, 8, 8)
            }

            // Handle click for single selection
            setOnClickListener {
                // Deselect the previously selected size, if any
                selectedSizeTextView?.isSelected = false
                (selectedSizeTextView as? TextView)?.setBackgroundResource(R.drawable.tag_background)

                // Select the new size
                isSelected = true
                setBackgroundResource(R.drawable.selected_tag_background)
                selectedSizeTextView = this
            }
        }
    }


    private fun View.toggleExpandCollapse() {
        if (visibility == View.GONE) {
            expand()
        } else {
            collapse()
        }
    }

    private fun loadMockJson(): FilterResponse? {
        return try {
            val inputStream = assets.open("mock_filters.json")
            val reader = InputStreamReader(inputStream)
            Gson().fromJson(reader, FilterResponse::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun displayFilters(filterResponse: FilterResponse) {
        // Dynamically add UI elements for each filter category
        filterResponse.filters.layout.forEach { layoutOption ->
            val checkBox = CheckBox(this).apply {
                text = layoutOption
            }
            binding.layoutContainer.addView(checkBox)
        }

        filterResponse.filters.availability.forEach { availabilityOption ->
            val switch = Switch(this).apply {
                text = availabilityOption
            }
            binding.availabilityContainer.addView(switch)
        }

        filterResponse.filters.productType.forEach { productTypeOption ->
            val radioButton = RadioButton(this).apply {
                text = productTypeOption
            }
            binding.productTypeRadioGroup.addView(radioButton)
        }

        filterResponse.filters.brand.forEach { brandOption ->
            val checkBox = CheckBox(this).apply {
                text = brandOption
            }
            binding.brandContainer.addView(checkBox)
        }

        // Dynamically add color buttons with single selection behavior
        filterResponse.filters.color.forEach { colorHex ->
            //setupColorSelector(colorHex)
            Log.d("TAG", "displayFilters: $colorHex")

            val colorView = TextView(this)
            colorView.setBackgroundColor(Color.parseColor(colorHex))
            colorView.setOnClickListener {
                selectedColor = colorHex
                // Update UI to visually indicate selection

                binding.colorContainer.addView(colorView)
            }

        }


        // Dynamically add size tags with single selection behavior
        filterResponse.filters.size.forEach { sizeOption ->
            val textView = createSizeTextView(sizeOption)
            binding.sizeContainer.addView(textView)
        }

        filterResponse.filters.tags.forEach { tagOption ->
            val tagTextView = TextView(this).apply {
                text = tagOption
                setPadding(16, 8, 16, 8)
                setBackgroundResource(R.drawable.tag_background)
            }
            binding.tagsContainer.addView(tagTextView)
        }
    }

    private fun getSelectedFilters(): String {
        val selectedLayouts = mutableListOf<String>()
        val selectedAvailability = mutableListOf<String>()
        val selectedProductTypes: String? = null
        val selectedBrands = mutableListOf<String>()
        val selectedColors = mutableListOf<String>()
        val selectedSizes = mutableListOf<String>()

        for (i in 0 until binding.layoutContainer.childCount) {
            val checkBox = binding.layoutContainer.getChildAt(i) as CheckBox
            if (checkBox.isChecked) {
                selectedLayouts.add(checkBox.text.toString())
            }
        }

        for (i in 0 until binding.availabilityContainer.childCount) {
            val switch = binding.availabilityContainer.getChildAt(i) as Switch
            if (switch.isChecked) {
                selectedAvailability.add(switch.text.toString())
            }
        }

        val selectedRadioButton =
            findViewById<RadioButton>(binding.productTypeRadioGroup.checkedRadioButtonId)
        val selectedProductType = selectedRadioButton?.text.toString()

        for (i in 0 until binding.brandContainer.childCount) {
            val checkBox = binding.brandContainer.getChildAt(i) as CheckBox
            if (checkBox.isChecked) {
                selectedBrands.add(checkBox.text.toString())
            }
        }

        for (i in 0 until binding.colorContainer.childCount) {
            val button = binding.colorContainer.getChildAt(i) as Button
            if (button.isSelected) {
                selectedColors.add(button.tag.toString())
            }
        }

        for (i in 0 until binding.sizeContainer.childCount) {
            val textView = binding.sizeContainer.getChildAt(i) as TextView
            if (textView.isSelected) {
                selectedSizes.add(textView.text.toString())
            }
        }

        return """
            Selected Filters:
            Layout: ${selectedLayouts.joinToString(", ")}
            Availability: ${selectedAvailability.joinToString(", ")}
            Product Type: $selectedProductTypes
            Brands: ${selectedBrands.joinToString(", ")}
            Colors: ${selectedColors.joinToString(", ")}
            Sizes: ${selectedSizes.joinToString(", ")}
        """.trimIndent()
    }

    private fun handleDrawer(binding: ActivityMainBinding) {
        if (!binding.main.isDrawerOpen(GravityCompat.END)) {
            binding.main.openDrawer(GravityCompat.END)
        } else {
            binding.main.closeDrawer(GravityCompat.END)
        }
    }

    private fun dataSetToUi(binding: ActivityMainBinding) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val parentAdapter = ParentAdapter(this, this, this)

        binding.parentRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = parentAdapter
        }

        viewModel.parentItems.observe(this, { parentItems ->
            parentAdapter.submitList(parentItems)
        })

        // Fetch data in the background
        viewModel.fetchParentItems()
    }

    override fun onParentItemClick(parentItem: ParentItem) {

        Toast.makeText(this, "Parent Clicked", Toast.LENGTH_SHORT).show()


    }

    override fun onChildItemClick(childItem: ChildItem) {
        Toast.makeText(this, "Child Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onSubChildItemClick(subChildItem: SubChildItem) {
        Toast.makeText(this, "Sub Child Clicked", Toast.LENGTH_SHORT).show()
    }


}

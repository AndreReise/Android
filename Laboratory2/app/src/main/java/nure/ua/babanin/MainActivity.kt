package nure.ua.babanin

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : ComponentActivity() {

    private lateinit var r_bar : SeekBar;
    private lateinit var r_barText : TextView;

    private lateinit var g_bar : SeekBar;
    private lateinit var g_barText : TextView;

    private lateinit var b_bar : SeekBar;
    private lateinit var b_barText : TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        r_bar = findViewById<SeekBar>(R.id.r_bar)
        r_barText = findViewById(R.id.r_value)

        g_bar = findViewById<SeekBar>(R.id.g_bar)
        g_barText = findViewById(R.id.g_value)

        b_bar = findViewById<SeekBar>(R.id.b_bar)
        b_barText = findViewById(R.id.b_value)

        val colorPanel = findViewById<ConstraintLayout>(R.id.color_plate)

        r_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateColor(colorPanel, r_bar.progress, r_bar.progress, b_bar.progress)

                r_barText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        g_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateColor(colorPanel, r_bar.progress, g_bar.progress, b_bar.progress)

                g_barText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        b_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                updateColor(colorPanel, r_bar.progress, g_bar.progress, b_bar.progress)

                b_barText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateColor(colorPanel: ConstraintLayout, red: Int, green: Int, blue: Int) {
        val color = Color.rgb(red, green, blue)
        colorPanel.setBackgroundColor(color)
    }
}
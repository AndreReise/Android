package nure.ua.babanin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        Log.d("lifecycle", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("lifecycle", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("lifecycle", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("lifecycle", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("lifecycle", "onDestroy")
        super.onDestroy()
    }
}
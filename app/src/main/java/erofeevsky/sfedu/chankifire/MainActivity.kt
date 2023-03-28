package erofeevsky.sfedu.chankifire

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import androidx.appcompat.app.AppCompatActivity
import erofeevsky.sfedu.chankifire.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
    }

    val requestCode = 1

    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    /**
     * A native method that is implemented by the 'chankifire' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String

    companion object {
        // Used to load the 'chankifire' library on application startup.
        init {
            System.loadLibrary("chankifire")
        }
    }
}
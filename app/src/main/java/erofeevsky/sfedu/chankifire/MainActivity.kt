package erofeevsky.sfedu.chankifire

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import erofeevsky.sfedu.chankifire.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {



    private lateinit var file: String
    private lateinit var path: String
    public fun getFile() : String {
        //return "Kotlin => C++"
        return path
    }

    private fun getRealPathFromURI(uri: Uri): String {
        return "/storage/emulated/0/Download/hello_ndk.txt"
    }

    private lateinit var binding: ActivityMainBinding

    private val openLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            try {
                uri?.let {
                    //openFile(it); println("URI ------------------------------>$it")
                    path = getRealPathFromURI(it)
                    println("Path ------------------------------>${file}")
                    binding.sampleText.text = stringFromJNI()
                }
            } catch (e: Exception) {
                showError(R.string.cant_open_file)
            }
        }

    private fun openFile(uri: Uri) {
        val data = contentResolver.openInputStream(uri)?.use {
            String(it.readBytes())
        } ?: throw IllegalStateException("Can't open input stream")
        file = data
        binding.sampleText.text = data
    }


    private fun showError(@StringRes res: Int) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            openButton.setOnClickListener { openLauncher.launch(arrayOf("text/plain")) }
        }
        // Example of a call to a native method
        //binding.sampleText.text = stringFromJNI()
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
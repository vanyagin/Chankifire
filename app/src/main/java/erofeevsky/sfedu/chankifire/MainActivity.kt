package erofeevsky.sfedu.chankifire

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import erofeevsky.sfedu.chankifire.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private val PERMISSION_STORAGE = 101

    private lateinit var path: String
    private lateinit var fileName: String

    public fun getFile() : String {
        //return "Kotlin => C++"
        return path
    }

    private lateinit var binding: ActivityMainBinding

    private val openLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            try {
                uri?.let {
                    contentResolver.query(it, null, null, null, null)?.use { cursor ->
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        cursor.moveToFirst()
                        fileName = cursor.getString(nameIndex)
                        cursor.close()
                    }
                    copyToInternalStorage(it)
                    println("Path ------------------------------>${path}")
                    binding.sampleText.text = stringFromJNI()
                }
            } catch (e: Exception) {
                showError(R.string.cant_open_file)
            }
        }


    private fun copyToInternalStorage(uri: Uri) {
        val file = File(filesDir.path, fileName)
        val output = FileOutputStream(file)
        contentResolver.openInputStream(uri)?.copyTo(output)
            ?: throw IllegalStateException("Can't open input stream")
        path = file.absolutePath
        output.close()
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
            openButton.setOnClickListener {
                if (!PermissionUtils.hasPermissions(this@MainActivity)) {
                    PermissionUtils.requestPermissions(this@MainActivity, PERMISSION_STORAGE)
                } else {
                    openLauncher.launch(arrayOf("text/plain"))
                }
            }
        }
        // Example of a call to a native method
        //binding.sampleText.text = stringFromJNI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
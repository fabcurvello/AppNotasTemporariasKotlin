package br.com.fabriciocurvello.appnotastemporariaskotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var etNotaEntrada: EditText
    private lateinit var btSalvar: Button
    private lateinit var btCarregar: Button
    private lateinit var tvNotaSaida: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNotaEntrada = findViewById(R.id.et_nota_entrada)
        btSalvar = findViewById(R.id.bt_salvar)
        btCarregar = findViewById(R.id.bt_carregar)
        tvNotaSaida = findViewById(R.id.tv_nota_saida)

        btSalvar.setOnClickListener { saveNote() }
        btCarregar.setOnClickListener { loadNote() }
    }

    private fun saveNote() {
        val note = etNotaEntrada.text.toString()
        val cacheDir = cacheDir
        val file = File(cacheDir, "temp_note.txt")

        try {
            FileOutputStream(file).use { fos ->
                fos.write(note.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadNote() {
        val cacheDir = cacheDir
        val file = File(cacheDir, "temp_note.txt")

        if (file.exists()) {
            try {
                FileInputStream(file).use { fis ->
                    val lenght = file.length().toInt()
                    val bytes = ByteArray(lenght)
                    fis.read(bytes)
                    val note = String(bytes)
                    tvNotaSaida.text = note
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            tvNotaSaida.text = "Nenhuma nota encontrada."
        }
    }

}
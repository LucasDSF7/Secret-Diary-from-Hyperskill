package org.hyperskill.secretdiary
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
// import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import android.app.AlertDialog
import android.content.Context

class MainActivity : AppCompatActivity() {
    private lateinit var etNewWriting: EditText
    private lateinit var btnSave: Button
    private lateinit var btnUndo: Button
    private lateinit var tvDiary: TextView
    private lateinit var diaryNotes: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = getSharedPreferences("PREF_DIARY", Context.MODE_PRIVATE)

        etNewWriting = findViewById(R.id.etNewWriting)
        btnSave = findViewById(R.id.btnSave)
        btnUndo = findViewById(R.id.btnUndo)
        tvDiary = findViewById(R.id.tvDiary)
        tvDiary.text = sharedPref.getString("KEY_DIARY_TEXT", "")
        diaryNotes = if (tvDiary.text.toString().isBlank()) mutableListOf()
        else tvDiary.text.toString().split("\n\n").toMutableList()

        btnSave.setOnClickListener {
            saveDiaryContentFromListener()
        }
        btnUndo.setOnClickListener {
            undoDiaryContentFromListener()
        }
    }

    private fun saveDiaryContentFromListener() {
        if (etNewWriting.text.isNotBlank()) {
            diaryNotes.add(index = 0, getCurrentLocalDateTime() + etNewWriting.text.toString())
            etNewWriting.text.clear()
            setTvDiaryText()
        }
        else {
            Toast.makeText(this, "Empty or blank input cannot be saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun undoDiaryContentFromListener(){
        AlertDialog.Builder(this)
            .setTitle("Remove last note")
            .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
            .setPositiveButton(R.string.Yes) {
                _, _ ->
                try {
                    diaryNotes.removeAt(0)
                    setTvDiaryText()
                } catch (_: IndexOutOfBoundsException) {}
            }
            .setNegativeButton(R.string.No, null)
            .show()

    }

    private fun setTvDiaryText() {
        val text = diaryNotes.joinToString("\n\n")
        tvDiary.text = text
        val sharedPref = getSharedPreferences("PREF_DIARY", Context.MODE_PRIVATE)
        sharedPref.edit().putString("KEY_DIARY_TEXT", text).apply()
    }

    private fun getCurrentLocalDateTime(): String {
        val instant = Clock.System.now()
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.date.toString() + " " + localDateTime.time.toString().split(".")[0] + "\n"
    }
}
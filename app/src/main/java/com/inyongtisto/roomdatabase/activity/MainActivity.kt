package com.inyongtisto.roomdatabase.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.inyongtisto.roomdatabase.R
import com.inyongtisto.roomdatabase.adapter.AdapterNote
import com.inyongtisto.roomdatabase.model.NoteModel
import com.inyongtisto.roomdatabase.room.MyDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase
    //Melakukan pemanggilan kelas super onCreate untuk menyelesaikan pembuatan aktivitas seperti
    // hierarki tampilan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Mengatur tata letak antarmuka pengguna untuk aktivitas ini
        //file tata letak didefinisikan dalam file projek res/layout/main_activity.xml
        setContentView(R.layout.activity_main)

        myDb = MyDatabase.getInstance(this) // Melakukan pemanggilan database.

        displayNote()
        mainButton()
    }

    private fun mainButton() {
        btn_delete.setOnClickListener {
            // Pada saat aplikasi dijalankan maka akan dimulai dari kelas membuat catatan.
            startActivity(Intent(this, CreateNoteActivity::class.java)) //
        }
    }

    private fun displayNote() {
        val listNote = myDb.daoNote().getAll()
        rv_note.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        rv_note.adapter = AdapterNote(listNote, object : AdapterNote.Listeners {
            override fun onClicked(note: NoteModel) {
                val intent = Intent(this@MainActivity, DetailNoteActivity::class.java)
                intent.putExtra("extra", "" + note.id)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        displayNote()
        super.onResume()
    }
}
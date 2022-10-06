package com.inyongtisto.roomdatabase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.inyongtisto.roomdatabase.R
import com.inyongtisto.roomdatabase.model.NoteModel
import com.inyongtisto.roomdatabase.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_note.*
// kelas activity untuk membuat note(catatan)
class CreateNoteActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase
    //Melakukan pemanggilan kelas super onCreate untuk menyelesaikan pembuatan aktivitas seperti
    // hierarki tampilan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Mengatur tata letak antarmuka pengguna untuk aktivitas ini
        //file tata letak didefinisikan dalam file projek res/layout/main_activity.xml
        setContentView(R.layout.activity_create_note)
        myDb = MyDatabase.getInstance(this) // Memanggil database

        mainButton()
    }

    private fun mainButton() {
        btn_delete.setOnClickListener {
            insert()
        }
    }

    private fun insert() {
        if (edt_title.text.isEmpty()) {
            edt_title.error = "field is required"
            edt_title.requestFocus()
            return
        }

        val note = NoteModel() //Membuat catatan baru
        note.title = edt_title.text.toString()
        note.description = edt_title.text.toString()

        // Menambahkan data
        CompositeDisposable().add(Observable.fromCallable {
            myDb.daoNote().insert(note) // Menambahkan catatan baru
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                onBackPressed()
            })
    }
}
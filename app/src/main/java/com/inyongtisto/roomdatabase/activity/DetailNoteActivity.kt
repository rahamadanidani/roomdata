package id.rahma.roomdatabase.activity

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
import kotlinx.android.synthetic.main.activity_detail_note.*
import kotlinx.android.synthetic.main.activity_detail_note.btn_back
import kotlinx.android.synthetic.main.activity_detail_note.btn_delete
import kotlinx.android.synthetic.main.activity_detail_note.edt_desc
import kotlinx.android.synthetic.main.activity_detail_note.edt_title

class DetailNoteActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase
    lateinit var note: NoteModel
//Melakukan pemanggilan kelas super onCreate untuk menyelesaikan pembuatan aktivitas seperti
// hierarki tampilan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //Mengatur tata letak antarmuka pengguna untuk aktivitas ini
    //file tata letak didefinisikan dalam file projek res/layout/main_activity.xml
        setContentView(R.layout.activity_detail_note)
        myDb = MyDatabase.getInstance(this) // Melakukan pemanggilan database

        setupData()
        mainButton()
    }

    private fun setupData(){
        val noteId = intent.getStringExtra("extra").toString()
        note = myDb.daoNote().getNote(noteId) // Mendapatkan catatan dengan id catatan yang dipilih dari mainActivity
        edt_title.setText(note.title)
        edt_desc.setText(note.description)
    }
// digunakan untuk mengklik tombol delete saat kita ingin menghapus data yang telah di buat sebelumnya.
    private fun mainButton(){
        btn_delete.setOnClickListener {
            delete()
        }

        btn_back.setOnClickListener {
            update()
        }
    }

    private fun update() {
        if (edt_title.text.isEmpty()) {
            edt_title.error = "field is required"
            edt_title.requestFocus()
            return
        }

        note.title = edt_title.text.toString()
        note.description = edt_title.text.toString()

        // Menambahkan data pada tampilan
        CompositeDisposable().add(
            Observable.fromCallable {
            myDb.daoNote().update(note) // Melakukan pengupdate-an atau pembaharuan data.
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("respons", "data update")
                finish()
            })
    }

    private fun delete() {
        // Menambahkan data
        CompositeDisposable().add(
            Observable.fromCallable {
                myDb.daoNote().delete(note) // Menghapus note(catatan) yang telah dibuat.
            }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("respons", "data deleted")
                    Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show()
                    finish()
                })
    }

    override fun onBackPressed() {
        update()
    }
}
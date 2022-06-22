package com.example.uasyakan

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry)

        val modeEdit = intent.hasExtra("kode") && intent.hasExtra("nama") &&
                intent.hasExtra("sks") && intent.hasExtra("sifat")

        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etKdMatkul = findViewById<EditText>(R.id.etKdMatkul)
        val etNmMataKuliah = findViewById<EditText>(R.id.etNmMatkul)
        val etPrStudi = findViewById<EditText>(R.id.etPrStudi)
        val spinsks = findViewById<Spinner>(R.id.spinsks)
        val spingolongan = findViewById<Spinner>(R.id.spingolongan)
        val rdWajib = findViewById<RadioButton>(R.id.rdpilihan)
        val rdPilihan = findViewById<RadioButton>(R.id.rdwajib)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val sks = arrayOf("Tenaga Pengajar","Asisten Ahli", "Lektor", "Lektor Kepala", "Guru Besar")
        val adpsks = ArrayAdapter(
            this@EntryActivity,
            android.R.layout.simple_spinner_dropdown_item, sks

        )
        spinsks.adapter= adpsks

        val golongan = arrayOf("III/a - Penata Muda", "III/b - Penata Muda Tingkat I","III/c - Penata", "III/d - penata Tingkat I", "IV/a - Pembina", "IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda", "IV/d - Pembina Utama Madya", "IV/e - Pembina Utama")
        val adpgolongan = ArrayAdapter(
            this@EntryActivity,
            android.R.layout.simple_spinner_dropdown_item, golongan

        )
        spingolongan.adapter= adpgolongan

        if(modeEdit) {
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val nilaiSks = intent.getStringExtra("sks" )
            val sifat = intent.getStringExtra("sifat")

            etKdMatkul.setText(kode)
            etNmMataKuliah.setText(nama)
            spinsks.setSelection(sks.indexOf(nilaiSks))
            if (sifat == "wajib") rdWajib.isChecked = true else rdPilihan.isChecked = true
        }
        etKdMatkul.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etKdMatkul.text}".isNotEmpty()&&"${etNmMataKuliah.text}".isNotEmpty() &&
                (rdWajib.isChecked || rdPilihan.isChecked )){
                val db= DBHelper(this@EntryActivity)
                db.NIDN= "${etKdMatkul.text}"
                db.nama_dosen = "${etNmMataKuliah.text}"
                db.jabatan= spinsks.selectedItem as String
                db.program_studi = "${etPrStudi.text}"
                db.pendidikan = if(rdWajib.isChecked) "S2" else "S3"
                if (if (!modeEdit)db.simpan() else db.ubah("${etKdMatkul.text}")) {
                    Toast.makeText(
                        this@EntryActivity,
                        "Data Dosen Berhasil Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@EntryActivity,
                        "Data Dosen Gagal Disimpan",
                        Toast.LENGTH_SHORT

                    ).show()


            }else
                Toast.makeText(
                    this@EntryActivity,
                    "Data Dosen Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()

        }
    }
}
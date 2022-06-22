package com.example.uasyakan

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context): SQLiteOpenHelper(context, "campuss",null,1){
    var NIDN=""
    var nama_dosen=""
    var jabatan = ""
    var pendidikan=""
    var golongan_pangkat = ""
    var keahlian = ""
    var program_studi = ""

    private val tabel = "lecturer"
    private var sql= ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel  (
            NIDN char (10) primary key,
            nama_dosen varchar (50) not null,
            Jabatan varchar(15) not null,
            golongan_pangkat varchar(30) not null,
            Pendidikan char(2) not null,
            Keahlian varchar(30) not null,
            Program_studi varchar(50) not null
            )
        """.trimMargin()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        sql = "drop table if exist $tabel"
        db?.execSQL(sql)
    }
    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("NIDN", NIDN)
            put("nama_dosen", nama_dosen)
            put("jabatan", jabatan)
            put("pendidikan", pendidikan)
            put("golongan_pangkat",golongan_pangkat)
            put("keahlian",keahlian)
            put("program_studi",program_studi)
        }
        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L

    }

    fun ubah (kode: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        with(cv){
            put("nama_dosen", nama_dosen)
            put("jabatan",jabatan)
            put("pendidikan",pendidikan)
            put("golongan_pangkat",golongan_pangkat)
            put("keahlian",keahlian)
            put("program_studi",program_studi)
        }
        val cmd = db.update(tabel,cv,"NIDN=?", arrayOf(kode))
        db.close()
        return cmd!=-1
    }
    fun hapus (kode:String): Boolean{
        val db = writableDatabase
        val cmd = db.delete(tabel,"NIDN=?", arrayOf(kode))
        return  cmd !=-1
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }
}

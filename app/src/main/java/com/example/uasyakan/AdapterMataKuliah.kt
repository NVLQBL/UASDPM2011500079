package com.example.uasyakan

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class AdapterMataKuliah (
    private val getContext: Context,
    private val customListItem: ArrayList<MataKuliah>
): ArrayAdapter<MataKuliah>(getContext,0,customListItem) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var ListLayout = convertView
        val holder: ViewHolder
        if (ListLayout ==null) {
            val inflatelist = (getContext as Activity).layoutInflater
            ListLayout=inflatelist.inflate(R.layout.item, parent,false )
            holder = ViewHolder()
            with(holder){
                tvNmMatkul = ListLayout.findViewById(R.id.tvNmMatkul)
                tvKdMatkul = ListLayout.findViewById(R.id.tvKdMatkul)
                tvPrStudi = ListLayout.findViewById(R.id.tvPrStudi)
                btnEdit = ListLayout.findViewById(R.id.btnEdit)
                btnHapus = ListLayout.findViewById(R.id.btnHapus)
            }
            ListLayout.tag = holder
        }
        else
            holder = ListLayout.tag as ViewHolder
        val ListItem: MataKuliah = customListItem[position]
        holder.tvNmMatkul!!.text = ListItem.nmMatkul
        holder.tvKdMatkul!!.text = ListItem.kdMatkul
        holder.tvPrStudi!!.text = ListItem.PrStudi

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntryActivity::class.java)
            i.putExtra("kode", ListItem.kdMatkul)
            i.putExtra("nama", ListItem.nmMatkul)
            i.putExtra("prodi", ListItem.PrStudi)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener{
            val db = DBHelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvKdMatkul!!.text
            val nama = holder.tvNmMatkul!!.text
            val prodi = holder.tvPrStudi!!.text

            with (alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""Apakah Anda Yakin Akan Menghapus Data Ini??
                    |
                    |$nama
                    |[$kode-$prodi]
                    |
                """.trimMargin())
                setPositiveButton("Ya") {_, _->
                    if(db.hapus("$kode"))
                        Toast .makeText(
                            context,
                            "Data dosen berhasil dihapus",
                            Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(
                            context,
                            "Data dosen Gagal Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()


                }
                setNegativeButton("Tidak",null)
                create().show()
            }
        }

        return ListLayout!!
    }
}




class ViewHolder {
    internal var tvNmMatkul: TextView? = null
    internal var tvKdMatkul: TextView? = null
    internal var tvPrStudi: TextView? = null
    internal var btnEdit: ImageButton? = null
    internal var btnHapus: ImageButton? = null

}


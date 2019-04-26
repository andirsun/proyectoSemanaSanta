package com.example.datastorage.Controladores

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Modelos.User
import com.example.datastorage.R
import com.example.datastorage.Servicios.UserDBServices
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var registerService : UserDBServices


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val name =  intent.getStringExtra("name")
        Toast.makeText(this, "Nombre" + name , Toast.LENGTH_SHORT).show()
        llenarPerfil(name)


    }
    fun llenarPerfil(nombre: String)
    {

        //this.registerService.saveUser(user)
        val lista: List<User>?  = UserDBServices(this).buscarUsuario(nombre)
        d("tag", lista.toString())
        nameProfile.setText(lista!![0].name)
        ageProfile.setText("Edad:"+lista!![0].age)
        emailProfile.setText(lista!![0].email)
        imageProfile.setImageURI(Uri.parse(lista!![0].photo))



        //Toast.makeText(this, tempo,  Toast.LENGTH_SHORT).show()

    }
}

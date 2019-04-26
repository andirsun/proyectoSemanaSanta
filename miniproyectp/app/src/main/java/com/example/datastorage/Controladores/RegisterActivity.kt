package com.example.datastorage.Controladores

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Modelos.User
import com.example.datastorage.R
import com.example.datastorage.Servicios.LoginServices
import com.example.datastorage.Servicios.UserDBServices
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerService : UserDBServices
    private lateinit var ruta : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerService= UserDBServices(this)
    }
    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
    }
    fun seleccionarImagen(view: View){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
            val permisos = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permisos,PERMISSION_CODE)
        }
        else{
            cargarImagen()
        }
    }
    fun cargarImagen(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/"
        startActivityForResult(intent, IMAGE_PICK_CODE)


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE ->
                if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    cargarImagen()
                }
                else{
                    Toast.makeText(this,"No se genero el permiso",Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode== Activity.RESULT_OK && requestCode== IMAGE_PICK_CODE){
            val path= arrayOf(MediaStore.Images.Media.DATA)
            val cursor  = this.contentResolver.query(data?.data,path,null,null,null)
            assert(cursor!=null)
            cursor.moveToFirst()
            ruta=cursor.getString(cursor.getColumnIndex(path[0]))
            cursor.close()
            selectImage.setImageURI(Uri.parse(ruta))
        }
    }
    fun profile(view: View)
    {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
    fun ingresarUsuario(view: View)
    {
        val nombre= findViewById<TextView>(R.id.nombre);
        val password=findViewById<TextView>(R.id.contraseña);
        val email= findViewById<TextView>(R.id.correo);
        val edad=findViewById<TextView>(R.id.edad);
        val user = User(null, nombre.text.toString(), email.text.toString(),edad.text.toString(), password.text.toString(),ruta)

        //this.registerService.saveUser(user)
        UserDBServices(this).saveUser(user)

        //Toast.makeText(this, "Datos incorrectos",  Toast.LENGTH_SHORT).show()
    }

}

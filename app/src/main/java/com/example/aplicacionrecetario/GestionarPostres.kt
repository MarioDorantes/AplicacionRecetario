package com.example.aplicacionrecetario

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class GestionarPostres : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_postres)

        val txtNombre = findViewById<EditText>(R.id.txtNombrePostre)
        val txtIngredientes = findViewById<EditText>(R.id.txtIngredientesPostre)
        val txtProcedimiento = findViewById<EditText>(R.id.txtProcedimientoPostre)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarPostre)
        val btnModificar = findViewById<Button>(R.id.btnModificarPostre)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarPostre)
        val btnConsultarPostres = findViewById<Button>(R.id.btnConsultarPostres)
        val btnConsultarPorNombre = findViewById<Button>(R.id.btnConsultarPostrePorNombre)
        val txtTodosLosPostres = findViewById<TextView>(R.id.txtTodosLosPostres)

        btnConsultarPostres.setOnClickListener {

            txtTodosLosPostres.text = ""

            val admin = SQL(this, "administracion2", null, 1)
            val bd = admin.writableDatabase

            try{
                val fila = bd.rawQuery("select nombrePostre from postres", null)

                if(fila.moveToFirst()){
                    do{
                        txtTodosLosPostres.append("Nombres de los postres: " + fila.getString(0).toString() + "\n")

                    } while(fila.moveToNext())

                }else{
                    Toast.makeText(this, "No hay postres...", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            } finally {
                bd.close()
            }
        }

        btnConsultarPorNombre.setOnClickListener {

            val admin = SQL(this, "administracion2", null, 1)
            val bd = admin.writableDatabase

            try{
                if(!txtNombre.text.toString().isEmpty()){
                    val fila = bd.rawQuery("select ingredientes, procedimiento from postres where nombrePostre = '${txtNombre.text.toString()}'", null)

                    if(fila.moveToFirst()){
                        txtIngredientes.setText(fila.getString(0))
                        txtProcedimiento.setText(fila.getString(1))
                    }else{
                        Toast.makeText(this, "No existe el postre ingresado", Toast.LENGTH_SHORT).show()

                    }
                }
            } catch(e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            } finally{
                bd.close()
            }
        }

        btnRegistrar.setOnClickListener(){

            try{
                if(!(txtNombre.text.toString().isEmpty() && txtIngredientes.text.toString().isEmpty() && txtProcedimiento.text.toString().isEmpty())){
                    val admin = SQL(this, "administracion2", null, 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    registro.put("nombrePostre", txtNombre.getText().toString())
                    registro.put("ingredientes", txtIngredientes.getText().toString())
                    registro.put("procedimiento", txtProcedimiento.getText().toString())

                    bd.insert("postres", null, registro)
                    bd.close()

                    txtNombre.setText("")
                    txtIngredientes.setText("")
                    txtProcedimiento.setText("")

                    Toast.makeText(this, "Postre registrado exitosamente", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Favor de no dejar campos vacíos!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }

        }

        btnModificar.setOnClickListener{
            val admin = SQL(this, "administracion2", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("ingredientes", txtIngredientes.text.toString())
            registro.put("procedimiento", txtProcedimiento.text.toString())

            try{
                val cantidad = bd.update("postres", registro, "nombrePostre = '${txtNombre.text.toString()}'", null)

                txtNombre.setText("")
                txtIngredientes.setText("")
                txtProcedimiento.setText("")

                if(cantidad == 1){
                    Toast.makeText(this, "Postre actualizado exitosamente", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Actualización NO exitosa", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            } finally{
                bd.close()
            }
        }

        btnEliminar.setOnClickListener {
            val admin = SQL(this, "administracion2", null, 1)
            val bd = admin.writableDatabase

            try{
                val cant = bd.delete("postres", "nombrePostre = '${txtNombre.text.toString()}'", null)

                txtNombre.setText("")
                txtIngredientes.setText("")
                txtProcedimiento.setText("")

                if(cant == 1){
                    Toast.makeText(this, "Postre eliminado exitosamente", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Eliminación NO exitosa", Toast.LENGTH_SHORT).show()
                }
            } catch(e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            } finally{
                bd.close()
            }
        }


    }
}
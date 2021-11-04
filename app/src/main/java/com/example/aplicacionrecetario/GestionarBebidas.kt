package com.example.aplicacionrecetario

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.app.AlertDialog

class GestionarBebidas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_bebidas)

        val txtNombre = findViewById<EditText>(R.id.txtNombreBebida)
        val txtIngredientes = findViewById<EditText>(R.id.txtIngredientesBebida)
        val txtProcedimiento = findViewById<EditText>(R.id.txtProcedimientoBebida)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarBebida)
        val btnModificar = findViewById<Button>(R.id.btnModificarBebida)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarBebida)
        val btnConsultar = findViewById<Button>(R.id.btnConsultarbebidas)
        val btnConsultarPorNombre = findViewById<Button>(R.id.btnConsultarPorNombre)
        val txtTodasBebidas = findViewById<TextView>(R.id.txtTodasLasBebidas)

        btnRegistrar.setOnClickListener(){

            try{
                if(!(txtNombre.text.toString().isEmpty() && txtIngredientes.text.toString().isEmpty() && txtProcedimiento.text.toString().isEmpty())){
                    val admin = SQL(this, "administracion", null, 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    registro.put("nombreBebida", txtNombre.getText().toString())
                    registro.put("ingredientes", txtIngredientes.getText().toString())
                    registro.put("procedimiento", txtProcedimiento.getText().toString())

                    bd.insert("bebidas", null, registro)
                    bd.close()

                    txtNombre.setText("")
                    txtIngredientes.setText("")
                    txtProcedimiento.setText("")

                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Favor de no dejar campos vacíos!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }

        }

        btnConsultar.setOnClickListener {

            txtTodasBebidas.text = ""

            val admin = SQL(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            try{
                val fila = bd.rawQuery("select nombreBebida  from bebidas", null)

                if(fila.moveToFirst()){
                    do{
                        txtTodasBebidas.append("Nombre de la bebida: " + fila.getString(0).toString() + "\n")

                    } while(fila.moveToNext())

                }else{
                    Toast.makeText(this, "No hay bebidas...", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            } finally {
                bd.close()
            }
        }

        btnConsultarPorNombre.setOnClickListener {

            val admin = SQL(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            try{
                if(!txtNombre.text.toString().isEmpty()){
                    val fila = bd.rawQuery("select ingredientes, procedimiento  from bebidas where nombreBebida = '${txtNombre.text.toString()}'", null)

                    if(fila.moveToFirst()){
                        txtIngredientes.setText(fila.getString(0))
                        txtProcedimiento.setText(fila.getString(1))
                    }else{
                        Toast.makeText(this, "No existe la bebida", Toast.LENGTH_SHORT).show()

                    }
                }
            } catch(e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            } finally{
                bd.close()
            }
        }

        btnModificar.setOnClickListener{
            val admin = SQL(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("ingredientes", txtIngredientes.text.toString())
            registro.put("procedimiento", txtProcedimiento.text.toString())

            try{
                val cantidad = bd.update("bebidas", registro, "nombreBebida = '${txtNombre.text.toString()}'", null)

                txtNombre.setText("")
                txtIngredientes.setText("")
                txtProcedimiento.setText("")

                if(cantidad == 1){
                    Toast.makeText(this, "Bebida actualizada exitosamente", Toast.LENGTH_SHORT).show()
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

            val mensaje = AlertDialog.Builder(this)
            mensaje.setTitle("Mensaje de confirmación")
            mensaje.setMessage("¿Desea eliminar este registro?")

            mensaje.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT). show()

                val admin = SQL(this, "administracion", null, 1)
                val bd = admin.writableDatabase

                try{
                    val cant = bd.delete("bebidas", "nombreBebida = '${txtNombre.text.toString()}'", null)

                    txtNombre.setText("")
                    txtIngredientes.setText("")
                    txtProcedimiento.setText("")

                    if(cant == 1){
                        Toast.makeText(this, "Eliminación exitosa", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Eliminación NO exitosa", Toast.LENGTH_SHORT).show()
                    }
                } catch(e: Exception){
                    Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
                } finally{
                    bd.close()
                }

            }

            mensaje.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }

            mensaje.show()

        }



    }
}
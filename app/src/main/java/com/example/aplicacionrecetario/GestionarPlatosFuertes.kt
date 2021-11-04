package com.example.aplicacionrecetario

import android.app.AlertDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class GestionarPlatosFuertes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_platos_fuertes)

        val txtNombre = findViewById<EditText>(R.id.txtNombrePlatoFuerte)
        val txtIngredientes = findViewById<EditText>(R.id.txtIngredientesPlatoFuerte)
        val txtProcedimiento = findViewById<EditText>(R.id.txtProcedimientoPlatoFuerte)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarPlatoFuerte)
        val btnModificar = findViewById<Button>(R.id.btnModificarPlatoFuerte)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarPlatoFuerte)
        val btnConsultarPlatosFuertes = findViewById<Button>(R.id.btnConsultarPlatosFuertes)
        val btnConsultarPorNombre = findViewById<Button>(R.id.btnConsultarPlatoFPorNombre)
        val txtTodosLosPlatosF = findViewById<TextView>(R.id.txtTodosLosPlatosFuertes)

        btnConsultarPlatosFuertes.setOnClickListener {

            txtTodosLosPlatosF.text = ""

            val admin = SQL(this, "administracion3", null, 1)
            val bd = admin.writableDatabase

            try{
                val fila = bd.rawQuery("select nombrePlatoFuerte from platosFuertes", null)

                if(fila.moveToFirst()){
                    do{
                        txtTodosLosPlatosF.append("Nombres de los platos fuertes: " + fila.getString(0).toString() + "\n")

                    } while(fila.moveToNext())

                }else{
                    Toast.makeText(this, "No hay platos fuertes...", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            } finally {
                bd.close()
            }
        }

        btnConsultarPorNombre.setOnClickListener {

            val admin = SQL(this, "administracion3", null, 1)
            val bd = admin.writableDatabase

            try{
                if(!txtNombre.text.toString().isEmpty()){
                    val fila = bd.rawQuery("select ingredientes, procedimiento from platosFuertes where nombrePlatoFuerte = '${txtNombre.text.toString()}'", null)

                    if(fila.moveToFirst()){
                        txtIngredientes.setText(fila.getString(0))
                        txtProcedimiento.setText(fila.getString(1))
                    }else{
                        Toast.makeText(this, "No existe el plato fuerte ingresado", Toast.LENGTH_SHORT).show()

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
                    val admin = SQL(this, "administracion3", null, 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    registro.put("nombrePlatoFuerte", txtNombre.getText().toString())
                    registro.put("ingredientes", txtIngredientes.getText().toString())
                    registro.put("procedimiento", txtProcedimiento.getText().toString())

                    bd.insert("platosFuertes", null, registro)
                    bd.close()

                    txtNombre.setText("")
                    txtIngredientes.setText("")
                    txtProcedimiento.setText("")

                    Toast.makeText(this, "Plato fuerte registrado exitosamente", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Favor de no dejar campos vacíos!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this, "Error: " + e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }
        }

        btnModificar.setOnClickListener{
            val admin = SQL(this, "administracion3", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("ingredientes", txtIngredientes.text.toString())
            registro.put("procedimiento", txtProcedimiento.text.toString())

            try{
                val cantidad = bd.update("platosFuertes", registro, "nombrePlatoFuerte = '${txtNombre.text.toString()}'", null)

                txtNombre.setText("")
                txtIngredientes.setText("")
                txtProcedimiento.setText("")

                if(cantidad == 1){
                    Toast.makeText(this, "Plato Fuerte actualizado exitosamente", Toast.LENGTH_SHORT).show()
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

                val admin = SQL(this, "administracion3", null, 1)
                val bd = admin.writableDatabase

                try{
                    val cant = bd.delete("platosFuertes", "nombrePlatoFuerte = '${txtNombre.text.toString()}'", null)

                    txtNombre.setText("")
                    txtIngredientes.setText("")
                    txtProcedimiento.setText("")

                    if(cant == 1){
                        Toast.makeText(this, "Plato Fuerte eliminado exitosamente", Toast.LENGTH_SHORT).show()
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
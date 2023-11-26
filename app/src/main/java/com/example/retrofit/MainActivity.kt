package com.example.retrofit

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var service: Consumirapi
    val editTextProgrammerId: EditText = findViewById(R.id.editTextProgrammerId)
    val editTextFullName: EditText = findViewById(R.id.editTextFullName)
    val editTextNickname: EditText = findViewById(R.id.editTextNickname)
    val editTextAge: EditText = findViewById(R.id.editTextAge)
    val checkBoxIsActive: CheckBox = findViewById(R.id.checkBoxIsActive)

    val btnAddProgrammer: Button = findViewById(R.id.btnAddProgrammer)
    val btnGetProgrammerInfo: Button = findViewById(R.id.btnGetProgrammerInfo)
    val btnUpdateProgrammer: Button = findViewById(R.id.btnUpdateProgrammer)
    val btnDeleteProgrammer: Button = findViewById(R.id.btnDeleteProgrammer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val retrofit = Retrofit.Builder()
            .baseUrl("https://655e87bc879575426b439d7e.mockapi.io/api/v1/programmers/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Consumirapi::class.java)

        btnAddProgrammer.setOnClickListener {
            val fullName = editTextFullName.text.toString()
            val nickname = editTextNickname.text.toString()
            val age = editTextAge.text.toString().toIntOrNull() ?: 0
            val isActive = checkBoxIsActive.isChecked

            val newProgrammer = jsonplace(fullName, nickname, age, isActive)

            CoroutineScope(Dispatchers.IO).launch {
                addProgrammer(newProgrammer)
            }
        }

        btnGetProgrammerInfo.setOnClickListener {
            val programmerId = editTextProgrammerId.text.toString()
            if (programmerId.isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    getProgrammerById(programmerId.toInt())
                }
            } else {
                showToast("Ingrese un ID de programador válido")
            }
        }

        btnUpdateProgrammer.setOnClickListener {
            val programmerId = editTextProgrammerId.text.toString()
            if (programmerId.isNotBlank()) {
                val fullName = editTextFullName.text.toString()
                val nickname = editTextNickname.text.toString()
                val age = editTextAge.text.toString().toIntOrNull() ?: 0
                val isActive = checkBoxIsActive.isChecked

                val updatedProgrammer = jsonplace( fullName, nickname, age, isActive,programmerId.toInt(),)

                CoroutineScope(Dispatchers.IO).launch {
                    updateProgrammer(updatedProgrammer)
                }
            } else {
                showToast("Ingrese un ID de programador válido")
            }
        }

        btnDeleteProgrammer.setOnClickListener {
            val programmerId = editTextProgrammerId.text.toString()
            if (programmerId.isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    deleteProgrammer(programmerId.toInt())
                }
            } else {
                showToast("Ingrese un ID de programador válido")
            }
        }
    }
    private fun addProgrammer(programmer: jsonplace) {
        val call = service.addProgrammer(programmer)
        call.enqueue(object : Callback<jsonplace> {
            override fun onResponse(call: Call<jsonplace>, response: Response<jsonplace>) {
                if (response.isSuccessful) {
                    val addedProgrammer = response.body()
                    // Manejar el programador agregado
                    showToast("Programador agregado: ${addedProgrammer?.fullname}")
                } else {
                    showToast("Error al agregar el programador")
                }
            }

            override fun onFailure(call: Call<jsonplace>, t: Throwable) {
                showToast("Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun getProgrammerById(programmerId: Int) {
        val call = service.getProgrammer(programmerId)
        call.enqueue(object : Callback<jsonplace> {
            override fun onResponse(call: Call<jsonplace>, response: Response<jsonplace>) {
                if (response.isSuccessful) {
                    val programmer = response.body()
                    if (programmer != null) {
                        // Mostrar los datos del programador en los EditText correspondientes
                        editTextFullName.setText(programmer.fullname)
                        editTextNickname.setText(programmer.nickname)
                        editTextAge.setText(programmer.age.toString())
                        checkBoxIsActive.isChecked = programmer.isActive
                    } else {
                        showToast("Programador no encontrado")
                    }
                } else {
                    showToast("Error al obtener el programador")
                }
            }

            override fun onFailure(call: Call<jsonplace>, t: Throwable) {
                showToast("Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun updateProgrammer(programmer: jsonplace) {
        val call = service.updateProgrammer(programmer.id, programmer)
        call.enqueue(object : Callback<jsonplace> {
            override fun onResponse(call: Call<jsonplace>, response: Response<jsonplace>) {
                if (response.isSuccessful) {
                    val updatedProgrammer = response.body()
                    showToast("Programador actualizado: ${updatedProgrammer?.fullname}")
                } else {
                    showToast("Error al actualizar el programador")
                }
            }

            override fun onFailure(call: Call<jsonplace>, t: Throwable) {
                showToast("Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun deleteProgrammer(programmerId: Int) {
        val call = service.deleteProgrammer(programmerId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Programador eliminado")
                    // Limpiar los campos o realizar alguna acción adicional después de eliminar
                } else {
                    showToast("Error al eliminar el programador")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

}
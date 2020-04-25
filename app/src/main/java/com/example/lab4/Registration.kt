package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class Registration: AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var username: EditText
    private val auth by lazy  { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        email =  findViewById(R.id.email)
        password =  findViewById(R.id.password)
        username = findViewById(R.id.username)
        val reg = findViewById<Button>(R.id.signUp)
        val text = findViewById<TextView>(R.id.text)
        text.text = "<-    Sign Up"


        text.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

        reg.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                signUp(email.getText().toString(),  password.getText().toString())
                val user = hashMapOf(
                    "email" to email.getText().toString(),
                    "username" to username.getText().toString()
                    )
                database
                    .collection("users").add(user)
            }
        })
    }

    private fun signUp(
        email: String,
        password: String
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
//                    uid = task.getResult()?.user?.uid.toString()
//                    database
//                        .collection("users")
//                        .whereEqualTo("email" , email)
//                        .get()
//                        .addSnapshotListener{ documents ->
////                            val email = ArrayList<String>()
////                            for(doc in value!!){
////                                doc.getString("name")?.let{
////                                    users.set
////                                }
////                            }
//                            database.collection("users").set("uid" to uid)
//                        }

                    val intent = Intent(this, MainActivity::class.java);
                    startActivity(intent)

                }
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }
}
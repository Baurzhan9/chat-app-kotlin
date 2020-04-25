package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore


class FriendsActivity: AppCompatActivity() {

//    val timestamp = ServerValue.TIMESTAMP as com.google.firebase.Timestamp

    private lateinit var listView: ListView
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        val text = findViewById<TextView>(R.id.text)
        text.text = "<-    Select user"
        var result = ArrayList<UserDto>()
        var mListView = findViewById<ListView>(R.id.userlist)

        text.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java);
            startActivity(intent)
        }

        database.collection("users").orderBy("uid")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val user = querySnapshot?.documents?.map {

                    it.toObject(User::class.java)

                }

                user?.let {
                    var y = 0
                    for (i in it) {
                        if (user[y]?.email.toString() != auth.currentUser?.email.toString()) {
                            var us = UserDto(
                                user[y]?.username.toString(),
                                user[y]?.email.toString()
                            )
                            result.add(us)
//                            Log.d("taaaag", result.toString())
                            var arrayAdapter = UserListAdapter(this, result)
                            mListView?.adapter = arrayAdapter

                            y += 1
                        } else {
                            y += 1
                        }
                    }
                }

                    mListView.setOnItemClickListener { parent, view, position, id ->
                        val intent = Intent(this, MessageActivity::class.java)

                        val element = mListView.getItemAtPosition(position) as UserDto
                        intent.putExtra("email", element.email)
                        intent.putExtra("username", element.username)
                        intent.putExtra("id", user?.get(position)?.uid)
//                        val participantIds = arrayOf(auth.currentUser?.uid, user?.get(position)?.uid)
//                        val participants = arrayOf(auth.currentUser, user?.get(position))

//                        intent.putExtra("participantIds",participantIds)
//                        intent.putExtra("participants",participants)
                        startActivity(intent);

                    }
                }
            }
    }




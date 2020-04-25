package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatActivity: AppCompatActivity() {

    lateinit var email: TextView
    lateinit var sms: TextView
    lateinit var username1: TextView
    private val auth by lazy  { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val text = findViewById<TextView>(R.id.text)
        val logOut = findViewById<TextView>(R.id.textEnd)
        val cUser = auth.currentUser?.email
        val conversation = findViewById<Button>(R.id.conversation)

        logOut.text = "Log Out"

        conversation.setOnClickListener {
            val intent = Intent(this, FriendsActivity::class.java);
            startActivity(intent)
        }
        logOut.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }
        database.collection("users")
            .whereEqualTo("email", cUser.toString())
            .addSnapshotListener{querySnapshot, firebaseFirestoreException ->
                val user = querySnapshot?.documents?.map{
                    it.toObject(User::class.java)

                }
//                text.text = user?.map {it?.username}.toString()
                text.text = user?.get(0)?.username.toString()

            }

        setupView()
    }

    private fun setupView(){
        var result = ArrayList<Chat>()
        var mListView = findViewById<ListView>(R.id.chatlist)
        database.collection("chats").orderBy("LastMessageTimestamp")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val chat = querySnapshot?.documents?.map {
                    it.toObject(Chat::class.java)
                }
                var y = 0
                if (chat != null) {
                    for (i in chat) {
                        var us = Chat(
                            chat[y]?.id.toString(),
                            "rom@gmail.com",
                            "Roma",
                            chat[y]?.LastMessage.toString(),
                            chat[y]?.LastMessageTimestamp.toString()
                        )

                        result.add(us)
//                        Log.d("taaaag-chat", chat[y]?.id.toString())
//                        Log.d("taaaag-user", user?.get(j)?.uid)

                        var arrayAdapter = ChatListAdapter(this, result)
                        mListView?.adapter = arrayAdapter

                        y += 1
                    }


                }


                mListView.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this, MessageActivity::class.java)

                    val element = mListView.getItemAtPosition(position) as Chat
                    intent.putExtra("email", element.email)
                    intent.putExtra("username", element.username)
                    intent.putExtra("id", chat?.get(position)?.id)

                    startActivity(intent);

                }
            }
    }
}
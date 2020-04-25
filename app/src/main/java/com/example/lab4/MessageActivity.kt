package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.nav.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


private val database by lazy { FirebaseFirestore.getInstance() }
private val auth by lazy  { FirebaseAuth.getInstance() }
//private lateinit var reference: DatabaseReference


class MessageActivity : AppCompatActivity() {

    lateinit var m: EditText
    lateinit var messages_view: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        messages_view = findViewById<RecyclerView>(R.id.messages_view)
        val send = findViewById<Button>(R.id.send)
        m = findViewById(R.id.m)
        val txtEmail = intent.getStringExtra("email")
        val txtUsername = intent.getStringExtra("username")
        val userId = intent.getStringExtra("id")
        val cUser = auth.currentUser
        val text = findViewById<TextView>(R.id.text)
//        reference = FirebaseDatabase.getInstance().getReference("chats")
        text.text = "<-    $txtEmail"
        textEnd.text = "$txtUsername"
        setupView(userId)
        send.setOnClickListener {
            var msg = m.getText().toString()
            if (!msg.equals("")) {
                sendMessage(cUser!!.uid, userId, msg)
            } else {
                Toast.makeText(this, "You cannot send empty message", Toast.LENGTH_SHORT).show()
            }
            m.setText("")
        }

        text.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java);
            startActivity(intent)
        }

    }


    private fun sendMessage(sender: String, receiver: String, message: String) {
        var reference: CollectionReference
        var reference2: CollectionReference
        val currentTimestamp = SimpleDateFormat("yyyyMMdd_HHmss").format(Date())
        val participantIds = listOf(receiver, sender)
        reference = database.collection("chats")
        reference2 = database.collection("messages")
        val hashmap2: HashMap<String, Any> = hashMapOf(
            "LastMessageTimestamp" to currentTimestamp,
            "id" to receiver,
            "LastMessage" to message,
            "participantIds" to participantIds
        )


        val hashmap: HashMap<String, Any> = hashMapOf(
            "chatId" to receiver,
            "sender" to sender,
            "timestamp" to currentTimestamp,
            "message" to message
        )
        reference.document("$receiver").set(hashmap2)
        reference2.add(hashmap)
    }

    private fun setupView(receiver: String) {
//        var reference = database.collection("messages")
//            //.document("$receiver")
        messages_view.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = false
        }
//        reference.get().addOnSuccessListener { querySnapshot ->
//            val mess = querySnapshot?.documents?.map {
//                it.toObject(Message::class.java)
//
//            }
        database.collection("messages").orderBy("timestamp")
//            .whereEqualTo("chatId", receiver).get()
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val msg = querySnapshot?.map {
                    it.toObject(Message::class.java)
                }

                var y = 0


                var result = ArrayList<Message>()
                if (msg != null) {
                    for (i in msg) {
                        if(msg[y]?.chatId.toString().equals(receiver)) {
                            var us = Message(
                                msg[y]?.chatId.toString(),
                                msg[y]?.message.toString(),
                                msg[y]?.sender.toString(),
                                msg[y]?.timestamp.toString()
                            )
                            result.add(us)
                            var arrayAdapter = MessagesAdapter(result)
                            messages_view?.adapter = arrayAdapter

                            Log.d("taaag", us.toString())
                        }
                        y += 1
                        Handler().postDelayed({
                            messages_view.smoothScrollToPosition(100)
                        }, 0)
                    }
                }

            }

//        database.collection("messages")
//            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                val mess = querySnapshot?.documents?.map {
//                    it.toObject(Message::class.java)
//                }
//
//                var y = 0
//
//                var result = ArrayList<Message>()
//                if (mess != null) {
//                    for (i in mess) {
//                       var us = Message(
//                                mess[y]?.chatId.toString(),
//                                mess[y]?.message.toString(),
//                                mess[y]?.sender.toString(),
//                                mess[y]?.timestamp.toString()
//                            )
//                        result.add(us)
//                        var arrayAdapter = MessagesAdapter(result)
//                        messages_view?.adapter = arrayAdapter
//                        y += 1
////                        Log.d("taaag", us.toString())
//                    }
//                }
//            }

    }
}

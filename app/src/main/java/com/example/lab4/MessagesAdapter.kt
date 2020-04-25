package com.example.lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.layout_item_message_in.view.*

class MessagesAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutResource = if (viewType == 0)
            R.layout.layout_item_message_out
        else
            R.layout.layout_item_message_in

        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(message = messages[position])
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {

        return if (auth.currentUser?.uid == messages[position].sender)
            0
        else
            1
    }

    inner class MessageViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bindMessage(message: Message) {
            view.text_view.text = message.message
        }
    }
}
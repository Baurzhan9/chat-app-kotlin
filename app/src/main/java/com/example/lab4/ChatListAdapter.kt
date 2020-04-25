package com.example.lab4

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.getSystemService

class ChatListAdapter(private var activity: Activity, private var item: ArrayList<Chat>): BaseAdapter() {
    private class ViewHolder(row: View?){
        var txtUsername: TextView? = null
        var sms: TextView? = null
        var date: TextView? = null

        init{
            this.txtUsername = row?.findViewById<TextView>(R.id.txtUsername)
            this.sms = row?.findViewById<TextView>(R.id.sms)
            this.date = row?.findViewById<TextView>(R.id.date)

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null){
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.chat_list, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var userDto = item[position]
        viewHolder.sms?.text = userDto.LastMessage
        viewHolder.txtUsername?.text = userDto.username
        viewHolder.date?.text = userDto.LastMessageTimestamp
        return view as View
    }

    override fun getItem(i: Int): Any {
        return item[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return item.size
    }
}
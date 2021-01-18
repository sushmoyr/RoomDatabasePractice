package com.example.roomdatabasepractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasepractice.data.User
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.textView_id.text = currentItem.id.toString()
        holder.itemView.textView_FirstName.text = currentItem.firstName
        holder.itemView.textView_lastName.text = currentItem.lastName
        holder.itemView.textView_age.text = currentItem.age.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(users: List<User>){
        this.userList = users
        notifyDataSetChanged()
    }

}
package com.example.submission1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R

class ListAvatarAdapter (private val listAvatars: ArrayList<Avatars>) : RecyclerView.Adapter<ListAvatarAdapter.ListViewHolder>(){
    class ListViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        var imgPhoto : ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvUsername : TextView = itemView.findViewById(R.id.tv_item_username)
        var tvName : TextView = itemView.findViewById(R.id.tv_item_name)
        var tvFollowers : TextView = itemView.findViewById(R.id.tv_item_followers)
        var tvFollowing : TextView = itemView.findViewById(R.id.tv_item_following)
        var tvLocation : TextView = itemView.findViewById(R.id.tv_item_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_row_up,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, name, followers, following, location, company, repository, photo ) = listAvatars[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvUsername.text = username
        holder.tvName.text = name
        holder.tvFollowers.text = followers
        holder.tvFollowing.text = following
        holder.tvLocation.text = location


    }

    override fun getItemCount() : Int = listAvatars.size
}
package com.example.github.Search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.github.API.APIResponse
import com.example.github.databinding.ItemRowBinding

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.MyViewHolder>(){

    private  val list = ArrayList<APIResponse>()
    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchList(users: ArrayList<APIResponse>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class  MyViewHolder (private val binding : ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (user : APIResponse){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgPhoto)
                tvUsername.text = user.login
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data : APIResponse)
    }
}
package com.example.registration

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ItemRvBinding
import com.example.registration.models.MyShablon

class RvAdapter(val list:List<MyShablon>,val rvAction:RvClick): RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(val itemRvBinding:ItemRvBinding): RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(myShablon: MyShablon,position: Int){
            itemRvBinding.name.text=myShablon.name
            itemRvBinding.number.text = myShablon.number
            itemRvBinding.back.setOnClickListener {
                rvAction.backClick(myShablon,position)
            }
            itemRvBinding.imageProfile.setImageURI(Uri.parse(myShablon.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
interface RvClick{
    fun backClick(myShablon: MyShablon,position: Int)
}
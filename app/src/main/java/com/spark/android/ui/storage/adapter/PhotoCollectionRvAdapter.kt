package com.spark.android.ui.storage.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PhotoCollectionRvAdapter : RecyclerView.Adapter<PhotoCollectionRvAdapter.PhotoCollectionRvViewHolder>{

    var leftdayList = listOf<String>()

    fun setList(list : List<String>){
        leftdayList = list
        notifyDataSetChanged()
    }

    class PhotoCollectionRvViewHolder {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoCollectionRvViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PhotoCollectionRvViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
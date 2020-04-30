package com.example.recyclerviewreplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class ListAdapter(var activity: MainActivity) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var models: List<User> = listOf()
//    var userFilterList: List<User> = listOf()

    fun setData(data : List<User>){
        models = data
//        this.models = userFilterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.populateModel(models[position],itemCount,position,activity)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun populateModel(user: User, size: Int,position: Int,activity: MainActivity){
            itemView.tvTitle.text = user.title
            itemView.tvDescription.text = user.description

            itemView.setOnClickListener {
                activity.onItemClicked(size, position)
            }
            itemView.btnOption.setOnClickListener {activity.onOptionButtonClick(itemView.btnOption,size,position)}
        }
    }
//
//    override fun getFilter(): Filter {
//        return object : Filter(){
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                var charString: String = constraint.toString()
//                if(charString.isEmpty()){
//                    userFilterList = models
//                }else{
//                    var filteredList: MutableList<User> = mutableListOf()
//                    for(s: User in models){
//                        if(s.title.toLowerCase().contains(charString.toLowerCase())){
//                            filteredList.add(s)
//                        }
//                    }
//                    userFilterList = filteredList
//                }
//                var filterResults: FilterResults = FilterResults()
//                filterResults.values = userFilterList
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                userFilterList = results!!.values as MutableList<User>
//                notifyDataSetChanged()
//            }
//        }
//    }
}
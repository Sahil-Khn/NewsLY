package com.example.NewsIn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class NewsListAdapter(private val listner: NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> =ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder { //called no. of views shown on screen times and then it repeatedly get recycled
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener {
            listner.onItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {   //cals only first time shows how many items will be callled
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem= items[position]
        holder.titleView.text=currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.image)
    }
    fun updateNews(updatedItems :ArrayList<News>){
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }
}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView=itemView.findViewById(R.id.title)
    val image :ImageView=itemView.findViewById(R.id.image)
    val author :TextView=itemView.findViewById(R.id.author)
}
interface NewsItemClicked{
    fun onItemClicked(item: News)
}


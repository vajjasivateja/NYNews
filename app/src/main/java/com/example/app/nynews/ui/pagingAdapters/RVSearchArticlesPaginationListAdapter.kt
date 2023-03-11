package com.example.app.nynews.ui.pagingAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app.nynews.R
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*

class RVSearchArticlesPaginationListAdapter(val mContext: Context) : PagingDataAdapter<ResponseArticlesList.Response.NewsList,
        RVSearchArticlesPaginationListAdapter.MyViewHolder>(DiffUtils) {

    private var mListener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.item_article, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = getItem(position)
        article?.let {
            holder.apply {
                mtv_publish_date.text = "Published On: " + convertTimestampToMalaysianTime(article.pubDate)
                mtv_title.text = article.abstract
                mtv_description.text = article.leadParagraph
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mtv_publish_date: MaterialTextView = itemView.findViewById(R.id.mtv_publish_date)
        internal var mtv_title: MaterialTextView = itemView.findViewById(R.id.mtv_title)
        internal var mtv_description: MaterialTextView = itemView.findViewById(R.id.mtv_description)
    }


    object DiffUtils : DiffUtil.ItemCallback<ResponseArticlesList.Response.NewsList>() {
        override fun areItemsTheSame(oldItem: ResponseArticlesList.Response.NewsList, newItem: ResponseArticlesList.Response.NewsList): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: ResponseArticlesList.Response.NewsList, newItem: ResponseArticlesList.Response.NewsList): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun onArticleSelected(completedTxn: ResponseArticlesList.Response.NewsList?)
    }

    fun setListener(listener: Listener) {
        mListener = listener
    }

    fun convertTimestampToMalaysianTime(timestamp: String): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputDateFormat.parse(timestamp)

        val outputDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        outputDateFormat.timeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur")

        return outputDateFormat.format(date)
    }
}
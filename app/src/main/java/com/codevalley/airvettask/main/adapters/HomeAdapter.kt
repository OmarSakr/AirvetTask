package com.codevalley.airvettask.main.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codevalley.airvettask.databinding.ItemHomeBinding
import com.codevalley.airvettask.main.activities.userDetails.view.UserDetailsActivity
import com.codevalley.airvettask.models.Result
import com.codevalley.airvettask.utils.ParentClass

class HomeAdapter(var context: Context) :
    PagingDataAdapter<Result, HomeAdapter.ViewHolder>(DataDifferentiators) {
    private var layoutInflater: LayoutInflater? = null

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datum = getItem(position)
        holder.binding.tvName.text = datum?.name?.first
        holder.binding.tvEmail.text = datum?.email
        holder.binding.tvLocation.text = datum?.location?.country
        holder.binding.tvMobile.text = datum?.phone
        ParentClass.loadImageWithPicasso(datum?.picture?.large, context, holder.binding.ivUserImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, UserDetailsActivity::class.java)
            intent.putExtra("userData", datum)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    class ViewHolder(var binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)
    object DataDifferentiators : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }


}
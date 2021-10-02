package com.codevalley.airvettask.main.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codevalley.airvettask.databinding.ItemHomeBinding
import com.codevalley.airvettask.main.activities.userDetails.view.UserDetailsActivity
import com.codevalley.airvettask.models.Result
import com.codevalley.airvettask.utils.ParentClass
import java.util.ArrayList

class CacheAdapter(val context: Context) : RecyclerView.Adapter<CacheAdapter.ViewHolder>() {

    private var itemList: ArrayList<Result>? = null
    private var layoutInflater: LayoutInflater? = null

    init {
        itemList = ArrayList()
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = itemList?.get(position)?.name?.first
        holder.binding.tvEmail.text = itemList?.get(position)?.email
        holder.binding.tvLocation.text = itemList?.get(position)?.location?.country
        holder.binding.tvMobile.text = itemList?.get(position)?.phone
        ParentClass.loadImageWithPicasso(
            itemList?.get(position)?.picture?.large,
            context,
            holder.binding.ivUserImage
        )
        holder.itemView.setOnClickListener {
            val intent = Intent(context, UserDetailsActivity::class.java)
            intent.putExtra("userData", itemList?.get(position))
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return itemList!!.size
    }

    fun addAll(data: List<Result>?) {
        itemList!!.clear()
        itemList!!.addAll(data!!)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)


}
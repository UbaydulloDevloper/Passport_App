package Adapters

import Entity.User
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Developer.passportapp.databinding.ItemRecycleBinding

class MyAdapters(val list: List<User>, val click: Click) :
    RecyclerView.Adapter<MyAdapters.Vh>() {


    inner class Vh(var itemRv: ItemRecycleBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(user: User,position: Int) {
            itemRv.textName.text = user.name
            itemRv.textSeriaNumber.text = user.seriaNumber
            itemRv.root.setOnClickListener {
                click.itemClick(user,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    interface Click {
        fun itemClick(user: User, position: Int)
    }

    override fun getItemCount(): Int = list.size
}

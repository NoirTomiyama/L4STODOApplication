package app.miyuseru.l4stodoapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*





class TaskAdapter(
    private val context: Context,
    private var taskList: OrderedRealmCollection<Task>?,
    private var listener:OnItemClickListener,
    private var listener2: CheckBoxClickListener,
    private val autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<Task, TaskAdapter.TaskViewHolder>(taskList, autoUpdate) {

    override fun getItemCount(): Int = taskList?.size ?: 0
    //override fun getItemCount(): Int = checkList?.size ?: 0


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = taskList?.get(position) ?: return
        val checkbox: CheckBox= CheckBox?.get(position) ?: return




        holder.container.setOnClickListener{
            listener.onItemClick(task)
        }

        holder.container.setOnClickListener{
            listener2.CheckBoxClick(checkbox)
        }



        //holder.imageView.setImageResource(task.imageId)
        holder.TodoTextView.text = task.Todo
        //holder.contentTextView.text = task.content
        holder.dateTextView.text =
            SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)


    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)

        return TaskViewHolder(v)
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val container : LinearLayout = view.container

        val checkbox: CheckBox = view.checkBox
//        val imageView: ImageView = view.imageView


        val TodoTextView: TextView = view.todoTextView
        //val contentTextView: TextView = view.contentTextView
        val dateTextView: TextView = view.dateTextView
    }

    interface  OnItemClickListener{
        fun  onItemClick(item: Task)
    }

    interface  CheckBoxClickListener {
        fun CheckBoxClick(item: CheckBox)
    }


}
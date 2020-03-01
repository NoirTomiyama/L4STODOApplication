package app.miyuseru.l4stodoapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        tasks = getTasks()
        // 表示の切替え

        switchView(tasks)

        val taskList = readAll()





//        CheckBox()
//
//        val checkBox = findViewById<CheckBox>(R.id.checkbox)
//        checkBox.setOnCheckedChangeListener {checkBox, isChecked ->
//            startActivity(Intent(applicationContext,Active::class.java))
//        }
//
//

        //画面遷移するよ
        plusButton.setOnClickListener {

            startActivity(Intent(this, CreateActivity::class.java))

        }



        val adapter = TaskAdapter(//TaskAdapter(this, taskList, true)
            this,
        taskList,

       object : TaskAdapter.OnItemClickListener {
           override fun onItemClick(item: Task) {


               val preview = Intent(applicationContext, detail::class.java)
               preview.putExtra("Title", item.Todo)
               preview.putExtra("create", item.content)
               preview.putExtra("id", item.id)
               startActivity(preview)
               Log.d("click","click")


           }
       },

           object :TaskAdapter.CheckBoxClickListener {
                override fun onCheckBoxClick(item: Task) {
                    if (checkBox.isChecked ){

                       // startActivity(Intent(applicationContext,Active::class.java))
                    }
                    else{

                    }
                   Log.d("check","check")

                }
            },true)




        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }

//    override fun onCheckBoxClick(updateDate: String) {
//        val realmTask = realm
//            .where(Task::class.java)
//            .equalTo("createdAt", updateDate)
//            .findFirst()
//
//        realm.executeTransaction {
//            realmTask?.let {
//                it.isChecked = !(it.isChecked)
//            }
//        }
//    }


    fun create(todo: String, content: String) {
        realm.executeTransaction {
            val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
            task.Todo = todo
            task.content = content
        }
    }

    private  var mode : Int=0
    private lateinit var tasks: List<Task>




    private fun getTasks(): List<Task> {
        when (mode) {
            0 -> textView.text = "All TO-DOs"
            1 -> textView.text = "Completed TO-DOs"
            2 -> textView.text = "Active TO-DOs"
        }

        val results: RealmResults<Task> = when (mode) {
            // 全部
            0 -> {
                realm.where(Task::class.java)
                    .findAll()
                    .sort("createdAt", Sort.ASCENDING)
            }
            // いけた
            1 -> {
                realm.where(Task::class.java)
                    .equalTo("isChecked", true)
                    .findAll()
                    .sort("createdAt", Sort.ASCENDING)
            }
            // まだ
            2 -> {
                realm.where(Task::class.java)
                    .equalTo("isChecked", false)
                    .findAll()
                    .sort("createdAt", Sort.ASCENDING)
            }
            else -> return mutableListOf()
        }

        // realmからtaskListを読み取る
        tasks = realm.copyFromRealm(results)
        return tasks
    }

    private fun switchView(tasks: List<Task>) {
        // 表示の切り替え
        if (tasks.isEmpty()) {
            textView.visibility = View.INVISIBLE
        } else {
            textView.visibility = View.VISIBLE
        }
    }


    private fun setTaskList() {
        val results: RealmResults<Task> = when (mode) {
            // 全部
            0 -> {
                realm.where(Task::class.java)
                    .findAll()
                    .sort("createdAt", Sort.ASCENDING)
            }

            // できてるやつ
            1 -> {
                realm.where(Task::class.java)
                    .equalTo("isChecked", true)
                    .findAll()
                    .sort("createdAt", Sort.ASCENDING)


            }

            // まだ
            2 -> {
                realm.where(Task::class.java)
                    .equalTo("isChecked", false)
                    .findAll()
                    .sort("createdAt", Sort.ASCENDING)
            }
            else -> return
        }



        tasks = realm.copyFromRealm(results)

        // 表示
        switchView(tasks)

        // データ更新
        realm.executeTransaction() {
             return@executeTransaction
        }

    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.active -> {
                // まだのやつ表示
                Toast.makeText(applicationContext, "active", Toast.LENGTH_SHORT).show()
                textView.text = "Active TO-DOs"
                mode = 2

                setTaskList()
            }
            R.id.completed -> {
                // いけたやつ表示
                Toast.makeText(applicationContext, "completed", Toast.LENGTH_SHORT).show()
               textView.text = "Completed TO-DOs"
                mode = 1
                setTaskList()
            }
            R.id.all -> {
                //全部表示
                Toast.makeText(applicationContext, "all", Toast.LENGTH_SHORT).show()
                textView.text = "All TO-DOs"
                mode = 0
                setTaskList()
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
        //Log.d("true","true")
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    //だみ−10個作るよ
//     fun createData() {
//
//            create(R.drawable.ic_launcher_background, "あああ")
//
//    }

    fun readAll(): RealmResults<Task> {
        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }



//  fun CheckBox(){
//
//      // set the listener upon the checkbox
//      checkBox.setOnClickListener(
//          View.OnClickListener
//          {
//              val check = checkBox.isChecked()
//              if (check ) {
//                  startActivity(Intent(applicationContext,Active::class.java))
//              } else {
//                  startActivity(Intent(applicationContext,Conpleted::class.java))
//          }
//
// })
  }







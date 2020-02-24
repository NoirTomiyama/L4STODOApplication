package app.miyuseru.l4stodoapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val taskList = readAll()



        //画面遷移するよ
        plusButton.setOnClickListener {

            startActivity(Intent(this, CreateActivity::class.java))

        }



        val adapter = //TaskAdapter(this, taskList, true)
        TaskAdapter(this,taskList, object : TaskAdapter.OnItemClickListener {
            override fun onItemClick(item: Task) {


                val preview = Intent(applicationContext,detail::class.java)
                preview.putExtra("Title" , item.Todo)
                preview.putExtra("create" , item.content)

                preview.putExtra("id" , item.id)

                startActivity(preview)


            }
        },true)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val message = when (item?.itemId) {
            R.id.all -> {
                resources.getString(R.string.all_add)
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }



                R.id.active -> {resources.getString(R.string.active_add)
                startActivity(Intent(applicationContext, Active::class.java))
            }


                R.id.completed -> {
                    resources.getString(R.string.completed_add)
                    startActivity(Intent(applicationContext, Conpleted::class.java))
                }
              else -> super.onOptionsItemSelected(item)

        }

        return super.onOptionsItemSelected(item)
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

    fun create(todo: String, content: String) {
        realm.executeTransaction {
            val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
            task.Todo = todo
            task.content = content
        }
    }

    fun readAll(): RealmResults<Task> {
        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }





}


package app.miyuseru.l4stodoapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        // 画面遷移
        plusButton.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        val adapter = TaskAdapter(
            this, taskList,
            object : TaskAdapter.OnItemClickListener {
                override fun onItemClick(item: Task) {

                    Log.d("onItemClick", "onItemClick")

                    val preview = Intent(applicationContext, DetailActivity::class.java)
                    preview.putExtra("Title", item.Todo)
                    preview.putExtra("create", item.content)
                    preview.putExtra("id", item.id)

                    startActivity(preview)
                }
            },
            object : TaskAdapter.OnCheckBoxClickListener {
                override fun onCheckBoxClick(item: Task) {
                    Log.d("onCheckBox", "onCheckBox")
                }
            }, true
        )

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        // TO みゆせる
        // TODO ここの仕様をActivityへの遷移ではなく，MainActivity内で完結させる仕様に変更
        val message = when (item?.itemId) {
            R.id.all -> {
                resources.getString(R.string.all_add)
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }

            R.id.active -> {
                resources.getString(R.string.active_add)
                startActivity(Intent(applicationContext, ActiveActivity::class.java))
            }

            R.id.completed -> {
                resources.getString(R.string.completed_add)
                startActivity(Intent(applicationContext, CompletedActivity::class.java))
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun create(todo: String, content: String) {
        realm.executeTransaction {
            val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
            task.Todo = todo
            task.content = content
        }
    }

    // ここでrealmからTaskを取得する部分のコードを変更すれば，MainActivity内でフィルタリングできるよ．
    fun readAll(): RealmResults<Task> {
        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }

}






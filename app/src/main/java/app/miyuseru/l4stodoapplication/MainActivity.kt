package app.miyuseru.l4stodoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.file.Files.delete
import java.util.*

class MainActivity : AppCompatActivity() {
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList = readAll()




        if (taskList.isEmpty()) {
            createDummyData()
        }

        val adapter = TaskAdapter(this, taskList, true)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

    }

    override fun onDestroy() {


        super.onDestroy()
        realm.close()
    }

    fun createDummyData() {

        for (i in 0..10) {
            create(R.drawable.ic_launcher_background, "やること $i")
        }
    }

    fun create(imageId: Int, content: String) {

        realm.executeTransaction {
            val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
            task.imageId = imageId
            task.content = content
        }
    }




    fun readAll(): RealmResults<Task> {

        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }

}
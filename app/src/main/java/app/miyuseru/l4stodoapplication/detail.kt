package app.miyuseru.l4stodoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat.startActivity
import io.realm.Realm
import io.realm.RealmObject.deleteFromRealm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.titleText
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.list_item.*
import java.nio.file.Files.delete

class detail : AppCompatActivity() {


    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val titleText2 = intent.getStringExtra("Title")
        val createText2 = intent.getStringExtra("create")

        titleText.text = titleText2
        createText.text = createText2



        editButton.setOnClickListener() {

            val update = Intent(applicationContext, UpdateActivity::class.java)
            val id: String = intent.getStringExtra("id")
            update.putExtra("taskId", id)

            startActivity(update)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = intent.getStringExtra("taskId")
        val task = realm.where<Task>().equalTo("id", id).findFirst()!!

        titleText.setText(task.Todo)
        editText2.setText(task.content)

        val message = when (item?.itemId) {
            R.id.deleteButton -> {
                resources.getString(R.string.menu_tools)

                // update(titleText.text.toString(),editText2.text.toString())

                delete(task = Task())
//                onDestroy(container)


            }

            else -> super.onOptionsItemSelected(item)

        }

        return super.onOptionsItemSelected(item)
    }


    fun delete(task: Task) {
        val id = intent.getStringExtra("taskId")
        val task = realm.where<Task>().equalTo("id", id).findFirst()!!



        realm.executeTransaction {
            deleteFromRealm(task)

        }

        finish()

    }
}








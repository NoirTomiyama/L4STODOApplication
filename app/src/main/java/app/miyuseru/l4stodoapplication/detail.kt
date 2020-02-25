package app.miyuseru.l4stodoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.titleText
import kotlinx.android.synthetic.main.list_item.*

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when (item.itemId) {
            R.id.deleteButton -> {
                resources.getString(R.string.menu_tools)

             delete(taskId.toString())

            }

            else -> super.onOptionsItemSelected(item)

        }

        return super.onOptionsItemSelected(item)
    }


    fun delete(id :String) {

        val id :String = intent.getStringExtra("id")

        realm.executeTransaction() {
            val task = realm.where(Task :: class.java).equalTo("id", id).findFirst()

               ?: return@executeTransaction

            task.deleteFromRealm()

        }

        finish()

    }
}




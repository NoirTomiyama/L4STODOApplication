package app.miyuseru.l4stodoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_update.titleText
import kotlinx.android.synthetic.main.list_item.*
import java.util.*

class UpdateActivity : AppCompatActivity() {

    val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val id = intent.getStringExtra("taskId")
        val task = realm.where<Task>().equalTo("id", id).findFirst()!!

        titleText.setText(task.Todo)
        editText2.setText(task.content)

        floatingActionButton.setOnClickListener(){
            update(titleText.text.toString(),editText2.text.toString())
        }



    }

    fun update(todo: String, content: String) {
        val id = intent.getStringExtra("taskId")
        val task = realm.where<Task>().equalTo("id", id).findFirst()!!

        realm.executeTransaction {
            task.Todo = todo
            task.content = content
        }
        startActivity(Intent(applicationContext, MainActivity::class.java))

    }

}

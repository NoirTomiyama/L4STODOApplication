package app.miyuseru.l4stodoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_create.*
import java.util.*

class CreateActivity : AppCompatActivity() {

    val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        createButton.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
            create(titleText.text.toString(),createText.text.toString())

        }

    }

    fun create(todo: String, content: String) {

        realm.executeTransaction {
            val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
            task.Todo = todo
            task.content = content
        }
    }
}

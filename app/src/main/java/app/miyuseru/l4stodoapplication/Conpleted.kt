package app.miyuseru.l4stodoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class Conpleted : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conpleted)
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
}

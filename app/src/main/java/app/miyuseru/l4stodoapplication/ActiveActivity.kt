package app.miyuseru.l4stodoapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

// TODO 廃棄する
class ActiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active)
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
}

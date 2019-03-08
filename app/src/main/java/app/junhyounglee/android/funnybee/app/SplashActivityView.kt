package app.junhyounglee.android.funnybee.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivityView : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler.postDelayed({
            startActivity(Intent(this@SplashActivityView, WelcomeActivityView::class.java))
            finish()
        }, 1500)
    }
}

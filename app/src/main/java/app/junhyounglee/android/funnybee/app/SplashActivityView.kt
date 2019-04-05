package app.junhyounglee.android.funnybee.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

/**
 * class SplashActivityView
 *
 * application entry screen to show splash screen for 1.5 seconds.
 *
 * history
 *  created: 8 March 2019
 *  reviewed: 30 March 2019
 */
/*
 * reviewer comment
 * - mohamed:
 *      please add the name of developer and date for when this class was created
 *      please add class summary
 */
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

package app.junhyounglee.android.funnybee.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.junhyounglee.android.funnybee.R
import app.junhyounglee.android.funnybee.ui.event.NavigateSignUpViewEvent
import app.junhyounglee.android.funnybee.ui.event.RxEventManager
import io.reactivex.disposables.CompositeDisposable

class WelcomeActivityView : AppCompatActivity() {

    private var subscription: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_view)

        setUpEventLifeCycle()

        if (savedInstanceState == null) {
            navigateToLoginView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanUpEventLifeCycle()
    }

    private fun navigateToLoginView() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                LoginFragmentView.create()
            )
            .commit()
    }

    private fun setUpEventLifeCycle() {
        cleanUpEventLifeCycle()

        subscription = CompositeDisposable()
    }

    private fun cleanUpEventLifeCycle() {
        subscription?.let {
            if (!it.isDisposed) it.dispose()
            subscription = null
        }
    }
}

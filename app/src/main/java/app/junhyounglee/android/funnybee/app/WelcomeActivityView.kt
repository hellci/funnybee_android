package app.junhyounglee.android.funnybee.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.junhyounglee.android.funnybee.R
import app.junhyounglee.android.funnybee.ui.event.NavigateSignUpViewEvent
import app.junhyounglee.android.funnybee.ui.event.RxEventManager
import io.reactivex.disposables.CompositeDisposable

/**
 * class WelcomeActivityView
 *
 * An activity view class that has login, sign up views
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
 * - harjot:
 *      the name of developer, history and other explanation comments should be included.
 *      the class summary could also be included.
 */
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

    private fun navigateToSignUpView() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                SignUpFragmentView.create()
            )
            .addToBackStack(null)
            .commit()
    }

    /**
     * Subscribe sign up event(NavigateSignUpViewEvent). if it catch event navigate to sign up view
     *
     * reviewer comment
     * - mohamed:
     *      if possible, please add summary to explain why these functions needed, as someone new to Kotlin, I am not familiar with the logic behind them
     */
    private fun setUpEventLifeCycle() {
        cleanUpEventLifeCycle()

        subscription = CompositeDisposable()

        subscription!!.add(RxEventManager.instance.subscribe(NavigateSignUpViewEvent::class.java) {
            navigateToSignUpView()
        }!!)
    }

    private fun cleanUpEventLifeCycle() {
        subscription?.let {
            if (!it.isDisposed) it.dispose()
            subscription = null
        }
    }
}

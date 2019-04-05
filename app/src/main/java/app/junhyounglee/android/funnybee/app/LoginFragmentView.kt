package app.junhyounglee.android.funnybee.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import app.junhyounglee.android.funnybee.R
import app.junhyounglee.android.funnybee.app.domain.FunnyBeeDatabase
import app.junhyounglee.android.funnybee.app.domain.model.User
import app.junhyounglee.android.funnybee.app.domain.model.UserDao
import app.junhyounglee.android.funnybee.ui.event.NavigateSignUpViewEvent
import app.junhyounglee.android.funnybee.ui.event.RxEventManager
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * class LoginFragmentView
 *
 * Login screen
 *
 * history
 *  created: 8 March 2019
 *  reviewed: 30 March 2019
 */
/*
 * reviewer comment
 * - mohamed:
 *      please remove unused imports
 *      please add the name of developer and date for when this class was created
 *      please add class summary
 * - harjot:
 *      the name of developer, history and other explanation comments should be included.
 *      the class summary could also be included.
 */
class LoginFragmentView : Fragment() {

    @BindView(R.id.container) lateinit var container: View

    /*
     * reviewer comment
     * - mohamed:
     *      is it possible to use more informative name for the view type? like idTxt for example
     */
    @BindView(R.id.idText) lateinit var idText: EditText

    /*
     * reviewer comment
     * - mohamed:
     *      is it possible to use more informative name for the view type? like passwordTxt for example
     */
    @BindView(R.id.passwordText) lateinit var passwordText: EditText

    /*
     * reviewer comment
     * - mohamed:
     *      would you please add summary to this property?
     */
    private val userDao: UserDao by lazy {
        FunnyBeeDatabase.getDatabase(context!!).userDao()
    }

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_view, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    @OnClick(R.id.loginView)
    public fun onLoginClick() {
        val id = idText.text.toString()
        val password = passwordText.text.toString()

        scope.launch(Dispatchers.IO) {
            val user: User? = userDao.get(id)

            /*
             * reviewer comment
             * - mohamed:
             *      security wise, I think it would be better to return the same msg whether it's wrong password or id
             *      for instance, "Invalid user name or password!"
             */
            withContext(Dispatchers.Main) {
                if (user != null) {
                    when (password) {
                        user.password -> {
                            navigateHomeView(user)
                        }
                    }
                } else {
                    Snackbar.make(container, "Invalid user name or password!", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    @OnClick(R.id.navigateSignUpView)
    public fun onSignUpClick() {
        RxEventManager.instance.post(NavigateSignUpViewEvent())
    }

    private fun navigateHomeView(user: User) {
        val intent = Intent(context, HomeActivityView::class.java)
        intent.putExtra(HomeActivityView.EXTRA_USER, user)
        startActivity(intent)
    }


    companion object {
        /**
         * create a login screen instance.
         */
        fun create(): LoginFragmentView {
            /*
             * reviewer comment
             * - mohamed:
             *      you could return LoginFragmentView() without assigning it to a new variable
             */
            return LoginFragmentView()
        }
    }

}

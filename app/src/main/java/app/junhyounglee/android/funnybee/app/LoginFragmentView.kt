package app.junhyounglee.android.funnybee.app


import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class LoginFragmentView : Fragment() {

    @BindView(R.id.container) lateinit var container: View
    @BindView(R.id.idView) lateinit var idView: EditText
    @BindView(R.id.passwordView) lateinit var passwordView: EditText

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
        val id = idView.text.toString()
        val password = passwordView.text.toString()

        scope.launch(Dispatchers.IO) {
            val user: User? = userDao.get(id)

            withContext(Dispatchers.Main) {
                if (user != null) {
                    when (password) {
                        user.password -> {
                            navigateHomeView(user)
                        }
                        else -> {
                            Snackbar.make(container, "User information is not matched!", Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                } else {
                    Snackbar.make(container, "No matched user!", Snackbar.LENGTH_LONG).show()
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
        intent.putExtra(HomeActivityView.EXSTRA_USER, user)
        startActivity(intent)
    }


    companion object {
        fun create(): LoginFragmentView {
            val fragment = LoginFragmentView()
            return fragment
        }
    }

}

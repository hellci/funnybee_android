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
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_signup_view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignUpFragmentView : Fragment() {

    @BindView(R.id.rootView) lateinit var rootView: View
    @BindView(R.id.idView) lateinit var idView: EditText
    @BindView(R.id.passwordView) lateinit var passwordView: EditText
    @BindView(R.id.repassView) lateinit var repassView: EditText

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
        val view = inflater.inflate(R.layout.fragment_signup_view, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    @OnClick(R.id.signUp)
    public fun onSignUpClick() {
        if (!validate()) {
            errorView.text = "Please enter valid information"
            return
        }

        errorView.text = ""

        scope.launch(Dispatchers.IO) {
            val user = User(id = idView.text.toString(), password = passwordView.text.toString())
            userDao.addOrUpdate(user)

            withContext(Dispatchers.Main) {
                navigateHomeView(user)
            }
        }
    }

    private fun validate(): Boolean {
        val id = idView.text.toString()
        val password = passwordView.text.toString()
        val repass = repassView.text.toString()
        return id.isNotEmpty() && password.isNotEmpty() && password == repass
    }

    private fun navigateHomeView(user: User) {
        val intent = Intent(context, HomeActivityView::class.java)
        intent.putExtra(HomeActivityView.EXSTRA_USER, user)
        startActivity(intent)
    }


    companion object {
        fun create(): SignUpFragmentView {
            val fragment = SignUpFragmentView()
            return fragment
        }
    }
}

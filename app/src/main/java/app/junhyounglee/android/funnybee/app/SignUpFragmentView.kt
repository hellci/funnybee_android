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
import kotlinx.android.synthetic.main.fragment_signup_view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * class SignUpFragmentView
 *
 * sign up screen
 *
 * history
 *  created: 8 March 2019
 *  reviewed: 30 March 2019
 */
/*
 * reviewer comment
 * - mohamed:
 *      please remove unused imports
 * - harjot:
 *      the name of developer, history and other explanation comments should be included.
 *      the class summary could also be included.
 */
class SignUpFragmentView : Fragment() {
    /*
     * reviewer comment
     * - mohamed:
     *      please remove unused members
     */
    @BindView(R.id.rootView) lateinit var rootView: View

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
    @BindView(R.id.repassText) lateinit var repassText: EditText

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

    /*
     * reviewer comment
     * - harjot:
     *      more detailed message could be considered
     *      Login and SignUp fragments have different styles to display message.
     *      One common style could be considered.
     */
    @OnClick(R.id.signUp)
    public fun onSignUpClick() {
        if (!validate()) {
            errorView.text = "Please enter valid information"
            return
        }

        errorView.text = ""

        scope.launch(Dispatchers.IO) {
            val user = User(id = idText.text.toString(), password = passwordText.text.toString())
            userDao.addOrUpdate(user)

            withContext(Dispatchers.Main) {
                navigateToHomeView(user)
            }
        }
    }

    /*
     * reviewer comment
     * - harjot:
     *      please consider the case where user enters the ID that already exists
     *      Current application terminate when user enter the id that already exists
     */
    private fun validate(): Boolean {
        val id = idText.text.toString()
        val password = passwordText.text.toString()
        val repass = repassText.text.toString()
        return id.isNotEmpty() && password.isNotEmpty() && password == repass
    }

    private fun navigateToHomeView(user: User) {
        val intent = Intent(context, HomeActivityView::class.java)
        intent.putExtra(HomeActivityView.EXTRA_USER, user)
        startActivity(intent)
    }

    /*
     * reviewer comment
     * - mohamed:
     *      you could return SignUpFragmentView() without assigning it to a new variable
     */
    companion object {
        /**
         * create sign up screen instance
         */
        fun create(): SignUpFragmentView = SignUpFragmentView()
    }
}

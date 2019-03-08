package app.junhyounglee.android.funnybee.app


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import app.junhyounglee.android.funnybee.R
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar

class SignUpFragmentView : Fragment() {

    @BindView(R.id.rootView) lateinit var rootView: View
    @BindView(R.id.idView) lateinit var idView: EditText
    @BindView(R.id.passwordView) lateinit var passwordView: EditText
    @BindView(R.id.repassView) lateinit var repassView: EditText


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
            context?.let {
                Snackbar.make(rootView, "Please enter valid information", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validate(): Boolean {
        return idView.text.isNotEmpty() && passwordView.text.isNotEmpty() && passwordView.text == repassView.text
    }


    companion object {
        fun create(): SignUpFragmentView {
            val fragment = SignUpFragmentView()
            return fragment
        }
    }
}

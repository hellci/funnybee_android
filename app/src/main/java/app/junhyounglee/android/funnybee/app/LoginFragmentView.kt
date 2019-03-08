package app.junhyounglee.android.funnybee.app


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.junhyounglee.android.funnybee.R
import app.junhyounglee.android.funnybee.ui.event.NavigateSignUpViewEvent
import app.junhyounglee.android.funnybee.ui.event.RxEventManager
import butterknife.ButterKnife
import butterknife.OnClick

class LoginFragmentView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_view, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    @OnClick(R.id.navigateSignUpView)
    public fun onSignUpClick() {
        RxEventManager.instance.post(NavigateSignUpViewEvent())
    }


    companion object {
        fun create(): LoginFragmentView {
            val fragment = LoginFragmentView()
            return fragment
        }
    }

}

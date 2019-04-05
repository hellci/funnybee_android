package app.junhyounglee.android.funnybee.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.junhyounglee.android.funnybee.R
import app.junhyounglee.android.funnybee.app.domain.model.Post
import app.junhyounglee.android.funnybee.app.domain.model.User
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home_view.*

/**
 * class HomeActivityView
 *
 * Home screen to present list data.
 *
 * history
 *  created: 8 March 2019
 *  reviewed: 30 March 2019
 */
/*
 * reviewer comment: access controller should be considered
 * - mohamed:
 *      please add the name of developer and date for when this class was created
 *      please add class summary
 */
class HomeActivityView : AppCompatActivity() {

    @BindView(R.id.container) lateinit var container: View
    @BindView(R.id.postsView) lateinit var postsView: RecyclerView

    private lateinit var user: User
    private var adapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resolveViewArguments()

        setContentView(R.layout.activity_home_view)
        ButterKnife.bind(this)

        setUpViews()
    }

    private fun resolveViewArguments() {
        user = intent.getParcelableExtra(EXTRA_USER)
    }

    private fun setUpViews() {
        postsView.layoutManager = LinearLayoutManager(this)
        postsView.setHasFixedSize(true)
        postsView.adapter = getOrCreateAdapter()

        welcomeView.text = "Welcome ${user.id}"
    }

    private fun getOrCreateAdapter(): PostAdapter? {
        if (adapter == null) {
            adapter = PostAdapter(object: PostAdapter.OnPostItemClickListener {
                override fun onPostItemClick(holder: PostAdapter.PostItemView, post: Post) {

                    Snackbar.make(container, post.title!!, Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
        }
        return adapter
    }

    /**
     * Data provider for post list.
     */
    class PostAdapter(private val onItemClickListener: OnPostItemClickListener) :
        RecyclerView.Adapter<PostAdapter.PostItemView>() {

        private var data: List<Post>? = null

        override fun getItemCount(): Int {
            return if (data == null) 0 else data!!.size
        }

        @NonNull
        override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, viewType: Int): PostItemView {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.post_item_layout, viewGroup, false)
            return PostItemView(view, onItemClickListener)
        }

        override fun onBindViewHolder(@NonNull holder: PostItemView, position: Int) {
            val item = data!![position]
            holder.bind(item)
        }

        /**
         * update data adapter with given Post list and refresh list.
         *
         * reviewer comment:
         * - mohamed:
         *      please remove this function if it isn't used anywhere
         */
        fun update(data: List<Post>) {
            this.data = data
            notifyDataSetChanged()
        }

        /**
         * Post list data item view holder.
         *
         * reviewer comment:
         * - mohamed:
         *      please add class summary
         */
        class PostItemView(@NonNull itemView: View, private val onPostItemClickListener: OnPostItemClickListener?) :
            RecyclerView.ViewHolder(itemView) {

            private val titleView: TextView = itemView.findViewById(R.id.titleText)
            private val commentView: TextView = itemView.findViewById(R.id.commentText)

            init {
                if (this@PostItemView.onPostItemClickListener != null) {
                    itemView.setOnClickListener { view ->
                        val post = view.tag as Post
                        this@PostItemView.onPostItemClickListener.onPostItemClick(this@PostItemView, post)
                    }
                }
            }

            fun bind(post: Post) {
                titleView.text = post.title
                commentView.text = post.comment

                itemView.tag = post
            }
        }


        interface OnPostItemClickListener {

            fun onPostItemClick(holder: PostItemView, post: Post)
        }
    }

    /**
     * Define static variables
     *
     * reviewer comment:
     * - mohamed:
     *      please add inline comment to inform what this line of code does.
     */
    companion object {
        /**
         * user information key object to retrieve
         *
         * reviewer comment:
         * - mohamed:
         *      Please fix the typo in the constant name.
         * - harjot:
         *      please consider the correct spellings of const
         *      i.e. EXTRA_USER instead of EXSTRA_USER
         */
        const val EXTRA_USER = "extra_user"
    }
}

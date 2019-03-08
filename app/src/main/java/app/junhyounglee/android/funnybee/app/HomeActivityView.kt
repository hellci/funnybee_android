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
import butterknife.BindView
import com.google.android.material.snackbar.Snackbar

class HomeActivityView : AppCompatActivity() {

    @BindView(R.id.container) lateinit var container: View
    @BindView(R.id.postsView) lateinit var postsView: RecyclerView

    private var adapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_view)

        setUpViews()


    }

    private fun setUpViews() {
        postsView.layoutManager = LinearLayoutManager(this)
        postsView.setHasFixedSize(true)
        postsView.adapter = getOrCreateAdapter()
    }

    private fun getOrCreateAdapter(): PostAdapter? {
        if (adapter == null) {
            adapter = PostAdapter(object: PostAdapter.OnPostItemClickListener {
                override fun onPostItemClick(holder: PostAdapter.PostItemView, post: Post) {

                    Snackbar.make(container, post.title, Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
        }
        return adapter
    }


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

        fun update(data: List<Post>) {
            this.data = data
            notifyDataSetChanged()
        }


        class PostItemView(@NonNull itemView: View, private val onPostItemClickListener: OnPostItemClickListener?) :
            RecyclerView.ViewHolder(itemView) {

            private val titleView: TextView = itemView.findViewById(R.id.titleView)
            private val commentView: TextView = itemView.findViewById(R.id.commentView)

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
}

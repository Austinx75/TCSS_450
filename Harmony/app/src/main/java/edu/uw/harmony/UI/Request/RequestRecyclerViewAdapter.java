package edu.uw.harmony.UI.Request;

import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentRequestCardBinding;

//extends androidx.recyclerview.widget.RecyclerView.Adapter
public class RequestRecyclerViewAdapter extends RecyclerView.Adapter<RequestRecyclerViewAdapter.BlogViewHolder> {

    //Store all of the requests to present
    private final List<RequestPost> mRequests;

    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<RequestPost, Boolean> mExpandedFlags;

    public RequestRecyclerViewAdapter(List<RequestPost> items) {
        this.mRequests = items;
        mExpandedFlags = mRequests.stream().collect(Collectors.toMap(Function.identity(), blog -> false));
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlogViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_request_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        holder.setBlog(mRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class BlogViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentRequestCardBinding binding;
        private RequestPost mBlog;

        public BlogViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentRequestCardBinding.bind(view);
            binding.requestCard.setOnClickListener(this::handleMoreOrLess);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            mExpandedFlags.put(mBlog, !mExpandedFlags.get(mBlog));
            displayPreview();
        }
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        private void displayPreview() {
            if (mExpandedFlags.get(mBlog)) {
                binding.requestPreview.setVisibility(View.VISIBLE);
//                binding.buittonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_less_grey_24dp));
            } else {
                binding.requestPreview.setVisibility(View.GONE);
//                binding.buittonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_more_grey_24dp));
            }
        }
        void setBlog(final RequestPost blog) {
            mBlog = blog;
//            binding.buttonFullPost.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(BlogListFragmentDirections.actionNavigationBlogsToBlogPostFragment(blog));
//            });


//            binding.textTitle.setText(blog.getTitle());
//            binding.textPubdate.setText(blog.getPubDate());


            //Use methods in the HTML class to format the HTML found in the text
//            final String preview = Html.fromHtml(
//                    blog.getTeaser(),
//                    Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,100) //just a preview of the teaser
//                    + "...";


            String preview = "I want to be your friend";
            binding.requestPreview.setText(preview);
            displayPreview();
        }
    }
}

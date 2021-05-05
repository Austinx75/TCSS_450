package edu.uw.harmony.UI.Chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.harmony.R;
import edu.uw.harmony.databinding.FragmentChatPostBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatPostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChatPostFragmentArgs args = ChatPostFragmentArgs.fromBundle(getArguments());
        FragmentChatPostBinding binding = FragmentChatPostBinding.bind(getView());
        binding.textPubdate.setText(args.getChat().getPubDate());
        binding.textTitle.setText(args.getChat().getTitle());
        final String preview = Html.fromHtml(
                args.getChat().getTeaser(),
                Html.FROM_HTML_MODE_COMPACT)
                .toString();
        binding.textPreview.setText(preview);
        //Note we are using an Intent here to start the default system web browser
//        binding.buttonUrl.setOnClickListener(button ->
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(args.getChat().getUrl()))));
    }
}









///////////////////////////////////////////////////////////////

//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import edu.uw.harmony.R;
//import edu.uw.harmony.databinding.FragmentChatPostBinding;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class ChatPostFragment extends Fragment {
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_chat_post, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ChatPostFragmentArgs args = ChatPostFragmentArgs.fromBundle(getArguments());
//        FragmentChatPostBinding binding = FragmentChatPostBinding.bind(getView());
//        binding.textPubdate.setText(args.getChat().getPubDate());
//        binding.textTitle.setText(args.getChat().getTitle());
//        final String preview = Html.fromHtml(
//                args.getChat().getTeaser(),
//                Html.FROM_HTML_MODE_COMPACT)
//                .toString();
//        binding.textPreview.setText(preview);
//        //Note we are using an Intent here to start the default system web browser
//        binding.buttonUrl.setOnClickListener(button ->
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(args.getChat().getUrl()))));
//    }
//}
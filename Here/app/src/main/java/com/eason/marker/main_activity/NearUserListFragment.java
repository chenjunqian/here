package com.eason.marker.main_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.model.Post;
import com.eason.marker.util.PostListViewAdapter;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Eason on 9/6/15.
 */
public class NearUserListFragment extends BaseFragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_list_layout, container, false);
        listView = (ListView) root.findViewById(R.id.post_list_view);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Collections.sort(MainActivity.postListItem, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }

            @Override
            public boolean equals(Object object) {
                return false;
            }
        });

        listView.setAdapter(new PostListViewAdapter(getActivity(),MainActivity.postListItem));
    }

}

package com.manga.tubes.mangaeden.tubes03;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manga.tubes.mangaeden.tubes03.Mangalist;
import com.manga.tubes.mangaeden.tubes03.R;
import com.manga.tubes.mangaeden.tubes03.RecyclerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<Mangalist> list=new ArrayList<>();
    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        adapter=new RecyclerAdapter();
        return view;
    }

}

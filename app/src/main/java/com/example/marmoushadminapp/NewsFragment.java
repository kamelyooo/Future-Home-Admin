package com.example.marmoushadminapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsFragment extends Fragment implements NewsFragment_IMPL {

    SwipeRefreshLayout newsrefresh;
    ListView lv_news;
    ImageView cm_imagenews;
    TextView cm_codenews;
    private List<News> news;
    newsadapter adapter;
    NewsFragmentPresenterIMPL presenter;
    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new NewsFragmentPresenter(this);
        presenter.getNews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        newsrefresh=view.findViewById(R.id.swapnews);
        lv_news=view.findViewById(R.id.lv_news);

        newsrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               presenter.getNews();
                newsrefresh.setRefreshing(false);
            }
        });


        lv_news.setOnItemLongClickListener((parent, view1, position, id) -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.removenews(news.get(position));
                }
            }).setNegativeButton("cancel",null).setMessage("Do you want to remove");


            builder.show();
            return false;
        });
        return view;
    }

    @Override
    public void showNews(List<News> response) {
        news=response;
        adapter = new newsadapter(getActivity(),news);
        lv_news.setAdapter(adapter);
    }

    @Override
    public void removedNews(News newss) {

        Toast.makeText(getActivity(), "removed"+ newss.getCode(), Toast.LENGTH_SHORT).show();


        news.remove(newss);
        adapter.notifyDataSetChanged();
    }

    class newsadapter extends ArrayAdapter<News> {
        public newsadapter(@NonNull Context context, List<News> news) {
            super(context, 0,news);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView==null)
                convertView=getLayoutInflater().inflate(R.layout.cm_news,parent,false);

            cm_imagenews=convertView.findViewById(R.id.cm_imagenews);
            cm_codenews=convertView.findViewById(R.id.cm_codenews);

            Picasso.get().load(getItem(position).getImageUrl()).into(cm_imagenews);
            cm_codenews.setText(getItem(position).getCode().toString());


            return convertView;
        }
    }

}
package com.example.marmoushadminapp;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;


import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class OrderFregmant extends Fragment implements OrderFregmant_IMPL {
    ListView lv_orders;
    TextView cm_ordername, cm_orderDate;
    private List<Users> names;
    List<Order> orders;
    Optional<String> namee;
    SimpleDateFormat formatter;
    SwipeRefreshLayout swipeLayout;
    orderPresenter_IMPL presenter;

    public OrderFregmant() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new orderPresenter(this);
        presenter.getOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_order_fregmant, container, false);
        lv_orders = view.findViewById(R.id.lv_orders);
        swipeLayout = view.findViewById(R.id.swiperefresh);


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getOrders();
                
                swipeLayout.setRefreshing(false);
            }
        });


        return view;
    }

    class orderadapter extends ArrayAdapter<Order> {
        public orderadapter(@NonNull Context context, List<Order> orders) {
            super(context, 0, orders);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.cm_order, parent, false);
            cm_ordername = convertView.findViewById(R.id.cm_orderName);
            cm_orderDate = convertView.findViewById(R.id.cm_orderDate);

            namee = names.parallelStream().filter(users -> users.ownerId.equals(getItem(position).getOwnerId())).findFirst().map(users -> users.name);

            namee.ifPresent(s -> cm_ordername.setText(s));

            formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");


            cm_orderDate.setText(formatter.format(Date.parse(getItem(position).getCreated().toString())));

            return convertView;
        }
    }

    @Override
    public void showOrders(List<Users> responseee, List<Order> response) {

        names = responseee;

        orders = response.stream().sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated())).collect(Collectors.toList());

        for (int i = 0; i < orders.size(); i++) {
            for (int j = i + 1; j < orders.size(); j++) {
                if (orders.get(i).getOwnerId().equals(orders.get(j).getOwnerId()) && orders.get(i).getCreated().equals(orders.get(j).getCreated())) {
                    orders.remove(i);
                }
            }
        }
        orderadapter adapter = new orderadapter(getActivity(), orders);
        lv_orders.setAdapter(adapter);
        lv_orders.setOnItemClickListener((parent, view1, position, id) -> {
            Order orderbyname = orders.get(position);
            Intent in = new Intent(getActivity(), orderDetails.class);
            in.putExtra("order", orderbyname);
            startActivity(in);
        });

    }

    public void showToast(String toast){
        Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
    }
}
package com.example.marmoushadminapp;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class orderPresenter implements orderPresenter_IMPL {
    OrderFregmant_IMPL fregmant;
    public orderPresenter(OrderFregmant fregmant) {
        this.fregmant = fregmant;
    }


    @Override
    public void getOrders(){
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(100);
        Backendless.Data.of(Users.class).find(queryBuilder, new AsyncCallback<List<Users>>() {
            @Override
            public void handleResponse(List<Users> responseee) {
                Log.i("ccc", responseee + "");

                DataQueryBuilder queryBuilde = DataQueryBuilder.create();
                queryBuilde.setPageSize(100);

                Backendless.Data.of(Order.class).find(queryBuilde, new AsyncCallback<List<Order>>() {
                    @Override
                    public void handleResponse(List<Order> response) {
                        fregmant.showOrders(responseee,response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                fregmant.showToast("Error Connection");
            }
        });
    }

}

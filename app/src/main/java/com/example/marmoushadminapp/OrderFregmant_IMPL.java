package com.example.marmoushadminapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public interface OrderFregmant_IMPL {
    void onCreate(Bundle savedInstanceState);

    View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState);

    void showOrders(List<Users> responseee, List<Order> response);

    void showToast(String toast);
}

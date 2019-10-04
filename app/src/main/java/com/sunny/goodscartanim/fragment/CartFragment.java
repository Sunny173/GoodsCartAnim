package com.sunny.goodscartanim.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CartFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);

        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
//        TextView tv = (TextView) view.findViewById(R.id.container);
        TextView tv = new TextView(getContext());
        tv.setText(agrs1);
        return tv;
    }


    public static CartFragment newInstance(String param1) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CartFragment() {

    }

    @Override
    public void onClick(View view) {

    }
}


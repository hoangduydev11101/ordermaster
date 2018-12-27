package com.master.ordercoffee.home.staff;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.master.ordercoffee.R;
import com.master.ordercoffee.service.FragmentService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StaffFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.ry_busy_table)
    RecyclerView mListBusy;
    @BindView(R.id.tv_empty)
    TextView mEmpty;
    @BindView(R.id.tv_add_booking)
    TextView mAddBooking;


    @OnClick(R.id.tv_add_booking)
    void onAddBookingClicked() {
        FragmentService.getInstance(mContext).pushFragment(R.id.add_view, new AddBookingFragment(), "staff");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

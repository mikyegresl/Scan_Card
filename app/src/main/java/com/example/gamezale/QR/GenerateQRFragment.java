package com.example.gamezale.QR;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamezale.Database.Employee;
import com.example.gamezale.MainActivity;
import com.example.gamezale.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class GenerateQRFragment extends Fragment {

    private ImageView qrImageView;
    private TextView cancelBtn;
    private PulsatorLayout pulsatorLayout;
    private TextView nameTextView;

    private Employee currentEmployee;


    public GenerateQRFragment() {
        // Required empty public constructor
    }

    public static GenerateQRFragment newInstance(final Employee employee) {
        GenerateQRFragment fragment = new GenerateQRFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.SHARE_EMPLOYEE, employee);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            currentEmployee = (Employee)getArguments().getSerializable(MainActivity.SHARE_EMPLOYEE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_generate_qr, container, false);

        try {

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(
                            MainActivity.SHARE_FULLNAME + currentEmployee.getFullname() +
                                    MainActivity.SHARE_PHONE_MOBILE + currentEmployee.getMobile_phone() +
                                    MainActivity.SHARE_EMAIL + currentEmployee.getEmail() +
                                    MainActivity.SHARE_COMPANY + currentEmployee.getCompany() +
                                    MainActivity.SHARE_POSITION + currentEmployee.getPosition() +
                                    MainActivity.SHARE_STREET + currentEmployee.getAddress().getStreet() +
                                    MainActivity.SHARE_CITY + currentEmployee.getAddress().getCity() +
                                    MainActivity.SHARE_WEBSITE + currentEmployee.getWebsite() +
                                    MainActivity.SHARE_PHONE_WORK + currentEmployee.getWork_phone() +
                                    MainActivity.SHARE_PHONE_HOME + currentEmployee.getHome_phone(),
                                    BarcodeFormat.QR_CODE, 300, 300);

            qrImageView = v.findViewById(R.id.qrImageView);
            qrImageView.setImageBitmap(bitmap);
            pulsatorLayout = v.findViewById(R.id.pulsator);
            pulsatorLayout.start();
            nameTextView = v.findViewById(R.id.fullNameTextView);
            nameTextView.setText(currentEmployee.getFullname());
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        cancelBtn = v.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().popBackStack();
                //getActivity().onBackPressed();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

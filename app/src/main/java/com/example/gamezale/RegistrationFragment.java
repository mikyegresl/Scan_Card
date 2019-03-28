package com.example.gamezale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamezale.Database.Address;
import com.example.gamezale.Database.Employee;
import com.example.gamezale.Database.EmployeeViewModel;

public class RegistrationFragment extends Fragment {

    private EditText fullnameEditText;

    private EditText phoneEditText;
    private EditText phoneWorkEditText;
    private EditText phoneHomeEditText;
    private ImageView addAddressBtn;
    private ImageView addPhoneBtn;
    private boolean addressBtnState;
    private boolean phoneBtnState;
    private LinearLayout phoneWorkLayout;
    private LinearLayout phoneHomeLayout;
    private EditText phoneTypeWork;
    private EditText phoneTypeHome;

    private EditText emailEditText;
    private EditText companyEditText;
    private EditText positionEditText;
    private EditText streetEditText;
    private EditText cityEditText;
    private EditText websiteEditText;
    private TextView saveBtn;
    private TextView cancelBtn;
    private ImageView iconImageView;

    private String fullname;
    private String mobile_phone;
    private String work_phone;
    private String home_phone;
    private String email;
    private String company;
    private String position;
    private Address address;
    private String website;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initLayout(view);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (phoneEditText.getText().toString().isEmpty()
//                || emailEditText.getText().toString().isEmpty()
//                || fullnameEditText.getText().toString().isEmpty()
//                || companyEditText.getText().toString().isEmpty()
//                || positionEditText.getText().toString().isEmpty()
//                || streetEditText.getText().toString().isEmpty()
//                || cityEditText.getText().toString().isEmpty()
//                || websiteEditText.getText().toString().isEmpty()) {

                if (phoneEditText.getText().toString().isEmpty()
                        || fullnameEditText.getText().toString().isEmpty()) {

                    makeToast("Mobile Phone and Full Name fields must be filled!");
                }
                else {
                    //to do: check fields for regexp
                    fullname = fullnameEditText.getText().toString();
                    mobile_phone = phoneEditText.getText().toString();

                    EmployeeViewModel employeeRepositoty = new EmployeeViewModel(getActivity().getApplication());
                    Employee current = new Employee(mobile_phone, fullname);

                    if (emailEditText.getText() != null) {
                        email = emailEditText.getText().toString();
                        current.setEmail(email);
                    }
                    if (companyEditText.getText() != null) {
                        company = companyEditText.getText().toString();
                        current.setCompany(company);
                    }
                    if (positionEditText.getText() != null) {
                        position = positionEditText.getText().toString();
                        current.setPosition(position);
                    }
                    if (streetEditText.getText() != null && cityEditText.getText() != null) {
                        address = new Address(streetEditText.getText().toString(), cityEditText.getText().toString());
                        current.setAddress(address);

                    }
                    if (websiteEditText.getText() != null) {
                        website = websiteEditText.getText().toString();
                        current.setWebsite(website);
                    }

                    if (phoneWorkEditText.getText() != null) {

                        current.setWork_phone(phoneWorkEditText.getText().toString());
                    }
                    if (phoneHomeEditText.getText() != null) {

                        current.setHome_phone(phoneHomeEditText.getText().toString());
                    }

                    employeeRepositoty.clearData();
                    employeeRepositoty.insert(current);

                    boolean result = getActivity().getSupportFragmentManager().popBackStackImmediate();
                    if (result == false) {

                        MainFragment mainFragment = MainFragment.newInstance(current);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, mainFragment)
                                .commit();
                    }


                }
            }
        });

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressBtnState == false) {

                    streetEditText.setVisibility(View.GONE);
                    cityEditText.setVisibility(View.GONE);
                    addAddressBtn.setImageResource(R.mipmap.add);
                    addressBtnState = true;
                }
                else if (addressBtnState == true) {

                    streetEditText.setVisibility(View.VISIBLE);
                    cityEditText.setVisibility(View.VISIBLE);
                    addAddressBtn.setImageResource(R.mipmap.remove);
                    addressBtnState = false;
                }
            }
        });


        addPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneBtnState == false) {

                    phoneWorkEditText.setVisibility(View.GONE);
                    phoneHomeEditText.setVisibility(View.GONE);
                    phoneTypeHome.setVisibility(View.GONE);
                    phoneTypeWork.setVisibility(View.GONE);
                    phoneHomeLayout.setVisibility(View.GONE);
                    phoneWorkLayout.setVisibility(View.GONE);
                    addPhoneBtn.setImageResource(R.mipmap.add);
                    phoneBtnState = true;
                }
                else if (phoneBtnState == true) {

                    phoneWorkEditText.setVisibility(View.VISIBLE);
                    phoneHomeEditText.setVisibility(View.VISIBLE);
                    phoneTypeHome.setVisibility(View.VISIBLE);
                    phoneTypeWork.setVisibility(View.VISIBLE);
                    phoneHomeLayout.setVisibility(View.VISIBLE);
                    phoneWorkLayout.setVisibility(View.VISIBLE);
                    addPhoneBtn.setImageResource(R.mipmap.remove);
                    phoneBtnState = false;
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    private void makeToast(String message)
    {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initLayout(View view)
    {
        phoneEditText = view.findViewById(R.id.phoneMobileEditText);
        phoneWorkEditText = view.findViewById(R.id.phoneWorkEditText);
        phoneHomeEditText = view.findViewById(R.id.phoneHomeEditText);

        addPhoneBtn = view.findViewById(R.id.phoneBtn);
        addAddressBtn = view.findViewById(R.id.addressBtn);
        addressBtnState = false;
        phoneBtnState = false;

        phoneTypeWork = view.findViewById(R.id.phoneTypeWork);
        phoneWorkLayout = view.findViewById(R.id.phoneWorkLayout);
        phoneTypeHome = view.findViewById(R.id.phoneTypeHome);
        phoneHomeLayout = view.findViewById(R.id.phoneHomeLayout);

        fullnameEditText = view.findViewById(R.id.fullnameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        companyEditText = view.findViewById(R.id.companyEditText);
        positionEditText = view.findViewById(R.id.positionEditText);
        streetEditText = view.findViewById(R.id.streetEditText);
        cityEditText = view.findViewById(R.id.cityEditText);
        websiteEditText = view.findViewById(R.id.websiteEditText);
        saveBtn = view.findViewById(R.id.saveBtn);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        iconImageView = view.findViewById(R.id.iconImageView);
    }
}

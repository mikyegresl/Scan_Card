package com.example.gamezale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gamezale.Database.Address;
import com.example.gamezale.Database.Employee;
import com.example.gamezale.Database.EmployeeViewModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final String SHARE_FULLNAME = "com.mikyegresl.android.bCode.fname";
    public static final String SHARE_PHONE_MOBILE = "com.mikyegresl.android.bCode.phone.mobile";
    public static final String SHARE_PHONE_WORK = "com.mikyegresl.android.bCode.phone.work";
    public static final String SHARE_PHONE_HOME = "com.mikyegresl.android.bCode.phone.home";
    public static final String SHARE_EMAIL = "com.mikyegresl.android.bCode.email";
    public static final String SHARE_COMPANY = "com.mikyegresl.android.bCode.company";
    public static final String SHARE_POSITION = "com.mikyegresl.android.bCode.position";
    public static final String SHARE_STREET = "com.mikyegresl.android.bCode.street";
    public static final String SHARE_CITY = "com.mikyegresl.android.bCode.city";
    public static final String SHARE_WEBSITE = "com.mikyegresl.android.bCode.website";
    public static final String SHARE_EMPLOYEE = "com.mikyegresl.android.bCode.employee";
    public static final String SHARE_NOTE = "com.mikyegresl.android.bCode.district";
    public static final String SHARE_CONTACT = "com.mikyegresl.android.bCode.contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //read current user
        EmployeeViewModel employeeViewModel = new EmployeeViewModel(getApplication());
        Employee employee = employeeViewModel.getCurrentEmployee();
        if (employee != null) {

            MainFragment myProfile = MainFragment.newInstance(employee);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, myProfile)
                    .commit();
        }
        else if (employee == null) {

            RegistrationFragment registrationFragment = new RegistrationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, registrationFragment)
                    .commit();
        }

//        String work1 = "Tel.: +99871 2155638";
//        String work2 = "+998 71 231 93 03";
//
//        String mobile = "+998 90 968 68 00";
//        String fax = "Fax: +99871 2545641";
//
//        Pattern work = Pattern.compile("(Tel.:)?\\s?\\+998\\s?71\\s?[\\d\\s]+");
//        Matcher matcher = work.matcher(fax);
//        if (matcher.matches()) {
//            showToast("Matches!");
//        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

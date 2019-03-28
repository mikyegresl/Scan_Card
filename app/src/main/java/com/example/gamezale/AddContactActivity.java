package com.example.gamezale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.gamezale.Database.Address;
import com.example.gamezale.Database.Contact;
import com.example.gamezale.Database.ContactsViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    private EditText addPhoneEditText;
    private EditText addNameEditText;
    private EditText addEmailEditText;
    private EditText addGroupEditText;
    private EditText addCompanyEditText;
    private EditText addPositionEditText;
    private EditText addStreetEditText;
    private EditText addCityEditText;
    private EditText addWebsiteEditText;
    private ImageView iconImageView;
    private TextView saveBtn;
    private TextView cancelBtn;

    private ImageView addAddressBtn;
    private boolean addressBtnState;
    private ImageView addPhoneBtn;
    private boolean phoneBtnState;

    private EditText addPhoneWork;
    private EditText addPhoneHome;
    private EditText addTypeWork;
    private EditText addTypeHome;

    private String name;
    private String phone;
    private String email;
    private String group;
    private String company;
    private String position;
    private String website;

    private Contact oldContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initLayout();
        if (getIntent().getStringExtra("qrData") != null) {

            String qrData = (String)getIntent().getStringExtra("qrData");

            if (qrData.contains(MainActivity.SHARE_FULLNAME)
                    && qrData.contains(MainActivity.SHARE_PHONE_MOBILE)
                    && qrData.contains(MainActivity.SHARE_EMAIL)
                    && qrData.contains(MainActivity.SHARE_COMPANY)
                    && qrData.contains(MainActivity.SHARE_POSITION)
                    && qrData.contains(MainActivity.SHARE_STREET)
                    && qrData.contains(MainActivity.SHARE_CITY)
                    && qrData.contains(MainActivity.SHARE_WEBSITE)) {

                addNameEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_FULLNAME)
                        + MainActivity.SHARE_FULLNAME.length(), qrData.indexOf(MainActivity.SHARE_PHONE_MOBILE)));
                addPhoneEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_PHONE_MOBILE)
                        + MainActivity.SHARE_PHONE_MOBILE.length(), qrData.indexOf(MainActivity.SHARE_EMAIL)));
                addEmailEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_EMAIL)
                        + MainActivity.SHARE_EMAIL.length(), qrData.indexOf(MainActivity.SHARE_COMPANY)));
                addCompanyEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_COMPANY)
                        + MainActivity.SHARE_COMPANY.length(), qrData.indexOf(MainActivity.SHARE_POSITION)));
                addPositionEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_POSITION)
                        + MainActivity.SHARE_POSITION.length(), qrData.indexOf(MainActivity.SHARE_STREET)));
                addStreetEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_STREET)
                        + MainActivity.SHARE_STREET.length(), qrData.indexOf(MainActivity.SHARE_CITY)));
                addCityEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_CITY)
                        + MainActivity.SHARE_CITY.length(), qrData.indexOf(MainActivity.SHARE_WEBSITE)));
                addWebsiteEditText.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_WEBSITE)
                        + MainActivity.SHARE_WEBSITE.length(), qrData.indexOf(MainActivity.SHARE_PHONE_WORK)));

                addPhoneWork.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_PHONE_WORK)
                        + MainActivity.SHARE_PHONE_WORK.length(), qrData.indexOf(MainActivity.SHARE_PHONE_HOME)));

                addPhoneHome.setText(qrData.substring(qrData.indexOf(MainActivity.SHARE_PHONE_HOME)
                        + MainActivity.SHARE_PHONE_HOME.length(), qrData.length()));
            }

            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Sorry...");
                builder.setMessage("Following QR code is different from bCard format");

                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        finish();
                    }
                });

                builder.show();
            }
        }

        else if (getIntent().getSerializableExtra(MainActivity.SHARE_CONTACT) != null) {

            Contact contact = (Contact)getIntent().getSerializableExtra(MainActivity.SHARE_CONTACT);
            oldContact = contact;
            addNameEditText.setText(contact.getFull_name());
            addPhoneEditText.setText(contact.getMobile_phone());

            if (contact.getEmail() != null) {
                addEmailEditText.setText(contact.getEmail());
            }
            if (contact.getGroup() != null) {
                addGroupEditText.setText(contact.getGroup());
            }
            if (contact.getCompany() != null) {
                addCompanyEditText.setText(contact.getCompany());
            }
            if (contact.getPosition() != null) {
                addPositionEditText.setText(contact.getPosition());
            }
            if (contact.getAddress().getStreet() != null) {
                addStreetEditText.setText(contact.getAddress().getStreet());
            }
            if (contact.getAddress().getCity() != null) {
                addCityEditText.setText(contact.getAddress().getCity());
            }
            if (contact.getWebsite() != null) {
                addWebsiteEditText.setText(contact.getWebsite());
            }

            if (contact.getWork_phone() != null) {
                addPhoneWork.setText(contact.getWork_phone());
            }
            if (contact.getHome_phone() != null) {
                addPhoneHome.setText(contact.getHome_phone());
            }

        }

        else if (getIntent().getStringExtra("scanData") != null) {

            String name = getIntent().getStringExtra(MainActivity.SHARE_FULLNAME);
            String mobilePhone = getIntent().getStringExtra(MainActivity.SHARE_PHONE_MOBILE);
            String workPhone = getIntent().getStringExtra(MainActivity.SHARE_PHONE_WORK);
            String email = getIntent().getStringExtra(MainActivity.SHARE_EMAIL);
            String company = getIntent().getStringExtra(MainActivity.SHARE_COMPANY);
            String position = getIntent().getStringExtra(MainActivity.SHARE_POSITION);
            String website = getIntent().getStringExtra(MainActivity.SHARE_WEBSITE);

            addNameEditText.setText(name);
            addPhoneEditText.setText(mobilePhone);
            addPhoneWork.setText(workPhone);
            addEmailEditText.setText(email);
            addCompanyEditText.setText(company);
            addPositionEditText.setText(position);
            addWebsiteEditText.setText(website);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = addNameEditText.getText().toString();
                phone = addPhoneEditText.getText().toString();
                email = addEmailEditText.getText().toString();
                group = addGroupEditText.getText().toString();
                company = addCompanyEditText.getText().toString();
                position = addPositionEditText.getText().toString();
                String street = addStreetEditText.getText().toString();
                String city = addCityEditText.getText().toString();
                website = addWebsiteEditText.getText().toString();

                if (name.isEmpty()
                        || phone.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
                    builder.setTitle("Mobile Phone and Full Name fields must be filled!");
                    builder.setMessage("Please fill these fields to proceed");

                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
                else {

                    //save data
                    Contact contact = new Contact(phone, name);

                    if (addEmailEditText.getText() != null) {
                        contact.setEmail(email);
                    }
                    if (addCompanyEditText.getText() != null) {
                        contact.setCompany(company);
                    }
                    if (addPositionEditText.getText() != null) {
                        contact.setPosition(position);
                    }
                    if (addStreetEditText.getText() != null && addCityEditText.getText() != null) {
                        contact.setAddress(new Address(street, city));
                    }
                    if (addGroupEditText.getText() != null) {
                        contact.setGroup(group);
                    }
                    if (addWebsiteEditText.getText() != null) {
                        contact.setWebsite(website);
                    }
                    if (addPhoneWork.getText() != null) {

                        contact.setWork_phone(addPhoneWork.getText().toString());
                    }
                    if (addPhoneHome.getText() != null) {

                        contact.setHome_phone(addPhoneHome.getText().toString());
                    }

                    ContactsViewModel contactsViewModel = new ContactsViewModel(getApplication());

                    if (oldContact != null) {
                        contactsViewModel.delete(oldContact);
                    }
                    contactsViewModel.insert(contact);
                    finish();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressBtnState == false) {

                    addStreetEditText.setVisibility(View.GONE);
                    addCityEditText.setVisibility(View.GONE);
                    addAddressBtn.setImageResource(R.mipmap.add);
                    addressBtnState = true;
                }
                else if (addressBtnState == true) {

                    addStreetEditText.setVisibility(View.VISIBLE);
                    addCityEditText.setVisibility(View.VISIBLE);
                    addAddressBtn.setImageResource(R.mipmap.remove);
                    addressBtnState = false;
                }
            }
        });


        addPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneBtnState == false) {

                    addPhoneWork.setVisibility(View.GONE);
                    addPhoneHome.setVisibility(View.GONE);
                    addTypeHome.setVisibility(View.GONE);
                    addTypeWork.setVisibility(View.GONE);
                    findViewById(R.id.phoneHomeLayout).setVisibility(View.GONE);
                    findViewById(R.id.phoneWorkLayout).setVisibility(View.GONE);
                    addPhoneBtn.setImageResource(R.mipmap.add);
                    phoneBtnState = true;
                }
                else if (phoneBtnState == true) {

                    addPhoneWork.setVisibility(View.VISIBLE);
                    addPhoneHome.setVisibility(View.VISIBLE);
                    addTypeHome.setVisibility(View.VISIBLE);
                    addTypeWork.setVisibility(View.VISIBLE);
                    findViewById(R.id.phoneHomeLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.phoneWorkLayout).setVisibility(View.VISIBLE);
                    addPhoneBtn.setImageResource(R.mipmap.remove);
                    phoneBtnState = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initLayout() {

        addPhoneEditText = findViewById(R.id.addPhoneMobileEditText);
        addNameEditText = findViewById(R.id.addNameEditText);
        addEmailEditText = findViewById(R.id.addEmailEditText);
        addGroupEditText = findViewById(R.id.addGroupEditText);
        addCompanyEditText = findViewById(R.id.addCompanyEditText);
        addPositionEditText = findViewById(R.id.addPositionEditText);
        addStreetEditText = findViewById(R.id.addStreetEditText);
        addCityEditText = findViewById(R.id.addCityEditText);
        addWebsiteEditText = findViewById(R.id.addWebsiteEditText);
        iconImageView = findViewById(R.id.addIconImageView);
        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        addAddressBtn = findViewById(R.id.addAdressBtn);
        addressBtnState = false;
        addPhoneBtn = findViewById(R.id.addPhoneBtn);
        phoneBtnState = false;

        addPhoneWork = findViewById(R.id.addPhoneWorkEditText);
        addPhoneHome = findViewById(R.id.addPhoneHomeEditText);
        addTypeWork = findViewById(R.id.phoneTypeWork);
        addTypeHome = findViewById(R.id.phoneTypeHome);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.example.gamezale;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamezale.Database.Contact;
import com.example.gamezale.Database.ContactsViewModel;
import com.example.gamezale.Database.Employee;
import com.example.gamezale.NFC.NFC;
import com.example.gamezale.QR.GenerateQRFragment;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.sheetmenu.SheetMenu;

public class ContactDetailFragment extends Fragment {

    private Context context;
    private ContactsViewModel contactsViewModel;
    private Contact selectedContact;
    private TextView fullNameTextView;
    private TextView jobTextView;

    private FloatingActionButton shareBtn;
    private LinearLayout editBtn;

    private RecyclerView contactDetails;
    private ContactDetailAdapter adapter;

    private List<ContactDetail> details = new ArrayList<>();

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    public static ContactDetailFragment newInstance(final Contact contact) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.SHARE_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            selectedContact = (Contact)getArguments().getSerializable(MainActivity.SHARE_CONTACT);
            ContactDetail mobileDetail = new ContactDetail(R.mipmap.icon_call, "Mobile", selectedContact.getMobile_phone());

            if (selectedContact.getGroup() != null) {

                if (!selectedContact.getGroup().isEmpty()) {
                    ContactDetail groupDetail = new ContactDetail(R.mipmap.icon_users, "Group", selectedContact.getGroup());
                    details.add(groupDetail);
                }
            }

            if (selectedContact.getAddress() != null) {

                if (!selectedContact.getAddress().getStreet().isEmpty()
                        || !selectedContact.getAddress().getCity().isEmpty()) {
                    ContactDetail addressDetail = new ContactDetail(
                            R.mipmap.icon_loc, "Address", selectedContact.getAddress().getStreet()
                            + ", " + selectedContact.getAddress().getCity());
                    details.add(addressDetail);
                }
            }

            if (selectedContact.getEmail() != null) {

                if (!selectedContact.getEmail().isEmpty()) {
                    ContactDetail emailDetail = new ContactDetail(
                            R.mipmap.icon_msg, "Email", selectedContact.getEmail());
                    details.add(emailDetail);
                }
            }

            if (selectedContact.getWork_phone() != null) {

                if (!selectedContact.getWork_phone().isEmpty()) {
                    ContactDetail workDetail = new ContactDetail(
                            R.mipmap.icon_call, "Work", selectedContact.getWork_phone());
                    details.add(workDetail);
                }
            }

            if (selectedContact.getHome_phone() != null) {
                if (!selectedContact.getHome_phone().isEmpty()) {
                    ContactDetail homeDetail = new ContactDetail(
                            R.mipmap.icon_call, "Home", selectedContact.getHome_phone());
                    details.add(homeDetail);
                }
            }

            details.add(mobileDetail);
            adapter.setContactDetails(details);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);

        fullNameTextView = view.findViewById(R.id.fullNameTextView);
        jobTextView = view.findViewById(R.id.jobTextView);
        contactDetails = view.findViewById(R.id.contactDetails);
        contactDetails.setAdapter(adapter);
        contactDetails.setLayoutManager(new LinearLayoutManager(context));

        fullNameTextView.setText(selectedContact.getFull_name());
        if (selectedContact.getPosition() != null && selectedContact.getCompany() != null) {
            if (!selectedContact.getPosition().isEmpty() && !selectedContact.getCompany().isEmpty()) {
                jobTextView.setText(selectedContact.getPosition() + ", " + selectedContact.getCompany());
            }
        }

        shareBtn = view.findViewById(R.id.shareBtn);
        editBtn = view.findViewById(R.id.editBtn);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SheetMenu.with(getContext())
                        .setTitle("Send contact via")
                        .setMenu(R.menu.detail_menu)
                        .setAutoCancel(true)
                        .setLayoutManager(new GridLayoutManager(getContext(), 3))
                        .setClick(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                if (item.getItemId() == R.id.action_qr) {
                                    Employee e = new Employee(selectedContact.getMobile_phone(),
                                            selectedContact.getFull_name());

                                    if (selectedContact.getEmail() != null) {
                                        e.setEmail(selectedContact.getEmail());
                                    }
                                    if (selectedContact.getAddress() != null) {
                                        e.setAddress(selectedContact.getAddress());
                                    }
                                    if (selectedContact.getCompany() != null) {
                                        e.setCompany(selectedContact.getCompany());
                                    }
                                    if (selectedContact.getPosition() != null) {
                                        e.setPosition(selectedContact.getPosition());
                                    }
                                    if (selectedContact.getWebsite() != null) {
                                        e.setWebsite(selectedContact.getWebsite());
                                    }

                                    if (selectedContact.getWork_phone() != null) {
                                        e.setWork_phone(selectedContact.getWork_phone());
                                    }
                                    if (selectedContact.getHome_phone() != null) {
                                        e.setHome_phone(selectedContact.getHome_phone());
                                    }

                                    GenerateQRFragment fragment = GenerateQRFragment.newInstance(e);
                                    getActivity()
                                            .getSupportFragmentManager()
                                            .beginTransaction()
                                            .addToBackStack(null)
                                            .replace(R.id.fragmentContainer, fragment)
                                            .commit();
                                    return true;
                                }
                                else if (item.getItemId() == R.id.action_nfc) {

                                    NFC fragment = new NFC();
                                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.fragmentContainer, fragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    return true;
                                }
                                return false;
                            }
                        }).show();


            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AddContactActivity.class);
                intent.putExtra(MainActivity.SHARE_CONTACT, selectedContact);
                startActivity(intent);


                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .detach(ContactDetailFragment.this)
                        .commit();

                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.adapter = new ContactDetailAdapter(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

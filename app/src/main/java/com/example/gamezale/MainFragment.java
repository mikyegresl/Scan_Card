package com.example.gamezale;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamezale.Database.Contact;
import com.example.gamezale.Database.ContactsViewModel;
import com.example.gamezale.Database.Employee;
import com.example.gamezale.Database.EmployeeViewModel;
import com.example.gamezale.NFC.NFC;
import com.example.gamezale.QR.GenerateQRFragment;
import com.example.gamezale.QR.ScanQR;
import com.example.gamezale.Scan.ScanCard;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.sheetmenu.SheetMenu;

public class MainFragment extends Fragment implements ContactViewAdapter.ContactListClickListener {

    private MainFragment contactsFragment = this;
    private EditText searchEditText;

    FloatingActionMenu menuFab;
    private com.github.clans.fab.FloatingActionButton scanCardBtn;
    private com.github.clans.fab.FloatingActionButton scanQRBtn;
    private com.github.clans.fab.FloatingActionButton typeManualBtn;
    private com.github.clans.fab.FloatingActionButton nfcBtn;
    private FloatingActionButton shareBtn;
    private ImageView moreBtn;

    private Employee currentEmployee;
    private List<Contact> contactsList = new ArrayList<>();

    private RecyclerView contactsRecyclerView;
    private ContactViewAdapter contactsAdapter;
    private ContactsViewModel contactsViewModel;
    private EmployeeViewModel employeeViewModel;
    private Context context;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView fullNameTextView;
    private TextView jobTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView addressTextView;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(final Employee employee) {

        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.SHARE_EMPLOYEE, employee);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        if (getArguments() != null) {
            currentEmployee = (Employee)getArguments().getSerializable(MainActivity.SHARE_EMPLOYEE);
        }

        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);

        employeeViewModel.getCurrentEmployeeLive().observe(this, new Observer<Employee>() {
            @Override
            public void onChanged(@Nullable Employee employee) {

                currentEmployee = employee;
                fullNameTextView.setText(currentEmployee.getFullname());
                phoneTextView.setText(currentEmployee.getMobile_phone());
                if (currentEmployee.getPosition() != null && currentEmployee.getCompany() != null) {

                    if (!currentEmployee.getPosition().isEmpty() && !currentEmployee.getCompany().isEmpty()) {
                        jobTextView.setText(currentEmployee.getPosition() + " ," + currentEmployee.getCompany());
                    }
                }
                if (currentEmployee.getEmail() != null) {
                    if (!currentEmployee.getEmail().isEmpty()) {
                        emailTextView.setText(currentEmployee.getEmail());
                    }
                }
                if (currentEmployee.getAddress() != null) {
                    if (!currentEmployee.getAddress().getStreet().isEmpty() && !currentEmployee.getAddress().getCity().isEmpty())
                    {
                        addressTextView.setText(currentEmployee.getAddress().getStreet()
                                + " , " + currentEmployee.getAddress().getCity());
                    }
                }
            }
        });

        contactsViewModel.getContactList().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                contactsAdapter.setContactList(contacts);
                contactsList = contacts;
            }
        });
        contactsAdapter = new ContactViewAdapter(context, this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        initLayout(v);
        fullNameTextView.setText(currentEmployee.getFullname());
        phoneTextView.setText(currentEmployee.getMobile_phone());
        if (currentEmployee.getPosition() != null && currentEmployee.getCompany() != null) {

            if (!currentEmployee.getPosition().isEmpty() && !currentEmployee.getCompany().isEmpty()) {
                jobTextView.setText(currentEmployee.getPosition() + " ," + currentEmployee.getCompany());
            }
        }
        if (currentEmployee.getEmail() != null) {
            if (!currentEmployee.getEmail().isEmpty()) {
                emailTextView.setText(currentEmployee.getEmail());
            }
        }
        if (currentEmployee.getAddress() != null) {
            if (!currentEmployee.getAddress().getStreet().isEmpty() && !currentEmployee.getAddress().getCity().isEmpty())
            {
                addressTextView.setText(currentEmployee.getAddress().getStreet()
                        + " , " + currentEmployee.getAddress().getCity());
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.mipmap.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        contactsRecyclerView.setAdapter(contactsAdapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        Log.v("CurrentEmployee", currentEmployee.getMobile_phone() + " " + currentEmployee.getFullname());

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

                                    GenerateQRFragment fragment = GenerateQRFragment.newInstance(currentEmployee);
                                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.fragmentContainer, fragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
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

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.edit_my_info);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.edit_my_info) {

                            RegistrationFragment fragment = new RegistrationFragment();
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.fragmentContainer, fragment)
                                    .commit();

                            return true;
                        }
                        else {

                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        scanQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scan qr code
                startActivity(new Intent(getContext(), ScanQR.class));
                menuFab.close(true);
            }
        });

        scanCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScanCard.class));
                menuFab.close(true);
            }
        });

        typeManualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
                menuFab.close(true);
            }
        });

        nfcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NFC fragment = new NFC();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                menuFab.close(true);
            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initLayout(final View v) {

        searchEditText = v.findViewById(R.id.searchContactEditText);

        scanCardBtn = v.findViewById(R.id.scanCardBtn);
        scanQRBtn = v.findViewById(R.id.scanQRBtn);
        typeManualBtn = v.findViewById(R.id.typeManualBtn);
        nfcBtn = v.findViewById(R.id.nfcBtn);
        menuFab = v.findViewById(R.id.menuFab);

        contactsRecyclerView = v.findViewById(R.id.contactsRecyclerView);
        drawerLayout = v.findViewById(R.id.drawer_layout);
        navigationView = v.findViewById(R.id.nav_view);
        toolbar = v.findViewById(R.id.toolbar);
        View header = navigationView.getHeaderView(0);
        shareBtn = header.findViewById(R.id.shareBtn);

        moreBtn = header.findViewById(R.id.moreBtn);
        int [] icons = {R.mipmap.icon_edit_info};
        String [] text = {"Edit my info"};
        CustomAdapter adapter = new CustomAdapter(getActivity().getApplicationContext(), text, icons);
        //moreBtn.setAdapter(adapter);

        fullNameTextView = header.findViewById(R.id.headerFullName);
        jobTextView = header.findViewById(R.id.headerCompany);
        phoneTextView = header.findViewById(R.id.headerPhone);
        emailTextView = header.findViewById(R.id.headerEmail);
        addressTextView = header.findViewById(R.id.headerAddress);
    }

    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onDetailIconClicked(View view, int position) {

        if (view.getId() == R.id.detailsHolder) {

            ContactDetailFragment fragment = ContactDetailFragment.newInstance(contactsList.get(position));
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        }


    }

    @Override
    public void onCallIconClicked(View view, int position) {

    }

    @Override
    public void onMsgIconClicked(View view, int position) {

    }

    @Override
    public void onShareIconClicked(View view, final int position) {
        if (view.getId() == R.id.shareHolder) {

            SheetMenu.with(getContext())
                    .setTitle("Send contact via")
                    .setMenu(R.menu.detail_menu)
                    .setAutoCancel(true)
                    .setLayoutManager(new GridLayoutManager(getContext(), 3))
                    .setClick(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getItemId() == R.id.action_qr) {

                                Employee e = new Employee(contactsList.get(position).getMobile_phone(),
                                        contactsList.get(position).getFull_name());

                                if (contactsList.get(position).getEmail() != null) {
                                    e.setEmail(contactsList.get(position).getEmail());
                                }
                                if (contactsList.get(position).getAddress() != null) {
                                    e.setAddress(contactsList.get(position).getAddress());
                                }
                                if (contactsList.get(position).getCompany() != null) {
                                    e.setCompany(contactsList.get(position).getCompany());
                                }
                                if (contactsList.get(position).getPosition() != null) {
                                    e.setPosition(contactsList.get(position).getPosition());
                                }
                                if (contactsList.get(position).getWebsite() != null) {
                                    e.setWebsite(contactsList.get(position).getWebsite());
                                }

                                if (contactsList.get(position).getWork_phone() != null) {
                                    e.setWork_phone(contactsList.get(position).getWork_phone());
                                }
                                if (contactsList.get(position).getHome_phone() != null) {
                                    e.setHome_phone(contactsList.get(position).getHome_phone());
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
    }

    private class CustomAdapter extends BaseAdapter {

        Context context;
        String [] text;
        int [] icon;
        LayoutInflater inflater;

        public CustomAdapter(Context context, String [] text, int [] icon) {
            this.context = context;
            this.text = text;
            this.icon = icon;
            this.inflater = LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            return icon.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.custom_spinner_item, null);
            ImageView icon = convertView.findViewById(R.id.editCustomIV);
            TextView tv = convertView.findViewById(R.id.editCustomTV);
            icon.setImageResource(this.icon[position]);
            tv.setText(this.text[position]);
            return convertView;
        }
    }

    private void makeToast(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}

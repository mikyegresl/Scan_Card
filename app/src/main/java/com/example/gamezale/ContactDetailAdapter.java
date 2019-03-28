package com.example.gamezale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamezale.Database.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailAdapter extends RecyclerView.Adapter<ContactDetailAdapter.ContactDetailHolder> {

    private Context context;
    private List<ContactDetail> contactDetails;

    public ContactDetailAdapter(Context context)
    {
        this.context = context;
    }

    public void setContactDetails(List<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_item, viewGroup, false);
        return new ContactDetailHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactDetailHolder contactDetailHolder, int i)
    {

        if (contactDetails == null)
        {
            return;
        }

        final ContactDetail detail = contactDetails.get(i);
        if (detail != null) {
            contactDetailHolder.infoTypeTextView.setText(detail.getInfoType());
            contactDetailHolder.infoValueTextView.setText(detail.getInfoValue());
            contactDetailHolder.infoIcon.setImageResource(detail.getImageURL());
        }
    }

    @Override
    public int getItemCount()
    {
        if (contactDetails == null) {
            return 0;
        }
        else {
            return contactDetails.size();
        }
    }

    public class ContactDetailHolder extends RecyclerView.ViewHolder {

        public TextView infoTypeTextView;
        public TextView infoValueTextView;
        public ImageView infoIcon;

        public ContactDetailHolder(@NonNull View itemView) {
            super(itemView);

            //3 lines

            this.infoTypeTextView = itemView.findViewById(R.id.typeTextView);
            this.infoValueTextView = itemView.findViewById(R.id.valueTextView);
            this.infoIcon = itemView.findViewById(R.id.iconImageView);
        }

        //1 method
    }
}

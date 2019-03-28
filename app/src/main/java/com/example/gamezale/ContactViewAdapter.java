package com.example.gamezale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gamezale.Database.Contact;

import java.util.List;

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ContactViewHolder> {

//    private View.OnClickListener onItemClickListener;
//    private View.OnClickListener onDetailsClickListener;
    private ContactListClickListener mListener;
    private List<Contact> contactList;
    private Context context;

    public ContactViewAdapter(Context context, ContactListClickListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    public void setContactList(List<Contact> contactList)
    {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);
        return new ContactViewHolder(itemView, mListener);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ContactViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        holder.itemView.setOnClickListener(onItemClickListener);
//        holder.detailsBtn.setOnClickListener(onDetailsClickListener);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ContactViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
//        holder.contactItem.setOnClickListener(null);
//        holder.detailsBtn.setOnClickListener(null);
    }

//    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

//    public void setOnDetailsClickListener(View.OnClickListener onDetailsClickListener)
//    {
//        this.onDetailsClickListener = onDetailsClickListener;
//    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {

        if (contactList == null)
        {
            return;
        }

        final Contact contact = contactList.get(i);
        if (contact != null) {
            contactViewHolder.fullNameTextView.setText(contact.getFull_name());
            contactViewHolder.phoneTextView.setText(contact.getMobile_phone());
//
//            for (int j = 0; j < contactList.size() - 1; j++) {
//                if (contactList.get(j).getFull_name().charAt(0) != contactList.get(j + 1).getFull_name().charAt(0)) {
//                    contactViewHolder.letterHolder.setVisibility(View.VISIBLE);
//                }
//            }
        }

        //2 lines
    }

    @Override
    public int getItemCount() {

        if (contactList == null) {
            return 0;
        }
        else {
            return contactList.size();
        }
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout contactItem;
        public TextView fullNameTextView;
        public TextView phoneTextView;
        public ImageView avatarImageView;
        public LinearLayout callBtn;
        public LinearLayout msgBtn;
        public LinearLayout shareBtn;
        public LinearLayout detailsBtn;

        public LinearLayout iconHolder;

        public LinearLayout letterHolder;
        public TextView firstLetter;

        public ContactViewHolder(@NonNull View itemView, final ContactListClickListener listener) {
            super(itemView);
            this.fullNameTextView = itemView.findViewById(R.id.fullNameTextView);
            this.phoneTextView = itemView.findViewById(R.id.phoneTextView);
            this.avatarImageView = itemView.findViewById(R.id.avatarImageView);
            this.contactItem = itemView.findViewById(R.id.contactItem);
            this.callBtn = itemView.findViewById(R.id.callHolder);
            this.msgBtn = itemView.findViewById(R.id.msgHolder);
            this.shareBtn = itemView.findViewById(R.id.shareHolder);
            this.detailsBtn = itemView.findViewById(R.id.detailsHolder);
            this.iconHolder = itemView.findViewById(R.id.iconHolder);

            this.letterHolder = itemView.findViewById(R.id.letterHolder);
            this.firstLetter = itemView.findViewById(R.id.firstLetter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onRowClicked(getAdapterPosition());
                    }
                }
            });

            contactItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iconHolder.getVisibility() == View.GONE) {
                        iconHolder.setVisibility(View.VISIBLE);
                    }
                    else if (iconHolder.getVisibility() == View.VISIBLE) {
                        iconHolder.setVisibility(View.GONE);
                    }
                }
            });

            detailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onDetailIconClicked(v, getAdapterPosition());
                    }
                }
            });
            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onCallIconClicked(v, getAdapterPosition());
                    }
                }
            });
            msgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onMsgIconClicked(v, getAdapterPosition());
                    }
                }
            });
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onShareIconClicked(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface ContactListClickListener {

        void onRowClicked(int position);
        //void onItemClicked(View view, int position);
        void onDetailIconClicked(View view, int position);
        void onCallIconClicked(View view, int position);
        void onMsgIconClicked(View view, int position);
        void onShareIconClicked(View view, int position);

    }
}

package com.mycontact.regnyawedantakusuma.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.mycontact.regnyawedantakusuma.InputActivity;
import com.mycontact.regnyawedantakusuma.R;
import com.mycontact.regnyawedantakusuma.TampilActivity;
import com.mycontact.regnyawedantakusuma.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> dataContact;
    private Context context;

    public ContactAdapter(List<Contact> dataContact, Context context) {
        this.dataContact = dataContact;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact tempContact = dataContact.get(position);
        holder.id = tempContact.getId();
        holder.firstName = tempContact.getFirstName();
        holder.lastName = tempContact.getLastName();
        holder.email = tempContact.getEmail();
        holder.gender = tempContact.getGender();
        holder.tvName.setText(holder.firstName + " " + holder.lastName);
        holder.tvNumber.setText(tempContact.getPhoneNumber());
        String imgLocation = tempContact.getPhoto();
        if(!imgLocation.equals(null)) {
            //Picasso.get().load(imgLocation).resize(64, 64).into(holder.imgAvatar);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(imgLocation)
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imgAvatar);
        }
        holder.imgAvatar.setContentDescription(tempContact.getPhoto());
    }

    @Override
    public int getItemCount() {
        return dataContact.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView tvName, tvNumber;
        private CircleImageView imgAvatar;
        private int id;
        private String firstName, lastName, email, gender;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
            imgAvatar = itemView.findViewById(R.id.profile_image);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent openDisplay = new Intent(context, TampilActivity.class);
            openDisplay.putExtra("FULL_NAME", firstName +" "+ lastName);
            openDisplay.putExtra("NUMBER", tvNumber.getText());
            openDisplay.putExtra("EMAIL", email);
            openDisplay.putExtra("IMAGE", imgAvatar.getContentDescription());
            openDisplay.putExtra("GENDER", gender);
            itemView.getContext().startActivity(openDisplay);
        }

        @Override
        public boolean onLongClick(View v) {
            Intent openInput = new Intent(context, InputActivity.class);
            openInput.putExtra("OPERATION", "update");
            openInput.putExtra("ID", id);
            openInput.putExtra("FIRST_NAME", firstName);
            openInput.putExtra("LAST_NAME", lastName);
            openInput.putExtra("NUMBER", tvNumber.getText());
            openInput.putExtra("EMAIL", email);
            openInput.putExtra("IMAGE", imgAvatar.getContentDescription());
            openInput.putExtra("GENDER", gender);
            itemView.getContext().startActivity(openInput);
            return true;
        }
    }
}
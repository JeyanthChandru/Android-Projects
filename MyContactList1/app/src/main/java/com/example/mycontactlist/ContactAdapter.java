package com.example.mycontactlist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jeyan on 4/12/2017.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> items;
    private Context adapterContext;

    public ContactAdapter(Context context, ArrayList<Contact> items) {
        super(context, R.layout.list_item, items);
        adapterContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Contact contact = items.get(position);
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }

            TextView contactName = (TextView) v.findViewById(R.id.textContactName);
            TextView contactNumber = (TextView) v.findViewById(R.id.textPhoneNumber);
            TextView cellNumber = (TextView) v.findViewById(R.id.textCellNumber);
            TextView contactAddress = (TextView) v.findViewById(R.id.textContactAddress);
            Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
            String homePhone = "Home : " + contact.getPhoneNumber() + ",";
            String cellPhone = "Cell : " + contact.getCellNumber();
            String Address = contact.getStreetAddress() + ", " + contact.getCity() + " " + contact.getState() + ", " + contact.getZipCode() + ",";
            contactName.setText(contact.getContactName());
            contactNumber.setText(homePhone);
            cellNumber.setText(cellPhone);
            contactAddress.setText(Address);
            b.setVisibility(View.INVISIBLE);

            if(position % 2 == 0)
                contactName.setTextColor(Color.parseColor("#FF0000"));
            else
                contactName.setTextColor(Color.parseColor("#0000FF"));
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }
    public void showDelete(final int position, final View convertView,final Context context, final Contact contact) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
        //2
        if (b.getVisibility()==View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete(position, convertView, context);
                    items.remove(contact);
                    deleteOption(contact.getContactID(), context);
                }
            });
        }
        else {
            hideDelete(position, convertView, context);
        }
    }
    //3
    private void deleteOption(int contactToDelete, Context context) {
        ContactDataSource db = new ContactDataSource(context);
        try {
            db.open();
            db.deleteContact(contactToDelete);
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Contact Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }
    //4
    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }
}

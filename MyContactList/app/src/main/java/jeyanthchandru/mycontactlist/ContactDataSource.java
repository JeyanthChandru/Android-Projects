package jeyanthchandru.mycontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.renderscript.Long2;
import android.util.StringBuilderPrinter;

/**
 * Created by jeyan on 2/25/2017.
 */

public class ContactDataSource {
    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;

    public ContactDataSource(Context context){
        dbHelper = new ContactDBHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public boolean insertContact(Contact c){
        boolean didSucceed = false;
        try{
            ContentValues initialValues = new ContentValues();

            initialValues.put("contactname",c.getContactName());
            initialValues.put("streetaddress",c.getStreetAddress());
            initialValues.put("city",c.getCity());
            initialValues.put("state",c.getState());
            initialValues.put("zipcode",c.getZipCode());
            initialValues.put("phonenumber",c.getPhoneNumber());
            initialValues.put("cellnumber",c.getCellNumber());
            initialValues.put("email",c.geteMail());
            initialValues.put("birthday",String.valueOf(c.getBirthday().getTimeInMillis()));

            didSucceed = database.insert("contact",null,initialValues) > 0;
        }
        catch (Exception e){

        }
        return didSucceed;
    }

    public boolean updateContact(Contact c){
        boolean didSucceed = false;
        try {
            Long rowId = (long) c.getContactID();
            ContentValues updateValues = new ContentValues();
            updateValues.put("contactname",c.getContactName());
            updateValues.put("streetaddress",c.getStreetAddress());
            updateValues.put("city",c.getCity());
            updateValues.put("state",c.getState());
            updateValues.put("zipcode",c.getZipCode());
            updateValues.put("phonenumber",c.getPhoneNumber());
            updateValues.put("cellnumber",c.getCellNumber());
            updateValues.put("email",c.geteMail());
            updateValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

            didSucceed = database.update("contact",updateValues,"_id="+rowId,null)>0;
        }
        catch (Exception e){

        }
        return didSucceed;
    }
}

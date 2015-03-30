package com.cs442.project.splitpay;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Members extends ActionBarActivity {
    private String memberName;
    private String groupsName;
    private String amountOwed;
    private String amountPaidFor;
    final Context context = this;
    private StoreDbHandler storeDbHandler;
    private String name;
    public static final int PICK_CONTACT = 1;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(String amountOwed) {
        this.amountOwed = amountOwed;
    }

    public String getAmountPaidFor() {
        return amountPaidFor;
    }

    public void setAmountPaidFor(String amountPaidFor) {
        this.amountPaidFor = amountPaidFor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mem_details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.members_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_add_user:
                Add_New_User();
                break;
            // action with ID action_settings was selected
            case R.id.action_add_pay:
                Request_Pay_Info();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void Add_New_User()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
        //Intent i = new Intent(this, Create_Group.class);
        //startActivity(i);
    }
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        switch(reqCode) {
            case (PICK_CONTACT) : {
                if (resCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    c.moveToFirst();
                     name = c.getString(c.getColumnIndexOrThrow(
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    c.close();
                    TextView tv = (TextView)findViewById(R.id.mem1);
                   tv.setText(name);
                   // mem = tv.getText().toString();
                }
                break;
            }
            default: break;
        }
    }

    public void Request_Pay_Info()
    {

        LayoutInflater lif = LayoutInflater.from(context);
        View promptsView = lif.inflate(R.layout.prompt_paydetails, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText forInput = (EditText) promptsView.findViewById(R.id.editTextDialogForInput);
        final EditText amtInput = (EditText) promptsView.findViewById(R.id.editTextDialogAmountInput);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("SPLIT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String purpose = forInput.getText().toString();
                                String amount = amtInput.getText().toString();

                                Members memberDetails = new Members();
                                memberDetails.setAmountOwed(amount);
                                memberDetails.setMemberName(name);
                                memberDetails.setAmountPaidFor(purpose);
                                Groups_Reg groupName = new Groups_Reg();
                                storeDbHandler = StoreDbHandler.getDbHandlerInstance(getApplicationContext(),null);
                                //storeDbHandler.addGroupMemberDetails(groupName,memberDetails);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}

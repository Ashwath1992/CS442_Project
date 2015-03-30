package com.cs442.project.splitpay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Groups_Reg extends ActionBarActivity {
    private String groupName;
    private StoreDbHandler storeDbHandler;

    final Context context = this;
    private Button button;
    TextView result;
    ListView listView;
    LinearLayout mytext;
    TextView groups;
    ArrayAdapter<String> group_names;
    ArrayList<String> m_listItems = new ArrayList<String>();
    //final int N = 10;
    //final TextView[] myTextViews = new TextView[N];

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Groups_Reg(){

    }
    public Groups_Reg(String groupName) {
        this.groupName = groupName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);

        button = (Button) findViewById(R.id.new_grp);
        //result = (TextView) findViewById(R.id.gp1);
        listView = (ListView) findViewById(R.id.list);
        storeDbHandler = StoreDbHandler.getDbHandlerInstance(getApplicationContext(),null);
        m_listItems = storeDbHandler.getGroupNameList();

        group_names = new ArrayAdapter<String>(this,
                R.layout.gname_list_row, R.id.list, m_listItems);
        listView.setAdapter(group_names);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(Groups_Reg.this, Members.class);
                startActivity(i);
            }
        });
        //mytext = (LinearLayout) findViewById(R.id.myLinearLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_groupname, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String gname = userInput.getText().toString();
                                        //if (null != gname && gname.length() > 0) {
                                        //}
                                        // get user input and set it to result
                                        //final TextView result = new TextView(Groups_Reg.this);
                                        //result.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                                        //result.setText(userInput.getText());
                                        //mytext.addView(result);
                                        Groups_Reg groupName = new Groups_Reg();
                                        groupName.setGroupName(gname);
                                        storeDbHandler = StoreDbHandler.getDbHandlerInstance(getApplicationContext(),null);
                                        if(storeDbHandler.addGroupName(groupName)){
                                            m_listItems.add(gname);
                                            group_names.notifyDataSetChanged();
                                        }
                                        //add to memberDetails page
                                        Members members = new Members();
                                        storeDbHandler.addGroupMemberDetails(groupName,members);
                                        //System.out.println("print group name " + gname);
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
        });
        // get prompts.xml view
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void memberDetails(View v){
        if (result.length() > 0) {
            Intent i = new Intent(this, Members.class);
            startActivity(i);
        }
    }

    /*public void create(View view)
    {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt_groupname, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                //final TextView result = new TextView(Groups_Reg.this);
                                //result.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                                result.setText(userInput.getText());
                                //mytext.addView(result);
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
        Intent i = new Intent(this, Create_Group.class);
        startActivity(i);
    }*/
}

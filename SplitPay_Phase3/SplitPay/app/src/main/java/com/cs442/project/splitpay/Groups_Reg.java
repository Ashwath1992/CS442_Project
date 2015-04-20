package com.cs442.project.splitpay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Groups_Reg extends ActionBarActivity {
    private String groupName;
    //private StoreDbHandler storeDbHandler;

    final Context context = this;
    private Button button;
    TextView result;
    ListView listView;
    LinearLayout mytext;
    TextView groups;
    int positionToRemove;
    ArrayAdapter<String> group_names;
    ArrayList<String> m_listItems = new ArrayList<String>();
    //final int N = 10;
    //final TextView[] myTextViews = new TextView[N];

    /*public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
*/
    public Groups_Reg(){

    }
    public Groups_Reg(String groupName) {
        this.groupName = groupName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_launcher);
        //ab.setDisplayHomeAsUpEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        button = (Button) findViewById(R.id.new_grp);
        //result = (TextView) findViewById(R.id.gp1);
        listView = (ListView) findViewById(R.id.list);
        //storeDbHandler = StoreDbHandler.getDbHandlerInstance(getApplicationContext(),null);
        //m_listItems = storeDbHandler.getGroupNameList();

        group_names = new ArrayAdapter<String>(this, R.layout.gname_list_row, R.id.list, m_listItems);
        listView.setAdapter(group_names);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionToRemove=position;
                Intent intent = new Intent(Groups_Reg.this, Members.class);
                System.out.println("grp pos:" + position + " list items size:" + m_listItems.size() + " item:" + m_listItems.get(position));
                intent.putExtra("GROUP_NAME", m_listItems.get(position));
                startActivity(intent);

                Bundle extras = intent.getExtras();
                if(extras == null){
                    System.out.println("extras is null");
                    //extras.putString("GROUP_NAME",group_names.getItem(position).toString());
                    //startActivity(intent);
                }

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
                                        Firebase ref = new Firebase("https://luminous-torch-5174.firebaseio.com/groupdetails/");
                                        GroupDetails gd = new GroupDetails(gname);
                                        Map<String, Object> grod = new HashMap<String, Object>();
                                        grod.put(gname, gd.getGroupName());
                                        ref.child("/").updateChildren(grod);
                                        //if()){
                                        m_listItems.add(gname);
                                        group_names.notifyDataSetChanged();
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
        switch (item.getItemId()) {
            case R.id.action_logout:
                Logout_App();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void Logout_App() {
        Intent logout=new Intent(this,LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logout);
        this.finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_delete:
                removeListViewItem();
                break;
        }
        return true;
    }

    public void removeListViewItem() {
        if (positionToRemove != -1) {
            m_listItems.remove(positionToRemove);
            group_names.notifyDataSetInvalidated();
            positionToRemove = -1;
        } else {
            Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void memberDetails(View v){
        if (result.length() > 0) {
            Intent i = new Intent(this, Members.class);
            startActivity(i);
        }
    }*/

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onRestart() {
        super.onRestart();
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
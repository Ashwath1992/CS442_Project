package com.cs442.project.splitpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
    private StoreDbHandler dbhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void register(View view)
    {
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
    }

    public void login(View view)
    {
        EditText username = (EditText)findViewById(R.id.editText);
        EditText password = (EditText)findViewById(R.id.editText2);
        dbhandler = StoreDbHandler.getDbHandlerInstance(getApplicationContext(),null);

        String uname = username.getText().toString();
        String pwd = password.getText().toString();
        if(uname !=null && pwd != null){
            if(dbhandler.isUserAuthenticated(uname,pwd)){
                Toast.makeText(this, "You have succesfully Logged in", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,Groups_Reg.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Please check your username and password", Toast.LENGTH_LONG).show();
                username.setText("");
                password.setText("");
            }
        }
        username.setText("");
        password.setText("");
    }
}

package com.cs442.project.splitpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;


public class LoginActivity extends Activity {
    Firebase refer;


    EditText usernameETV, passwordETV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        refer = new Firebase("https://luminous-torch-5174.firebaseio.com/users/");

        usernameETV = (EditText)findViewById(R.id.editText);
        passwordETV = (EditText)findViewById(R.id.editText2);
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
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void register(View view)
    {
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    public void login(View view)
    {
        final String username = new String(usernameETV.getText().toString());
        final String passwordVal = new String(passwordETV.getText().toString());
        refer.child("/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map<String, Object> root = (Map<String, Object>) dataSnapshot.getValue();
                try
                {
                    Map<String, Object> newPost = (Map<String, Object>) root.get(username);
                    {
                        String value = (String) newPost.get("password");
                        if (value.equals(passwordVal))
                        {
                            Toast.makeText(getApplicationContext(), "Logged-In", Toast.LENGTH_LONG).show();
                            Intent ii=new Intent(LoginActivity.this,Groups_Reg.class);
                            startActivity(ii);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Incorrect credentials", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "User not found..!!!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        usernameETV.setText("");
        passwordETV.setText("");
    }

    public void forgot_pass(View view)
    {
        final String username = new String(usernameETV.getText().toString());
        final String passwordVal = new String(passwordETV.getText().toString());
        refer.child("/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> root = (Map<String, Object>) dataSnapshot.getValue();
                try {
                    Map<String, Object> send_user = (Map<String, Object>) root.get(username);
                    {
                        if(send_user != null) {
                            String pass = (String) send_user.get("password");
                            String phone = (String) send_user.get("phoneno");
                            String passd = "Your SplitPay account password is: " + pass;
                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phone, null, passd, null, null);
                                Toast.makeText(getApplicationContext(), "Your Password has been sent to your registered phone no.",
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),
                                        "SMS faild, please try again later!",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Please enter username to receive your password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e)
                {

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
}

package com.cs442.project.splitpay;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;

public class Registration extends ActionBarActivity {

    private String name;
    private String email;
    private String phone;
    private String uname;
    private String passwd;
    private String conpass;
    private String card;
    private String ctype;
    private String cname;
    private String cnum;
    private Date exp;
    private String cvv;
    private StoreDbHandler dbHandler;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getConpass() {
        return conpass;
    }

    public void setConpass(String conpass) {
        this.conpass = conpass;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    TextView mEdit;
    EditText fullname, email_id, phone_no, username, password, repassword, card_name, card_num, card_cvv;
    Button register, cancel;
    RadioGroup cardGroup;
    RadioButton cardradio;
    Spinner cardType;

    private void isEmptyField (EditText editText){
        if (editText.getText().toString().length() <= 0)
        {
            editText.setError("Mandatory Field");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar ab =getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        register = (Button)findViewById(R.id.reg_btn);
        cancel = (Button)findViewById(R.id.can_btn);

        fullname = (EditText)findViewById(R.id.name);
        email_id = (EditText)findViewById(R.id.email);
        phone_no = (EditText)findViewById(R.id.phone);
        username = (EditText)findViewById(R.id.uname);
        password = (EditText)findViewById(R.id.passwd);
        repassword = (EditText)findViewById(R.id.conpass);

        card_name = (EditText)findViewById(R.id.cname);
        card_num = (EditText)findViewById(R.id.cnum);
        //card_date = (EditText)findViewById(R.id.date_in);
        card_cvv = (EditText)findViewById(R.id.cvv_in);
        cardGroup = (RadioGroup)findViewById(R.id.radioCard);
        //creditCard =(RadioButton)findViewById(R.id.credit);
        //debitCard =(RadioButton)findViewById(R.id.debit);
        cardType = (Spinner)findViewById(R.id.spinner);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration userRegistration = new Registration();
                String fname = fullname.getText().toString();
                String mail = email_id.getText().toString();
                //if(phone_no.getText().toString().matches("\\d{10}"))            //10 <--- Number of digits in Phone No.
                //int pno = Integer.parseInt(phone_no.getText().toString());
                String pno = phone_no.getText().toString();
                String user_name = username.getText().toString();
                String pass = password.getText().toString();
                String confirm = repassword.getText().toString();
                //Read from radio button
                int selectedId = cardGroup.getCheckedRadioButtonId();
                cardradio = (RadioButton) findViewById(selectedId);
                String Card = cardradio.getText().toString();
                //read from spinner
                String card_type = cardType.getSelectedItem().toString();

                String cardname = card_name.getText().toString();
                String cardnum = card_num.getText().toString();
                //String cdate = card_date.getText().toString();

                //int cardnum = Integer.parseInt(card_num.getText().toString());
                String c_cvv = card_cvv.getText().toString();

                isEmptyField(fullname);
                isEmptyField(email_id);
                isEmptyField(phone_no);
                isEmptyField(username);
                isEmptyField(password);
                isEmptyField(repassword);
                isEmptyField(card_name);
                isEmptyField(card_num);
                //isEmptyField(card_date);
                isEmptyField(card_cvv);

                if((c_cvv.length() > 0) && (c_cvv.length() < 3))
                {
                    card_cvv.setError("Incorrect CVV");
                    return;
                }

                if (!mail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
                {
                    email_id.setError("Incorrect Email-id");
                    return;
                }
                /*if (!(cdate.matches("^(1[0-2]|0[1-9])/20[0-9]{2}$")))
                {
                    Toast.makeText(getApplicationContext(), "HI", Toast.LENGTH_LONG).show();
                    card_date.setError("Invalid Date");
                    return;
                }*/
                if(cardnum.length() < 16)
                {
                    card_num.setError("Invalid Card Number");
                    return;
                }
                if (pno.length() > 10)
                {
                    phone_no.setError("Invalid Phone Number");
                    return;
                }
                if(TextUtils.isEmpty(pass) || pass.length() < 8)
                {
                    password.setError("You must have atleast 8 characters in your password");
                    password.setText("");
                    repassword.setText("");
                    return;
                }
                if(!pass.equals(confirm))
                {
                    repassword.setError("Password does not match");
                    password.setText("");
                    repassword.setText("");
                    return;
                }
                else if(pass.equals(user_name))
                {
                    password.setError("Password should not be same as username");
                    password.setText("");
                    repassword.setText("");
                    return;
                }
                else if(user_name.equals(fname))
                {
                    username.setError("Username should not be same as full name");
                    return;
                }
                else
                {
                    //Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                    userRegistration.setCard(cardname);
                    userRegistration.setCname(cardname);
                    userRegistration.setCnum(cardnum);
                    userRegistration.setConpass(confirm);
                    userRegistration.setName(fname);
                    userRegistration.setEmail(mail);
                    userRegistration.setPhone(pno);
                    userRegistration.setUname(user_name);
                    userRegistration.setPasswd(pass);
                    userRegistration.setCvv(c_cvv);
                    userRegistration.setCard(Card);
                    userRegistration.setCtype(card_type);

                    dbHandler = StoreDbHandler.getDbHandlerInstance(getApplicationContext(),null);
                    dbHandler.addUserDetails(userRegistration);

                    Intent intent = new Intent(Registration.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void selectDate(View view) {
        //android.app.DialogFragment newFragment = new SelectDateFragment();
        DialogFragment newFragment = new SelectDateFragment();
        //newFragment.show(getSupportFragmentManager(),"DatePicker");
        newFragment.show(getFragmentManager(),"DatePicker");
    }
    public void populateSetDate(int year, int month, int day) {
        mEdit = (TextView)findViewById(R.id.date_in);
        mEdit.setText(month+"/"+day+"/"+year);
    }
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_user, menu);
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
}

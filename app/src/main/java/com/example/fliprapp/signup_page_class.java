package com.example.fliprapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class signup_page_class extends AppCompatActivity {
    EditText name,email,password,phone;
    Button signup;
    public static final String url = "jdbc:mysql://192.168.0.101:3306/FLIPR"; //ip of laptop and port of xampp
    public static final String user = "hema";
    public static final String pass = "1234";

    public String new_name,new_email,new_password,new_phone;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);


        name = findViewById(R.id.name_signup_ed);
        email = findViewById(R.id.email_signup_ed);
        password = findViewById(R.id.password_signup_ed);
        phone = findViewById(R.id.phone_signup_ed);
        signup = findViewById(R.id.signup_btn_signup_page);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_name = name.getText().toString();  //variable to store the contents of ed and sent to bg db thread
                new_email= email.getText().toString();
                new_password = password.getText().toString();
                new_phone = phone.getText().toString();



                ConnectMySQL connectMySql = new ConnectMySQL();  //creating object for a class declared below
                connectMySql.execute("");
            }
        });

    }

    private class ConnectMySQL extends AsyncTask<String,Void,String> {
        String res = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");


                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();

                System.out.println(name+"name");
                int rs = st.executeUpdate("INSERT INTO `USER_TABLE` (`NAME`, `EMAIL`, `PASSWORD`, `PHONE`) " +
                        "VALUES ('"+new_name+ "', '"+new_email+"', '"+new_password+"', '"+new_phone+"');");




                res = result;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(signup_page_class.this, "successfully signed up", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signup_page_class.this, MainActivity.class);  //from and to ----------------------------!!!!!!!!
            startActivity(intent);  //to open login page
        }
    }
}

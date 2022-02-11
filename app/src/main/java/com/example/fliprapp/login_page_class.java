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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class login_page_class extends AppCompatActivity {
    public static final String url = "jdbc:mysql://192.168.0.101:3306/FLIPR"; //ip of laptop and port of xampp
    public static final String user = "hema";
    public static final String pass = "1234";
    EditText username,password;
    Button login;
    String email_login,password_login;

    public String new_username,new_email,new_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username = findViewById(R.id.email_login_ed);
        password = findViewById(R.id.password_login_ed);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_login = username.getText().toString();
                password_login=password.getText().toString();
                ConnectorLogin login = new ConnectorLogin();
                login.execute("");
            }
        });

    }










    private class ConnectorLogin extends AsyncTask<String,Void,String> {
        String res = "";
        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");

                String result = "Database Connection Successful\n";
                String output;
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM `USER_TABLE` WHERE `EMAIL` = '"+email_login+"' AND `PASSWORD` = '"+password_login+"';");



                ResultSetMetaData rsmd = rs.getMetaData();
                user user1 = new user();
                while (rs.next()) {                                         //-> to run with ddl
                    result += rs.getString(1).toString() + "\n"; // TO DETERMINE WHICH COLUMN INFO WE ARE GETTING!
                    user1.name = rs.getString(1).toString();
                    user1.email = rs.getString(2).toString();
                    user1.phone = rs.getString(4);

                }

                System.out.println("user"+user1.name);

                new_username = user1.getName();
                new_email = user1.getEmail();
                new_phone = user1.getPhone();
                res = new_username;



            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(res+"res");
            return res;

        }


        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);


            if (s!=null){
                Toast.makeText(login_page_class.this, "WELCOME BACK "+s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login_page_class.this, MainActivity.class);  //from and to ----------------------------!!!!!!!!
                startActivity(intent);  //to open login page

            }
            else {
                Toast.makeText(login_page_class.this,"INVALID DETAILS QQ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

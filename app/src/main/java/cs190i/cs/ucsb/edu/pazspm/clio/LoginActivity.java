package cs190i.cs.ucsb.edu.pazspm.clio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;

public class LoginActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String isLoggedIn = "LOGINKEY";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login_button = (Button)findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(isLoggedIn, true);
                editor.apply();

                EditText editText = (EditText) findViewById(R.id.id_editText);
                String text = editText.getText().toString();

                editText = (EditText) findViewById(R.id.password_editText);
                String password = editText.getText().toString();

                intent.putExtra("id", text);
                intent.putExtra("password", password);
                startActivity(intent);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("id", text).commit();
            }
        });

        Button signup_button = (Button)findViewById(R.id.signup_button);


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

    }
}

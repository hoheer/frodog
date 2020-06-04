package com.example.frodog;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;



public class Check extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        TextView UserName = findViewById(R.id.User_Name);
        TextView UserEmail = findViewById(R.id.User_Email);
        TextView Dogkind = findViewById(R.id.Dog_Kind);
        TextView Dogbir = findViewById(R.id.Dog_birthday);
        TextView Dogsex = findViewById(R.id.Dog_sex_check);
        Intent intent = getIntent();

        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("Email");
        String birthday = intent.getExtras().getString("birthday");
        String kind = intent.getExtras().getString("kind");
        String sex = intent.getExtras().getString("sex");


        UserName.setText(name);
        UserEmail.setText(email);
        Dogkind.setText(kind);
        Dogbir.setText(birthday);
        Dogsex.setText(sex);

    }

@Override
public void onBackPressed(){

}
}

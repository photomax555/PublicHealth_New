package findhosp.com.publichealth.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;

import findhosp.com.publichealth.Animal.AnimalSelect;
import findhosp.com.publichealth.People.PeopleFilter;
import findhosp.com.publichealth.People.PeopleSelect;
import findhosp.com.publichealth.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPeople = (Button) findViewById(R.id.button);
        btnPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PeopleSelect.class);
                startActivity(intent);

            }
        });

        Button btnAnimal = (Button) findViewById(R.id.button2);
        btnAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AnimalSelect.class);
                startActivity(intent);

            }
        });


    }

}

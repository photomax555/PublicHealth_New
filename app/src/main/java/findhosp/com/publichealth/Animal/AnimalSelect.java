package findhosp.com.publichealth.Animal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import findhosp.com.publichealth.Map.MapsAnimalAll;
import findhosp.com.publichealth.Map.MapsAnimalFilter;
import findhosp.com.publichealth.R;

public class AnimalSelect extends AppCompatActivity {
    String getUrl="http://find-hosp.com/web_service/get_ahos.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_select);

        Button button = (Button)findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalSelect.this, MapsAnimalAll.class);
                intent.putExtra("GET_URL", getUrl);
                startActivity(intent);
            }
        });

        Button btnAnimalFilter = (Button)findViewById(R.id.button4);
        btnAnimalFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalSelect.this, AnimalFilter.class);
                intent.putExtra("GET_URL", getUrl);
                startActivity(intent);
            }
        });
    }
}

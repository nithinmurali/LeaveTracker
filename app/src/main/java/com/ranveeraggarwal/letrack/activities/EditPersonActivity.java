package com.ranveeraggarwal.letrack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ranveeraggarwal.letrack.R;
import com.ranveeraggarwal.letrack.models.Person;
import com.ranveeraggarwal.letrack.storage.DatabaseAdapter;

import java.util.Locale;

import static com.ranveeraggarwal.letrack.utilities.RepetitiveUI.shortToastMaker;

public class EditPersonActivity extends AppCompatActivity {

    Person person;

    Toolbar toolbar;
    TextView nameField;
    TextView occupationField;
    TextView salaryField;
    Button submitButton;
    RadioGroup frequencyFieldGroup;
    RadioButton frequencyField;

    String selectedName;
    String selectedOccupation;
    String selectedSalary;
    String selectedFrequency;

    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("currentPerson");

        databaseAdapter = new DatabaseAdapter(this);

        toolbar = (Toolbar) findViewById(R.id.add_app_bar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit " + person.getName());

        nameField = (TextView) findViewById(R.id.name_field);
        nameField.setText(person.getName());

        occupationField = (TextView) findViewById(R.id.occupation_field);
        occupationField.setText(person.getOccupation());

        frequencyFieldGroup = (RadioGroup) findViewById(R.id.frequency_field);
        switch (person.getFrequency()) {
            case 1:
                frequencyFieldGroup.check(R.id.frequency_field_1);
                break;
            case 2:
                frequencyFieldGroup.check(R.id.frequency_field_2);
                break;
            case 3:
                frequencyFieldGroup.check(R.id.frequency_field_3);
                break;
            case 4:
                frequencyFieldGroup.check(R.id.frequency_field_4);
                break;
            default:
                frequencyFieldGroup.check(R.id.frequency_field_1);
                break;
        }

        salaryField = (TextView) findViewById(R.id.salary_field);
        salaryField.setText(String.format(Locale.ENGLISH, "%d", person.getSalary()));

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setText(R.string.confirm);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedName = nameField.getText().toString();

                selectedOccupation = occupationField.getText().toString();

                int checkedRadioButtonId = frequencyFieldGroup.getCheckedRadioButtonId();
                frequencyField = (RadioButton) frequencyFieldGroup.findViewById(checkedRadioButtonId);
                selectedFrequency = frequencyField.getText().toString();

                selectedSalary = salaryField.getText().toString();

                if (selectedName.equals("")) {
                    shortToastMaker(view.getContext(), "Name cannot be empty!");
                } else if (selectedOccupation.equals("")) {
                    shortToastMaker(view.getContext(), "Occupation cannot be empty!");
                } else if (selectedSalary.equals("")) {
                    shortToastMaker(view.getContext(), "Salary cannot be empty!");
                } else {
                    person.setName(selectedName);
                    person.setOccupation(selectedOccupation);
                    person.setSalary(Integer.parseInt(selectedSalary));
                    person.setFrequency(Integer.parseInt(selectedFrequency));

                    int count = databaseAdapter.updatePerson(person);
                    if (count < 0) shortToastMaker(view.getContext(), "Operation unsuccessful");
                    else shortToastMaker(view.getContext(), "Person edited successfully");
                    Intent intent = new Intent(EditPersonActivity.this, PersonDetails.class);
                    intent.putExtra("currentPerson", person);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setVisible(false);

        MenuItem addItem = menu.findItem(R.id.action_add_person);
        addItem.setVisible(true);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PersonDetails.class);
        intent.putExtra("currentPerson", person);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, PersonDetails.class);
            intent.putExtra("currentPerson", person);
            startActivity(intent);
            finish();
        } else if (menuItem.getItemId() == R.id.action_add_person) {
            selectedName = nameField.getText().toString();

            selectedOccupation = occupationField.getText().toString();

            int checkedRadioButtonId = frequencyFieldGroup.getCheckedRadioButtonId();
            frequencyField = (RadioButton) frequencyFieldGroup.findViewById(checkedRadioButtonId);
            selectedFrequency = frequencyField.getText().toString();

            selectedSalary = salaryField.getText().toString();

            if (selectedName.equals("")) {
                shortToastMaker(this, "Name cannot be empty!");
            } else if (selectedOccupation.equals("")) {
                shortToastMaker(this, "Occupation cannot be empty!");
            } else if (selectedSalary.equals("")) {
                shortToastMaker(this, "Salary cannot be empty!");
            } else {
                person.setName(selectedName);
                person.setOccupation(selectedOccupation);
                person.setSalary(Integer.parseInt(selectedSalary));
                person.setFrequency(Integer.parseInt(selectedFrequency));

                int count = databaseAdapter.updatePerson(person);
                if (count < 0) shortToastMaker(this, "Operation unsuccessful");
                else shortToastMaker(this, "Person edited successfully");
                Intent intent = new Intent(EditPersonActivity.this, PersonDetails.class);
                intent.putExtra("currentPerson", person);
                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(menuItem);
    }
}

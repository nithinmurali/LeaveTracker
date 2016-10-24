package com.ranveeraggarwal.letrack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.TextView;

import com.ranveeraggarwal.letrack.R;
import com.ranveeraggarwal.letrack.storage.DatabaseAdapter;

import static com.ranveeraggarwal.letrack.utilities.RepetitiveUI.shortToastMaker;

public class AddPersonActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView nameField;
    TextView occupationField;
    TextView salaryField;
    Button submitButton;
    RadioGroup frequencyFieldGroup;
    RadioButton frequencyField;
    AppCompatSpinner startDateField;

    String selectedName;
    String selectedOccupation;
    String selectedSalary;
    String selectedFrequency;
    String selectedStartDate;

    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        databaseAdapter = new DatabaseAdapter(this);

        toolbar = (Toolbar) findViewById(R.id.add_app_bar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Person");

        nameField = (TextView) findViewById(R.id.name_field);

        occupationField = (TextView) findViewById(R.id.occupation_field);

        frequencyFieldGroup = (RadioGroup) findViewById(R.id.frequency_field);

        salaryField = (TextView) findViewById(R.id.salary_field);

        startDateField = (AppCompatSpinner) findViewById(R.id.start_date_field);
        ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_array, android.R.layout.simple_spinner_item);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startDateField.setAdapter(dateAdapter);
        startDateField.setSelection(0);
        selectedStartDate = startDateField.getSelectedItem().toString();
        startDateField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStartDate = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submitButton = (Button) findViewById(R.id.submit_button);
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
                    long id = databaseAdapter.insertPerson(selectedName, selectedOccupation, Integer.parseInt(selectedFrequency), Integer.parseInt(selectedStartDate), Integer.parseInt(selectedSalary));
                    if (id < 0) shortToastMaker(view.getContext(), "Operation unsuccessful");
                    else shortToastMaker(view.getContext(), "Person added successfully");
                    Intent intent = new Intent(AddPersonActivity.this, MainActivity.class);
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
        Intent intent = new Intent(this, MainActivity.class);
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
            Intent intent = new Intent(this, MainActivity.class);
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
                long id = databaseAdapter.insertPerson(selectedName, selectedOccupation, Integer.parseInt(selectedFrequency), Integer.parseInt(selectedStartDate), Integer.parseInt(selectedSalary));
                if (id < 0) shortToastMaker(this, "Operation unsuccessful");
                else shortToastMaker(this, "Person added successfully");
                Intent intent = new Intent(AddPersonActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(menuItem);
    }
}

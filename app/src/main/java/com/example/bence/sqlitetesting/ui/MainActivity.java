package com.example.bence.sqlitetesting.ui;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bence.sqlitetesting.R;
import com.example.bence.sqlitetesting.domain.Person;
import com.example.bence.sqlitetesting.infrastructure.PersonRepository;
import com.example.bence.sqlitetesting.infrastructure.dao.MyPersonRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView personsListView;
    private ArrayAdapter personsAdapter;
    private PersonRepository personRepository;

    private Integer selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        personsListView = (ListView) findViewById(R.id.personsListView);
        personsListView.setOnItemClickListener(this);

        personsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<Person>());
        personsListView.setAdapter(personsAdapter);

        personRepository = new MyPersonRepositoryImpl(this);
        personRepository.open();
        updateList(personRepository.getAllPersons());
        updateSelectedPosition();
    }

    @Override
    protected void onStop() {
        personRepository.close();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            openAboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedPosition = position;
    }

    public void onCreateButtonClick(View view) {
        personRepository.createPerson(getNewPerson());
        updateList(personRepository.getAllPersons());
        selectLastPosition();
    }

    public void onUpdateButtonClick(View view) {
        if (selectedPosition != null) {
            Person person = (Person) personsAdapter.getItem(selectedPosition);
            personRepository.updatePerson(getModifiedPerson(person));
            updateList(personRepository.getAllPersons());
        }
    }

    public void onDeleteButtonClick(View view) {
        if (selectedPosition != null) {
            Person person = (Person) personsAdapter.getItem(selectedPosition);
            personRepository.deletePerson(person.getId());
            updateList(personRepository.getAllPersons());
            updateSelectedPosition();
        }
    }

    public void onDropButtonClick(View view) {
        personRepository.drop();
        personRepository.open();
        updateList(personRepository.getAllPersons());
        updateSelectedPosition();
    }

    private void updateList(List<Person> persons) {
        personsAdapter.clear();
        personsAdapter.addAll(persons);
        personsAdapter.notifyDataSetChanged();
    }

    private Person getNewPerson() {
        int id = getNextId();
        Person person = new Person();
        person.setId(id);
        person.setName("Name" + id);
        person.setWeight(65.0f + id);
        person.setTimestamp(new Date());
        return person;
    }

    public int getNextId() {
        int nextId = 0;
        List<Person> persons = personRepository.getAllPersons();
        if (persons.size() > 0) {
            nextId = persons.get(persons.size() - 1).getId() + 1;
        }
        return nextId;
    }
    private Person getModifiedPerson(Person selectedPerson) {
        Person person = new Person();
        person.setId(selectedPerson.getId());
        person.setName(selectedPerson.getName() + selectedPerson.getId());
        person.setWeight(selectedPerson.getWeight() + 1);
        person.setTimestamp(new Date());
        return person;
    }

    private void selectLastPosition() {
        selectedPosition = personsAdapter.getCount() - 1;
    }

    private void updateSelectedPosition() {
        int count = personsAdapter.getCount();
        if (count == 0) {
            selectedPosition = null;
        } else if (selectedPosition >= count) {
            selectedPosition = count - 1;
        }
    }

    public void openAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Close", null);

        String title = "About SQLiteTesting";
        builder.setTitle(title);

        String message = "This is a simple example for " +
                "integration testing the SQLite database access in Android " +
                "(enabler for TDD of the persistence layer).\n" +
                "Spike about Repository/DAO architecture over Contract/DbHelper approach.\n\n" +
                "Author: Bence Haraszti\n\n" +
                "Source code: https://github.com/bharaszti/SQLiteTesting";
        SpannableString spannableMessage = new SpannableString(message);
        Linkify.addLinks(spannableMessage, Linkify.ALL);
        builder.setMessage(spannableMessage);

        AlertDialog dialog = builder.show();
        ((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
    }

}

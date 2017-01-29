package com.example.petri.todoapp;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


import java.util.ArrayList;

import static com.google.android.gms.common.api.GoogleApiClient.*;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private Toast toast;
    // Button variable
    Button btn;
    EditText text;

    final ArrayList<String> arraylist = new ArrayList<String>();
    final ArrayList<Integer> arraylistid = new ArrayList<Integer>();
    final ArrayList<Integer> arraylistbool = new ArrayList<Integer>();

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new Builder(this).addApi(AppIndex.API).build();

        // Handle for future listview actions
        final ListView listview = (ListView) findViewById(R.id.listView);
        // Change text to show strikethrough if single click on item
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView v = (TextView)view;
                v.setPaintFlags(v.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });
        // Give listview permission to spawn a menu
        registerForContextMenu(listview);

        // Load previous messages
        final MyDb db = new MyDb(getApplicationContext());
        try (Cursor curse = db.selectRecords()) {
            // Fill array
            while (curse.moveToNext()) {
                arraylistid.add(curse.getInt(0));
                arraylist.add(curse.getString(1));
                arraylistbool.add(curse.getInt(2));
                //(Toast.makeText(getBaseContext(), str_tmp, Toast.LENGTH_SHORT)).show();
            }
        } finally {
            //(Toast.makeText(getBaseContext(), "No more entries", Toast.LENGTH_SHORT)).show();
            // Push array into listview
            ArrayAdapter arrayadapter = new ArrayAdapter(MainActivity.this, R.layout.textview_template, arraylist);
            listview.setAdapter(arrayadapter);
        }

        // Button listener that displays a toast and appends a task to the listview when the button is clicked
        btn = (Button)findViewById(R.id.btn);
        btn.setEnabled(false);

        text = (EditText)findViewById(R.id.editText);
        text.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*if(text.getText().length() <= 0){
                    btn.setEnabled(false);
                }
                else{
                    btn.setEnabled(true);
                }*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if(text.getText().length() <= 0){
                    btn.setEnabled(false);
                }
                else{
                    btn.setEnabled(true);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(text.getText().length() <= 0){
                    btn.setEnabled(false);
                }
                else{
                    btn.setEnabled(true);
                }
            }

        });
        btn.setOnClickListener(new View.OnClickListener(){
            //EditText text = (EditText)findViewById(R.id.editText);

            @Override
            public void onClick(View v){

                if (text.getText().length() > 0) {

                    // Add entry to database and update lists
                    db.createRecords(null, text.getText().toString(), 0); // zero as false
                    Cursor tmp_curse = db.selectRecords();
                    tmp_curse.moveToLast();
                    tmp_curse.moveToPrevious();
                    arraylistid.add(tmp_curse.getInt(0));
                    arraylist.add(tmp_curse.getString(1));
                    arraylistbool.add(tmp_curse.getInt(2));
                    tmp_curse.close();

                    // Update list
                    ArrayAdapter arrayadapter = new ArrayAdapter(MainActivity.this, R.layout.textview_template, arraylist);
                    listview.setAdapter(arrayadapter);

                    // If toast already exist only update instead of stacking, otherwise create a new toast
                    try {
                        toast.getView().isShown();
                        toast.setText(R.string.created_text);
                    } catch (Exception e) {
                        toast = Toast.makeText(getBaseContext(), R.string.created_text, Toast.LENGTH_SHORT); //getBaseContext()
                    }
                    // Shows the toast message
                    toast.show();

                    // Empties the text field when you submit the task
                    text.setText("");
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_entry, menu);
        MainActivity.super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null)
            return false;

        int index = info.position;
        int id = item.getItemId();
        TextView view = (TextView)info.targetView;

        switch (id) {
            case R.id.delete:
                //(Toast.makeText(getBaseContext(), String.valueOf(index) + " " + String.valueOf(id), Toast.LENGTH_SHORT)).show();
                MyDb db = new MyDb(getApplicationContext());
                db.removeRecords(arraylistid.get(index).toString());
                arraylistid.remove(index);
                arraylist.remove(index);
                arraylistbool.remove(index);
                // Update list
                ArrayAdapter arrayadapter = new ArrayAdapter(MainActivity.this, R.layout.textview_template, arraylist);
                ((ListView)findViewById(R.id.listView)).setAdapter(arrayadapter);
                break;

            case R.id.copy:
                // Copy to clipboard
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("", view.getText());
                clipboard.setPrimaryClip(clip);

                // Show toast
                (Toast.makeText(getBaseContext(), R.string.copied_text, Toast.LENGTH_SHORT)).show();
                break;

            default:
                break;
        }


        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }


    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    // Handwritten code TODO-stub


}

package com.example.petri.todoapp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


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

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new Builder(this).addApi(AppIndex.API).build();

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
                    LinearLayout linearlayout = (LinearLayout)findViewById(R.id.linearLayout);

                    CheckBox checkbox = (CheckBox)getLayoutInflater().inflate(R.layout.checkboxtemplate, null);
                    checkbox.setText(text.getText());
                    linearlayout.addView(checkbox);

                    // Tries to display a toast when the button is pressed
                    try {
                        toast.getView().isShown();
                        toast.setText("Task Created");
                    } catch (Exception e) {
                        toast = Toast.makeText(getBaseContext(), "Task Created", Toast.LENGTH_SHORT); //getBaseContext()
                    }
                    // Shows the toast message
                    toast.show();

                    // Empties the text field when you submit the task
                    text.setText("");
                }
            }
        });
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

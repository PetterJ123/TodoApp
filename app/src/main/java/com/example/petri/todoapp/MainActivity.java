package com.example.petri.todoapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        // Button variable
        Button btn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new Builder(this).addApi(AppIndex.API).build();

        // Button listener that displays a toast and appends a task to the listview when the button is clicked
        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener(){
            EditText text;

            @Override
            public void onClick(View v){
                text = (EditText)findViewById(R.id.editText);
                //CheckBox checkbox = (CheckBox)findViewById(R.id.checkBox);
                if (text.getText().length() > 0) {
                    LinearLayout linearlayout = (LinearLayout)findViewById(R.id.linearLayout);

                    CheckBox checkbox = new CheckBox(getApplicationContext());
                    checkbox.setText(text.getText());
                    //checkbox.
                    //checkbox.onKeyLongPress()
                    linearlayout.addView(checkbox);

                    try {
                        toast.getView().isShown();
                        toast.setText("Task Created");
                    } catch (Exception e) {
                        toast = Toast.makeText(getBaseContext(), "Task Created", Toast.LENGTH_SHORT);
                    }
                    toast.show();

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

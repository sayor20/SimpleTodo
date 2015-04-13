package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    int pos;
    String itemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        //get the bundled values from MainActivity.java
        Intent i =getIntent();
        itemValue=i.getExtras().getString("itemvalue");
        pos=i.getExtras().getInt("position");

        EditText etSaveItem=(EditText)findViewById(R.id.etSaveItem);
        etSaveItem.setText(itemValue);
        etSaveItem.setSelection(etSaveItem.getText().length());
    }

    public void onSaveItem(View v){
        EditText etSaveItem=(EditText) findViewById(R.id.etSaveItem);
        String itemText=etSaveItem.getText().toString();

        Intent data=new Intent();
        //Adding the updated value
        data.putExtra("newitemvalue",itemText);
        data.putExtra("pos",pos);
        // set result code and bundle data for response
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

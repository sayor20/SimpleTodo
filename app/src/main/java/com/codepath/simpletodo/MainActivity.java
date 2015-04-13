package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    ListView lvlItems;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvlItems=(ListView) findViewById(R.id.lvlItems);
        readItems();
        itemsAdapter=new ArrayAdapter<String>(this,
                                          android.R.layout.simple_list_item_1,
                                          items);
        lvlItems.setAdapter(itemsAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {
        lvlItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvlItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MainActivity.this,EditItemActivity.class);
                i.putExtra("itemvalue", items.get(position));
                i.putExtra("position",position);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Getting the extras from intent
            String newItemValue = data.getExtras().getString("newitemvalue");
            int pos = data.getExtras().getInt("pos");
            items.remove(pos);
            items.add(pos,newItemValue);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void readItems(){
        File fileDir=getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
         try{
            items=new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e){
            items=new ArrayList<String>();
        }

    }

    public void writeItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    public void onAddItem(View v){
        EditText etNewItem=(EditText) findViewById(R.id.etNewItem);
        String itemText=etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

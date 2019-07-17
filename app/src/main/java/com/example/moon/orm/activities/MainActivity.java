package com.example.moon.orm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.example.moon.orm.R;
import com.example.moon.orm.models.Item;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText etName,etDes,etDelete;
    Button btnSave,btnDelete,btnShowData;

    EditText etId,etNameUpdate,etDesUpdate;
    Button btnUpdate;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText)findViewById(R.id.et_name);
        etDes = (EditText)findViewById(R.id.et_description);
        etDelete = (EditText)findViewById(R.id.et_delete);
        etId = (EditText)findViewById(R.id.et_id);
        etNameUpdate = (EditText)findViewById(R.id.et_name_update);
        etDesUpdate = (EditText)findViewById(R.id.et_description_update);



        btnSave = (Button)findViewById(R.id.btn_saveData);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setName(etName.getText().toString());
                item.setDescription(etDes.getText().toString());
                Long save = item.save();
                Log.i(TAG, "onClick: "+save);
                if(save>0){
                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Failed To save",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete = (Button)findViewById(R.id.btn_DeleteData);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Item item = Item.load(Item.class,1);
//                item.delete();
                int count = 0;
                count = new Select().from(Item.class).where("Id = ? ", Integer.parseInt(etDelete.getText().toString().trim())).count();
                new Delete().from(Item.class).where("Id = ? ", Integer.parseInt(etDelete.getText().toString().trim())).execute();

                if(count!=0) {
                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Not deleted",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnShowData = (Button)findViewById(R.id.btn_showData);
        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "getData: \n");
                //Item item = new Select().from(Item.class).where("Id = ?", 1).executeSingle();
                List<Item> items = new Select().from(Item.class).orderBy("name ASC").execute();

                for (Item item:items) {
                    Log.i(TAG, "\n" + "Id : " + item.getId() +"\n" + "Name : " + item.getName() + "\nDescription : "
                            + item.getDescription() + "\n");
                }
            }
        });


        btnUpdate = (Button)findViewById(R.id.btn_updateData);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(etNameUpdate.getText().toString(), etDesUpdate.getText().toString());
                Item item1=new Select().from(Item.class).where("Id = ? ",Integer.parseInt(etId.getText().toString())).executeSingle();
                item1.name = item.getName();
                item1.description = item.getDescription();
                Long save = item1.save();
                Log.i(TAG, "item update : "+save);

//                new Update(Item.class).set("")
//                        .where("Id = ?",Integer.parseInt(etId.getText().toString().trim()))
//                        .execute();
            }
        });

    }
}

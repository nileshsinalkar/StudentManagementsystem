package com.example.nilesh.studentmanagementsystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText name, rollno, marks;
    Button show,view,viewall,add, modify,delete;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText)findViewById(R.id.name);
        rollno=(EditText)findViewById(R.id.rollno);
        marks=(EditText)findViewById(R.id.marks);
        show=(Button)findViewById(R.id.show);
        view=(Button)findViewById(R.id.view);
        viewall=(Button)findViewById(R.id.viewall);
        add=(Button)findViewById(R.id.add);
        delete=(Button)findViewById(R.id.delete);
        modify=(Button)findViewById(R.id.modify);

        db=openOrCreateDatabase("Student_Management", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER,name VARCHAR,marks INTEGER):");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rollno.getText().toString().trim().length()==0||
                        name.getText().toString().trim().length()==0||
                        marks.getText().toString().trim().length()==0){

                    showmessage("Error","Please enter all the values");
                    return;
                }
                db.execSQL("INSERT INTO student VALUES('"+rollno.getText()+"','"+name.getText()+"','"+marks.getText()+"');");
                showmessage("Success","Values Entered");
                clearText();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rollno.getText().toString().trim().length()==0){
                    showmessage("Error","Please enter the roll no ");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'",null);
                if(c.moveToFirst()){
                    db.execSQL("DELETE FROm student WHERE rollno='"+rollno.getText()+"'");
                    showmessage("Success","REcord Deleted");

                }
                else
                {
                    showmessage("Error","Invalid roll no ");
                }
                clearText();


            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rollno.getText().toString().trim().length()==0){
                    showmessage("Error","Please enter the roll no ");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'",null);
                if(c.moveToFirst()){
                    db.execSQL("UPDATE student SET name='"+name.getText()+"',marks='"+marks.getText()+"' WHERE rollno='"+rollno.getText()+"'");
                    showmessage("Success","Record updated");

                }
                else
                {
                    showmessage("Error","Invalid roll no ");
                }
                clearText();
            }
        });



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(rollno.getText().toString().trim().length()==0){
                    showmessage("Error","Please enter the roll no ");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'",null);
                if(c.moveToFirst()){
                   name.setText(c.getString(1));
                    marks.setText(c.getString(2));

                }
                else
                {
                    showmessage("Error","Invalid roll no ");
                }
                clearText();
            }
        });


        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c=db.rawQuery("SELECT * FROM student ",null);
                if(c.getCount()==0){
                    showmessage("Error","NO record found ");

                }
                StringBuffer buffer =new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("ROll NO :"+c.getString(0)+"\n");
                    buffer.append("NAME :"+c.getString(1)+"\n");

                    buffer.append("MARKS :"+c.getString(2)+"\n");

                }
                showmessage("Student Details",buffer.toString());



            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showmessage("Student Management Application","Created By Nilesh ");

            }
        });

    }

    public void showmessage(String Title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();

    }

    public void clearText(){
        rollno.setText("");
        name.setText("");
        marks.setText("");
        rollno.requestFocus();
    }
}














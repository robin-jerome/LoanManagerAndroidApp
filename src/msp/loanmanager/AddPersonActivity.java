package msp.loanmanager;

import java.io.File;

import msp.action.DataHandler;
import msp.object.Person;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPersonActivity extends Activity {

	private DataHandler handler = new DataHandler();
	private String fileName = MainActivity.PATH + "p";
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
          
        
		Button add = (Button)findViewById(R.id.add_person_confirm);
		
		add.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 Person person = new Person();
		    	 int id;
		    	 if (MainActivity.persons.size() == 0){
		    		 id = 1000;
		    	 }else{
		    		 id = MainActivity.persons.get(MainActivity.persons.size()-1).getId();		    		
		    	 }
		    	 person.setId(1000);
		    	 EditText name = (EditText)findViewById(R.id.add_person_name);
		    	 person.setName(name.getText().toString());
		    	 EditText info = (EditText)findViewById(R.id.add_person_info);
		    	 person.setInfo(info.getText().toString());		    
		    	 MainActivity.persons.add(person);
		    	 System.out.println("Vysledna cesta je: " + fileName + Integer.toString(id));
		    	 handler.writePerson(fileName + Integer.toString(id), person);
		     Intent intent = new Intent(AddPersonActivity.this, MainActivity.class);	
		     startActivity(intent);
		     }
		 });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_person, menu);
        return true;
    }
    
}

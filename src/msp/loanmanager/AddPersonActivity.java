package msp.loanmanager;

import java.io.File;

import msp.action.DataHandler;
import msp.action.Functions;
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
	private int id;

	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        
        Bundle extras = getIntent().getExtras();        

        if (extras != null) {
            id = extras.getInt("person_id");
         
            Person person = Functions.findPersonById(id);
            EditText name = (EditText)findViewById(R.id.add_person_name);
            name.setText(person.getName());
            EditText info = (EditText)findViewById(R.id.add_person_info);
            info.setText(person.getInfo());	
        }
        
		Button add = (Button)findViewById(R.id.add_person_confirm);
		
		add.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 Person person = new Person();
		    	
		    	 if (id == 0){	    	 
		    		 id = MainActivity.persons.get(MainActivity.persons.size()-1).getId() + 1;		    		
		    	 }
		    	 person.setId(id);
		    	 EditText name = (EditText)findViewById(R.id.add_person_name);
		    	 person.setName(name.getText().toString());
		    	 EditText info = (EditText)findViewById(R.id.add_person_info);
		    	 person.setInfo(info.getText().toString());	
		    	 
		    	 Functions.deletePerson(id);
		    	 MainActivity.persons.add(person);
		    	 System.out.println("Path is: " + fileName + Integer.toString(id));
		    	 		    	 
		    	 handler.writePerson(fileName + Integer.toString(id), person);
		    	 
			     Intent intent = new Intent(AddPersonActivity.this, PersonListActivity.class);	
			     startActivity(intent);
		     }
		 });
        
    }
    
	@Override
	protected void onPause() {					//	Function called before switching over the activity
		// TODO Auto-generated method stub
		super.onPause();
		finish();							// We are trying to kill the old activity here ...
	}
    
}

package msp.loanmanager;

import java.util.ArrayList;

import msp.action.DataHandler;
import msp.action.Functions;
import msp.object.Group;
import msp.object.Person;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PersonDescriptionActivity extends Activity {

	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gp_description);
		
		Bundle extras = getIntent().getExtras();        

        if (extras != null) {
            id = extras.getInt("person_id");
            Person person = Functions.findPersonById(id);
            TextView name = (TextView)findViewById(R.id.gp_name);
            name.setText(person.getName());
            TextView title = (TextView)findViewById(R.id.gp_title_table);
            title.setText(R.string.gp_title_group_person);
            TextView info = (TextView)findViewById(R.id.gp_additional_information);
            if(person.getInfo() != ""){
            	info.setText(person.getInfo());
            }
            ArrayList<Group> groups = Functions.findAllPersonsGroupsById(id);
            
            Button delete = (Button)findViewById(R.id.gp_button_delete);
            Button edit = (Button)findViewById(R.id.gp_button_edit);
            
            if(groups.size() != 0 || id == MainActivity.me_ID){
            	delete.setEnabled(false);            	
            }
            
            delete.setOnClickListener(new View.OnClickListener() {
	   		     @Override
	   		     public void onClick(View v) {	
	   		    	 DataHandler dh = new DataHandler();
	   		    	 String filename = MainActivity.PATH + "p" + Integer.toString(id);
	   		    	 dh.removeFile(filename);
	   		    	 Functions.deletePerson(id);
		   		     Intent intent = new Intent(PersonDescriptionActivity.this, PersonListActivity.class);
		   		     startActivity(intent);
	   		     }
   		 	});
            
            edit.setOnClickListener(new View.OnClickListener() {
	   		     @Override
	   		     public void onClick(View v) {	
	   		    	 Intent intent = new Intent(PersonDescriptionActivity.this, AddPersonActivity.class);
		   		     intent.putExtra("person_id", id);		   		  
		   		     startActivity(intent);
	   		     }
  		 	});
            
            
            
            
            TableLayout tl = (TableLayout) findViewById(R.id.member_of_group);	
    		            
            for(int i=0; i<groups.size(); i++){
            	TableRow tr = new TableRow(this);
            	
            	TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
          		tr.setLayoutParams(tableRowParams);	           		            		            
 	   	            	
             	TextView groupname = new TextView(this);            	
             	LayoutParams lineparams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
             	groupname.setLayoutParams(lineparams);
             	
             	groupname.setText(groups.get(i).getGroupName());             			            	
             	groupname.setTextSize(18);		            	            
             	groupname.setTextColor(Color.WHITE);
             	groupname.setGravity(Gravity.LEFT);
             	groupname.setPadding(5, 5, 5, 5); 
 	            tr.addView(groupname);
 	
 	            TextView number = new TextView(this);	     
 	            LayoutParams numberparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
 	            number.setLayoutParams(numberparams);
 	            number.setText(Integer.toString(groups.get(i).getPersonsCount()));
 	            number.setTextColor(Color.WHITE);
 	            number.setGravity(Gravity.RIGHT);
 	            number.setPadding(5, 5, 5, 5); 
 	            tr.addView(number);		
 	            
 	            tl.addView(tr);	        
                
            }
            
        }		
		
	}
	
	@Override
	protected void onPause() {					//	Function called before switching over the activity
		// TODO Auto-generated method stub
		super.onPause();
		finish();							// We are trying to kill the old activity here ...
	}

}

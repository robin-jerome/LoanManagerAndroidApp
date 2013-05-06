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
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GroupDescriptionActivity extends Activity {

	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gp_description);
		
		Bundle extras = getIntent().getExtras();        

        if (extras != null) {
            id = extras.getInt("group_id");
            Group group = Functions.findGroupById(id); 
            TextView name = (TextView)findViewById(R.id.gp_name);
            name.setText(group.getGroupName());
            TextView title = (TextView)findViewById(R.id.gp_title_table);
            title.setText(R.string.gp_title_group_group);
            TextView info = (TextView)findViewById(R.id.gp_additional_information);
            if(group.getInfo() != ""){
            	info.setText(group.getInfo());
            }            
            
            Button delete = (Button)findViewById(R.id.gp_button_delete);
            Button edit = (Button)findViewById(R.id.gp_button_edit);
            
            delete.setOnClickListener(new View.OnClickListener() {
	   		     @Override
	   		     public void onClick(View v) {	
	   		    	 DataHandler dh = new DataHandler();
	   		    	 String filename = MainActivity.PATH + "g" + Integer.toString(id);
	   		    	 dh.removeFile(filename);
	   		    	 Functions.deleteGroup(id);
		   		     Intent intent = new Intent(GroupDescriptionActivity.this, GroupListActivity.class);
		   		     startActivity(intent);
	   		     }
   		 	});
            
            edit.setOnClickListener(new View.OnClickListener() {
	   		     @Override
	   		     public void onClick(View v) {	
	   		    	 Intent intent = new Intent(GroupDescriptionActivity.this, AddGroupActivity.class);
		   		     intent.putExtra("group_id", id);		   		  
		   		     startActivity(intent);
	   		     }
  		 	});
            
            
            TableLayout tl = (TableLayout) findViewById(R.id.member_of_group);	
    		            
            for(int i=0; i<group.getPersons().length; i++){
            	if(group.getPersons()[i] != 0){
            		Person person = Functions.findPersonById(group.getPersons()[i]);
            		
	            	TableRow tr = new TableRow(this);
	            	
	            	TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	          		tr.setLayoutParams(tableRowParams);	           		            		            
	 	   	            	
	             	TextView groupname = new TextView(this);            	
	             	LayoutParams lineparams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	             	groupname.setLayoutParams(lineparams);
	             	
	             	groupname.setText(person.getName());             			            	
	             	groupname.setTextSize(18);		            	            
	             	groupname.setTextColor(Color.BLACK);
	             	groupname.setGravity(Gravity.LEFT);
	             	groupname.setPadding(5, 5, 5, 5); 
	 	            tr.addView(groupname);
	 		 	           	 	            
	 	            tl.addView(tr);	        
            	}
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

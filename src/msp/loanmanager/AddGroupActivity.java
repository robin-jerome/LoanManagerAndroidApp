package msp.loanmanager;

import msp.action.DataHandler;
import msp.action.Functions;
import msp.object.Group;
import msp.object.Person;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AddGroupActivity extends Activity {

	private DataHandler handler = new DataHandler();
	private String fileName = MainActivity.PATH + "g";
	private Group egroup;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_group);
		
		Bundle extras = getIntent().getExtras();        

        if (extras != null) {
            id = extras.getInt("group_id");           
            egroup = Functions.findGroupById(id);
            EditText name = (EditText)findViewById(R.id.add_group_name);
            name.setText(egroup.getGroupName());
            EditText info = (EditText)findViewById(R.id.add_group_info);
            info.setText(egroup.getInfo());	
        }
		
		TableLayout tl = (TableLayout) findViewById(R.id.add_group_choose_persons_table);
			
		for(int i=0; i<MainActivity.persons.size(); i++){
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	 		tr.setLayoutParams(tableRowParams); 
	 		
	 		CheckBox check = new CheckBox(this);
	 		check.setId(MainActivity.persons.get(i).getId());
	 		if(egroup != null && egroup.isInGroup(MainActivity.persons.get(i).getId())){
	 			check.setChecked(true);
	 		}
	 		tr.addView(check);
	 		
	 		TextView name = new TextView(this);	
	 		name.setText(MainActivity.persons.get(i).getName());
	 		name.setTextColor(Color.BLACK);
	        tr.addView(name);
	        
	        tl.addView(tr);
		}	
		
		Button add = (Button)findViewById(R.id.add_group_confirm);
		
		add.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 Group group = new Group();
		    	 
		    	 if(id == 0){
		    		 if (MainActivity.groups.size() == 0){
			    		 id = 1000;
			    	 }else{
			    		 id = MainActivity.groups.get(MainActivity.groups.size()-1).getId() + 1;		    		
			    	 } 
		    	 }
		    	 
		    	 group.setId(id);
		    	 EditText name = (EditText)findViewById(R.id.add_group_name);
		    	 group.setGroupName(name.getText().toString());
		    	 EditText info = (EditText)findViewById(R.id.add_group_info);
		    	 group.setInfo(info.getText().toString());	
//		    	 group.addPerson(id);
		    	 TableLayout tl = (TableLayout)findViewById(R.id.add_group_choose_persons_table);
		    	 for(int i=0; i<tl.getChildCount(); i++){
		    		 CheckBox check = (CheckBox) ((TableRow)tl.getChildAt(i)).getChildAt(0);
		    		 if (check.isChecked()){
		    			 group.addPerson(check.getId());
		    		 }
		    	 }		    	 
		    	 
		    	 System.out.println("Path is: " + fileName + Integer.toString(id));
		    	 
		    	 Functions.deleteGroup(id);		    	
		    	 MainActivity.groups.add(group);		    	
		    	 handler.writeGroup(fileName + Integer.toString(id), group);
		    	 
		    	 System.out.println("Everything is ok");
		    	 
			     Intent intent = new Intent(AddGroupActivity.this, GroupListActivity.class);	
			     startActivity(intent);
		     }
		 });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_group, menu);
		return true;
	}

}

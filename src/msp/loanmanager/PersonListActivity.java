package msp.loanmanager;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PersonListActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gp_list);
		
		Button button = (Button)findViewById(R.id.bar_add_button);
		button.setText(R.string.button_add_gp);
		button.setTextSize(20);
		
		TextView title = (TextView)findViewById(R.id.bar_add_title);
		title.setText(R.string.persons);
		
		button.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(PersonListActivity.this, AddPersonActivity.class);	
		     startActivity(intent);
		     }
		 });		
		
		
		TableLayout tl = (TableLayout) findViewById(R.id.tableList);	
		
		if(MainActivity.persons.size()==0){
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
     		tr.setLayoutParams(tableRowParams);            
            tr.setBackgroundColor(Color.LTGRAY);
            
            TextView info = new TextView(this);		           
        	info.setText("You don't have any persons.");
        	LayoutParams infoparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            info.setLayoutParams(infoparams);
            info.setPadding(5, 5, 5, 5);
        	info.setTextColor(Color.BLACK);
            tr.addView(info);	
            
            tl.addView(tr);
		}else{
			for(int i=0; i<MainActivity.persons.size(); i++){
				
				 TableRow tr = new TableRow(this);
				 tr.setId(MainActivity.persons.get(i).getId());
				 
		            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	         		
		            if(i+1 == MainActivity.persons.size()){
		            	tableRowParams.setMargins(0, 0, 0, 0);  
		            }else{
		            	tableRowParams.setMargins(0, 0, 0, 5); 
		            } 
		            
	         		tr.setLayoutParams(tableRowParams);            
		            tr.setBackgroundColor(Color.LTGRAY);		            		            
		   	            	
	            	TextView name = new TextView(this);	    
	            	LayoutParams lineparams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	            	name.setLayoutParams(lineparams);
	            	name.setText(MainActivity.persons.get(i).getName());
	            	name.setTypeface(Typeface.DEFAULT_BOLD);		            	
	            	name.setTextSize(20);		            	            
		            name.setTextColor(Color.BLACK);
		            name.setGravity(Gravity.LEFT);
		            name.setPadding(5, 0, 5, 0); 
		            tr.addView(name);	
		          
		            tr.setOnClickListener(new View.OnClickListener() {
			   		     @Override
			   		     public void onClick(View v) {		    	 
				   		     Intent intent = new Intent(PersonListActivity.this, AddPersonActivity.class);
				   		     intent.putExtra("person_id", ((TableRow)v).getId());
				   		     startActivity(intent);
			   		     }
		   		 	});
		            
		            
		            tl.addView(tr);	 
			}			
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_list, menu);
		return true;
	}

}

package msp.loanmanager;

import msp.action.Functions;
import msp.object.Person;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GroupListActivity extends Activity {

	private boolean fromLoans = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gp_list);
			
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
           fromLoans = extras.getBoolean("loans");
        }
		
		if(fromLoans){
			Button button = (Button)findViewById(R.id.bar_add_button);
			ViewGroup layout = (ViewGroup) button.getParent();
			layout.removeView(button);
		}else{		
			Button button = (Button)findViewById(R.id.bar_add_button);
			button.setText(R.string.button_add_gp);
			button.setTextSize(20);
			
			button.setOnClickListener(new View.OnClickListener() {
			     @Override
			     public void onClick(View v) {		    	 
			     Intent intent = new Intent(GroupListActivity.this, AddGroupActivity.class);	
			     startActivity(intent);
			     }
			 });		
		
		}
		TextView title = (TextView)findViewById(R.id.bar_add_title);
		title.setText(R.string.groups);
				
		
		TableLayout tl = (TableLayout) findViewById(R.id.tableList);	
		
		if(MainActivity.groups.size()==0){
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
     		tr.setLayoutParams(tableRowParams);            
            tr.setBackgroundColor(Color.LTGRAY);
            
            TextView info = new TextView(this);		           
        	info.setText("You don't have any groups.");
        	LayoutParams infoparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            info.setLayoutParams(infoparams);
            info.setPadding(5, 5, 5, 5);
        	info.setTextColor(Color.BLACK);
            tr.addView(info);	
            
            tl.addView(tr);
		}else{
			for(int i=0; i<MainActivity.groups.size(); i++){
				TableRow tr = new TableRow(this);
				tr.setId(MainActivity.groups.get(i).getId());
				 
	            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
         		
	            if(i+1 == MainActivity.groups.size()){
	            	 tableRowParams.setMargins(0, 0, 0, 0);  
	            }else{
	            	tableRowParams.setMargins(0, 0, 0, 5); 
	            }       		
         		tr.setLayoutParams(tableRowParams);            
	            tr.setBackgroundColor(Color.LTGRAY);		            		            
	   	            	
            	TextView name = new TextView(this);            	
            	LayoutParams nameparams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            	name.setLayoutParams(nameparams);
            	
            	name.setText(MainActivity.groups.get(i).getGroupName());
            	name.setTypeface(Typeface.DEFAULT_BOLD);		            	
            	name.setTextSize(20);		            	            
	            name.setTextColor(Color.BLACK);
	            name.setGravity(Gravity.LEFT);
	            name.setPadding(5, 0, 5, 0); 
	            tr.addView(name);
	
	            TextView number = new TextView(this);	     
	            LayoutParams numberparams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	            number.setLayoutParams(numberparams);
	            number.setText(Integer.toString(MainActivity.groups.get(i).getPersonsCount()));
	            number.setTextColor(Color.BLACK);
	            number.setGravity(Gravity.RIGHT);
	            number.setTextSize(20);	
	            number.setPadding(5, 0, 5, 0); 
	            tr.addView(number);		
	            
	            tr.setOnClickListener(new View.OnClickListener() {
		   		     @Override
		   		     public void onClick(View v) {		    	 
		   		    	 Intent intent;
		   		    	 if(fromLoans){
		   		    		 intent = new Intent(GroupListActivity.this, GroupLoansActivity.class);
		   		    	 }else{
		   		    		 intent = new Intent(GroupListActivity.this, GroupDescriptionActivity.class);
		   		    	 }
			   		     intent.putExtra("group_id", ((TableRow)v).getId());
			   		     startActivity(intent);
		   		     }
	   		 	});
	            
	            tl.addView(tr);	 
			}			
		}
	}
}

package msp.loanmanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;


public class MainMenuActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		Button addPer = (Button)findViewById(R.id.bGroups);
		
		addPer.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(MainMenuActivity.this, GroupListActivity.class);
		     startActivity(intent);
		     }
		 });
		
		Button addGroup = (Button)findViewById(R.id.bPersons);
		
		addGroup.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(MainMenuActivity.this, PersonListActivity.class);	
		     startActivity(intent);
		     }
		 });
		
		Button addGiveLoan = (Button)findViewById(R.id.bGive);
		
		addGiveLoan.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(MainMenuActivity.this, GiveActivity.class);	
		     startActivity(intent);
		     }
		 });
		
		Button given = (Button)findViewById(R.id.bGive2);
		
		given.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(MainMenuActivity.this, PersonListActivity.class);	
		     intent.putExtra("loans", true);
		     startActivity(intent);
		     }
		 });
		
		Button taken = (Button)findViewById(R.id.bTake2);
		
		taken.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(MainMenuActivity.this, GroupListActivity.class);
		     intent.putExtra("loans", true);
		     startActivity(intent);
		     }
		 });		
				
	}
	

 	Class newClass;
 
	public void onClick(View v) {
		switch(v.getId())	{
		case R.id.bGroups:													// More cases should be added based on what GUI wanna display next.
			try {
				newClass = Class.forName("msp.object.Group");				// or the class where you have new layout GUI.
				Intent ourIntent = new Intent(MainMenuActivity.this, newClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
				/* Bundle bund = new Bundle();
				bund.putString("keyReturn", textDisp);
				setResult(RESULT_OK, ourInt);					 
				finish(); 	 		*/			// If we want this activity to end when task is done.
			break;
		}
	}
		
}
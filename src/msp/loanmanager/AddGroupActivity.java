package msp.loanmanager;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AddGroupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_group);
		
		TableLayout tl = (TableLayout) findViewById(R.id.add_group_choose_persons_table);
			
		for(int i=0; i<MainActivity.persons.size(); i++){
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	 		tr.setLayoutParams(tableRowParams); 
	 		
	 		CheckBox check = new CheckBox(this);
	 		check.setId(MainActivity.persons.get(i).getId());
	 		tr.addView(check);
	 		
	 		TextView name = new TextView(this);	
	 		name.setText(MainActivity.persons.get(i).getName());
	 		name.setTextColor(Color.BLACK);
	        tr.addView(name);
	        
	        tl.addView(tr);
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_group, menu);
		return true;
	}

}

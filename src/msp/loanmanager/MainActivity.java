package msp.loanmanager;

import java.io.File;
import java.util.ArrayList;

import msp.action.DataHandler;
import msp.object.Group;
import msp.object.Loan;
import msp.object.Person;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static File externalStorage = Environment.getExternalStorageDirectory();
	public static final String PATH = externalStorage.getAbsolutePath()
			+ File.separator + "Android" + File.separator + "data"
			+ File.separator + "lma" + File.separator;	
	private DataHandler dataHandler = new DataHandler();
	public static ArrayList<Person> persons = null;
	public static ArrayList<Group> groups = null;
	public static ArrayList<Loan> loans = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		File file = new File(PATH);
		if (!file.exists()) {
			file.mkdirs();
			System.out.println("I have created directory.");
		}
		
		if(persons == null){
			persons = dataHandler.readPersons(PATH);
			groups = dataHandler.readGroups(PATH);
			loans = dataHandler.readLoans(PATH);
		}
		Button addPer = (Button)findViewById(R.id.button1);
		
		addPer.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);	
//		     intent.putExtra("type", "all");
		     startActivity(intent);
		     }
		 });
		
		Button addGroup = (Button)findViewById(R.id.button2);
		
		addGroup.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);	
//		     intent.putExtra("type", "all");
		     startActivity(intent);
		     }
		 });
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

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
	public static int me_ID = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
		
		File file = new File(PATH);
		if (!file.exists()) {
			file.mkdirs();
			System.out.println("I have created directory.");
			persons = new ArrayList<Person>();
			groups = new ArrayList<Group>();
			loans = new ArrayList<Loan>();
						
			Person me = new Person();
			me.setId(me_ID);
			me.setName("Me");
			me.setInfo("My personality :)");
			persons.add(me);
			DataHandler dh = new DataHandler();
			dh.writePerson(PATH + "p" + Integer.toString(me_ID), me);
			System.out.println("Je tu: " + persons.get(0).getName());
			
		}else{
			System.out.println("Directory already exists");
			if(persons == null){
				System.out.println("Reading data from files");
				persons = dataHandler.readPersons(PATH);
				groups = dataHandler.readGroups(PATH);
				loans = dataHandler.readLoans(PATH);				
			}	
		}			
		
		Thread timer = new Thread() {			// Now this is looking for "run()" function, so we gotta define that.
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 

				Class ourClass;
				try {
					ourClass = Class.forName("msp.loanmanager.MainMenuActivity");
					Intent ourIntent = new Intent(MainActivity.this, ourClass);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		timer.start();
	}

	
	@Override
	protected void onPause() {					//	Function called before switching over the activity
		// TODO Auto-generated method stub
		super.onPause();
		finish();							// We are trying to kill the old activity here ...
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

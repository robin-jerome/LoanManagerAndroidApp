package msp.loanmanager;

import java.util.ArrayList;

import msp.action.DataHandler;
import msp.object.Person;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Given extends ListActivity {  //ListPersons
	private DataHandler handler = new DataHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_given);
		
		
		
//		int id = MainActivity.loans.get(0).getPerson_id();
		
		ArrayList<Person> personstolist = new ArrayList<Person>();
		personstolist=handler.readPersons("filenombres.txt");
		String namestolist[] = new String[personstolist.size()+1];
		for (int i = 0; i < personstolist.size(); i++)
			 namestolist[i] = personstolist.get(i).getName();

		setListAdapter(
                new ArrayAdapter<String>(this,
                      R.layout.activity_given,
                      R.id.name,  //In the layout
                      namestolist)); //From 0 to the last name
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.given, menu);
		return true;
	}*/

}

package msp.loanmanager; //Change name according to others

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class Given extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_given); //Change name to others
		setListAdapter(
                new ArrayAdapter<String>(this,
                      R.layout.activity_given, //Change name according to others
                      R.id.name/*,
                      OurProyect.store.givennames(10)*/)); //From 0 to the last name
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.given, menu);
		return true;
	}*/

}

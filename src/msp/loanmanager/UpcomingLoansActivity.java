package msp.loanmanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UpcomingLoansActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming_loans);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upcoming_loans, menu);
		return true;
	}

}

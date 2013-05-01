package msp.loanmanager;

import msp.action.DataHandler;
import msp.object.Loan;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GiveActivity extends Activity {

	private DataHandler handler = new DataHandler();
	// lg - loans given
	private String fileName = MainActivity.PATH + "lg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give);
        Button add = (Button)findViewById(R.id.add_loan_confirm);
		add.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 Loan loan = new Loan();
		    	 int loanId;
		    	 if (MainActivity.loans.size() == 0){
		    		 loanId = 1000;
		    	 }else{
		    		 loanId = MainActivity.loans.get(MainActivity.loans.size()-1).getId() + 1;		    		
		    	 }
		    	 loan.setId(loanId);
		    	 
		    	 EditText personName = (EditText)findViewById(R.id.add_person_name);
		    	 EditText itemName = (EditText)findViewById(R.id.add_item_name);
		    	 EditText categoryName = (EditText)findViewById(R.id.add_category_name);
		    	 EditText itemCount = (EditText)findViewById(R.id.add_item_count);
		    	 EditText loanDate = (EditText)findViewById(R.id.add_loan_date);
		    	 EditText dueDate = (EditText)findViewById(R.id.add_due_date);
		    	 EditText amount = (EditText)findViewById(R.id.add_amount);
		    	 
		    	 loan.setAmount(Integer.valueOf(amount.getText().toString()));
		    	 
			     Intent intent = new Intent(GiveActivity.this, MainActivity.class);	
			     startActivity(intent);
		     }
		 });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.give, menu);
        return true;
	}

}

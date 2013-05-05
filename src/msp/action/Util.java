package msp.action;

import java.util.ArrayList;

import msp.loanmanager.MainActivity;
import msp.object.Group;
import msp.object.Person;
import android.content.Context;
import android.widget.Toast;

public class Util {

	public static int getPersonIdFromName(String personName) {
		int retVal = -1;
		ArrayList<Person> personList = MainActivity.persons;
		for(Person person: personList){
			if(personName.equals(person.getName())){
				retVal = person.getId();
			}
		}
		return retVal;
	}

	public static int getGroupIdFromName(String groupName) {
		int retVal = -1;
		ArrayList<Group> groupList = MainActivity.groups;
		for(Group group: groupList){
			if(groupName.equals(group.getGroupName())){
				retVal = group.getId();
			}
		}
		return retVal;
	}
	
	public static void showToastMessage(Context ctxt,String message){
		
		Toast.makeText(ctxt,message,Toast.LENGTH_SHORT).show();
	}
	
}

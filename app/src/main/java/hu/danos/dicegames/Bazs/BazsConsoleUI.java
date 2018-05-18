package hu.danos.dicegames.Bazs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BazsConsoleUI implements BazsUIInterface{

	@Override
	public boolean Believe(BazsRollable saidRoll) {
		System.out.println("Az el�z� j�t�kos azt mondja, ennyit dobott: " + saidRoll.getRollNumber() + " Elhiszed? (igen/nem)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return input.equals("igen");
	}

	@Override
	public void Roll(BazsRollable roll) {
		System.out.println("Dob�shoz nyomd meg az entert!");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(roll.getRollNumber() + "-t dobt�l.");
		
	}


	@Override
	public void SaidIsSmaller() {
		System.out.println("Nagyobbat kell mondanod mint az el�z�!");		
	}


	@Override
	public BazsRollable Say() {
		BazsDice d = new BazsDice();
		int intput;
		BazsRollable say = null;
		do
		{
		System.out.println("Mondj egy sz�mot!");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {e.printStackTrace();}
		intput = Integer.parseInt(input);
		say = d.intToRollable(intput);
		if (say == null)
			System.out.println("�rv�nytelen!");
		}while (say == null);
		return say;
	}

	@Override
	public void SetGuggolas() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpDateUI(UIDataType type, String value) {
		// TODO Auto-generated method stub
		
	}

}

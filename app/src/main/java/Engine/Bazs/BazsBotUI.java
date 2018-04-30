package Engine.Bazs;
import java.util.Random;

public class BazsBotUI implements BazsUIInterface{

	private BazsRollable roll;
	private BazsRollable previousSay;
	boolean guggolas;
	@Override
	public boolean Believe(BazsRollable saidRoll) {
		previousSay = saidRoll;
		double probabilityOfBelieving;
		if (saidRoll.getValue() == 0)
			probabilityOfBelieving = 1;
		else if (saidRoll.getValue() <=4)
			probabilityOfBelieving = 0.95;
		
		else if (saidRoll.getValue() <=8)
			probabilityOfBelieving = 0.85;
		
		else if (saidRoll.getValue() <=11)
			probabilityOfBelieving = 0.40;
		
		else if (saidRoll.getValue() <=13)
			probabilityOfBelieving = 0.25;
		
		else if (saidRoll.getValue() <=16)
			probabilityOfBelieving = 0.15;
		
		else if (saidRoll.getValue() <=18)
			probabilityOfBelieving = 0.10;
		else if (saidRoll.getValue() <=19)
			probabilityOfBelieving = 0.05;
		else probabilityOfBelieving = 0; //21-et implementalni
		
		Random r = new Random();
		double belief = r.nextDouble();
		/*
		if (belief < probabilityOfBelieving)
			System.out.println("Bot believes " + saidRoll.getRollNumber());
		else
			System.out.println("Bot doesn't believe " + saidRoll.getRollNumber());*/
		return (belief < probabilityOfBelieving);
	}

	@Override
	public void Roll(BazsRollable roll) {
		// TODO Auto-generated method stub
		this.roll=roll;
		//.out.println("Bot rolled " + roll.getRollNumber());
	}

	@Override
	public BazsRollable Say() {
		if (guggolas)
		{
			guggolas = false; //faradtkod
			//System.out.println("Bot said " + roll.getRollNumber());
			return roll; //botok guggolasbol egyelore nem hazudnak
			
		}
		else
		{
			if (previousSay == null || roll.getValue() > previousSay.getValue()) //ha nagyobbat dobott igazat mond
			{
				//System.out.println("Bot said " + roll.getRollNumber());
				return roll;
			}
			else //ha nem akkor kamuzik
			{
				BazsDice d = new BazsDice();
				int maxLie;
				
				if (previousSay.getValue() <= 4)
					maxLie = 5;
				else if (previousSay.getValue() <= 8)
					maxLie = 4;
				else if (previousSay.getValue() <= 11)
					maxLie = 2;
				else
					maxLie = 5;
				Random r = new Random();
				int lie = r.nextInt(maxLie)+1;
				//System.out.println("Bot said " + d.getBiggerThan(previousSay, lie).getRollNumber());
				return d.getBiggerThan(previousSay, lie);
				
			}
		}
	}

	@Override
	public void SaidIsSmaller() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetGuggolas() {
		guggolas = true;
		
	}

	@Override
	public void UpDateUI(UIDataType type, String value) {
		// TODO Auto-generated method stub
		
	}

}

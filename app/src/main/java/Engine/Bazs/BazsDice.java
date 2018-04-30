package Engine.Bazs;
import java.util.ArrayList;
import java.util.Random;

public class BazsDice {
	private ArrayList<BazsRollable> rollables;
	
	public BazsDice()
	{
		rollables = new ArrayList<BazsRollable>();
		rollables.add(new BazsRollable(31,rollables.size()));	//v=0
		rollables.add(new BazsRollable(32,rollables.size()));
		rollables.add(new BazsRollable(41,rollables.size()));
		rollables.add(new BazsRollable(42,rollables.size()));
		rollables.add(new BazsRollable(43,rollables.size()));	//v=4
		rollables.add(new BazsRollable(51,rollables.size()));
		rollables.add(new BazsRollable(52,rollables.size()));
		rollables.add(new BazsRollable(53,rollables.size()));
		rollables.add(new BazsRollable(54,rollables.size()));	//v=8
		rollables.add(new BazsRollable(61,rollables.size()));
		rollables.add(new BazsRollable(62,rollables.size()));
		rollables.add(new BazsRollable(63,rollables.size()));	//v=11
		rollables.add(new BazsRollable(64,rollables.size()));
		rollables.add(new BazsRollable(65,rollables.size()));	//v=13
		rollables.add(new BazsRollable(11,rollables.size()));
		rollables.add(new BazsRollable(22,rollables.size()));
		rollables.add(new BazsRollable(33,rollables.size()));	//v=16
		rollables.add(new BazsRollable(44,rollables.size()));
		rollables.add(new BazsRollable(55,rollables.size()));	//v=18
		rollables.add(new BazsRollable(66,rollables.size()));
		rollables.add(new BazsRollable(21,rollables.size()));	//v=20
	}
	
	public BazsRollable Roll()
	{
		Random r = new Random();
		int die_1 = r.nextInt(6)+1;
		int die_2 = r.nextInt(6)+1;
		if (die_2>die_1)
		{
			int tmp = die_1;
			die_1 = die_2;
			die_2 = tmp;
		}
		int rollInt = 10*die_1+die_2;
		BazsRollable roll = null;
		for (int i = 0; i < rollables.size(); i++) {
			if (rollables.get(i).getRollNumber() == rollInt)
			{
				roll = rollables.get(i);
				break;
			}
		}
		return roll;
	}
	public BazsRollable intToRollable(int in)
	{
		BazsRollable rollable = null;
		for (int i = 0; i < rollables.size(); i++)
		{
			if (in == rollables.get(i).getRollNumber())
			{
				rollable = rollables.get(i);
				break;
			}
		}
		return rollable;
	}
	public BazsRollable getBiggerThan(BazsRollable roll, int howMuchBigger)
	{
		int index = roll.getValue()+howMuchBigger;
		if (index >= rollables.size()-1)
			index = 19;
		return rollables.get(index);
	}
}

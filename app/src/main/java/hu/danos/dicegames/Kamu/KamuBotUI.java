package hu.danos.dicegames.Kamu;

import java.math.BigInteger;
import java.util.ArrayList;

public class KamuBotUI implements KamuUIInterface{
	private ArrayList<Integer> roll;
	private KamuRollable said;
	private boolean guggolas;
	@Override
	public void getRoll(ArrayList<Integer> roll) {
		this.roll = roll;
		System.out.println("A bot ak�vetkez�ket dobta: ");
		for (int i = 0; i< roll.size(); i++)
		{
			System.out.print(roll.get(i) + " ");
		}
		System.out.println("");
	}
	@Override
	public void Guggolas()
	{
		guggolas = true;
	}
	@Override
	public boolean Believe(KamuRollable said, int numberOfDiceLeft) {
		//azert vannak ilyen egybetus valtozonevek mert papiron levezetett matekbol emeltem at a kepleteket
		//kivenni a saj�t kock�kat a k�pletb�l
		this.said = said;
		guggolas = false;
		int x = said.getQuantity();
		int n = numberOfDiceLeft;
		for (int i = 0; i<roll.size();i++)
		{
			if (roll.get(i) == said.getRollNumber())
			{
				n--;
				x--;
			}
		}
		
		
		double probability = 0;
		for (int k = x; k <=n; k++)
		{
			probability += Choose(n,k)*Math.pow((double)1/(double)6, k)*Math.pow((double)5/(double)6, n-k); //mukodj pls
		
		}
		//System.out.println("Probability: " + probability);
		return probability > 0.5;
	}

	@Override
	public KamuRollable Say() {
		KamuRollable say;
		//TODO tipp algoritmust jav�tani
		if (guggolas)
		{
			say = new KamuRollable(roll.get(0),1);
		}
		else
		{
			say = new KamuRollable(said.getRollNumber(),said.getQuantity()+1);
		}
		return say;
	}

	@Override
	public void SaidIsSmaller() {
		// TODO Auto-generated method stub
		
	}
	
	public BigInteger Factorial(int x)
	{
		BigInteger result = BigInteger.valueOf(1);
		for (int i = 2; i<=x; i++)
		{
			result = result.multiply(BigInteger.valueOf(i));
			//result*=i;
		}
		//System.out.println(result);
		return result;
	}
	public int Choose(int n, int k)
	{
		BigInteger result = Factorial(n).divide((Factorial(k).multiply(Factorial(n-k))));
		return result.intValue();
	}
	

}

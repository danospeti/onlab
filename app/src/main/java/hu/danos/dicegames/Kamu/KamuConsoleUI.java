package hu.danos.dicegames.Kamu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class KamuConsoleUI implements KamuUIInterface{

	@Override
	public void getRoll(ArrayList<Integer> roll) {
		System.out.println("A k�vetkez�ket dobtad: ");
		for (int i = 0; i< roll.size(); i++)
		{
			System.out.print(roll.get(i) + " ");
		}
		System.out.println("");
		
	}

	@Override
	public boolean Believe(KamuRollable said, int numberOfDiceLeft) {
		// TODO Auto-generated method stub
		System.out.println(numberOfDiceLeft + " kocka van m�g a j�t�kban. Az el�z� j�t�kos szerint van " + said.getQuantity() + " db " + said.getRollNumber());
		System.out.println("Elhiszed?");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {input = br.readLine();}
		catch (IOException e) {e.printStackTrace();}		
		return input.equals("igen");
	}

	@Override
	public KamuRollable Say() {
		KamuRollable outSay;
		//eleg rosszul van megvalositva, de mivel ez a konzolos felulet csak tesztelni van igy mindegy
		System.out.println("Szerintem van...db");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {input = br.readLine();}
		catch (IOException e) {e.printStackTrace();}
		int intput = Integer.parseInt(input);
		System.out.println("...-es/os/as");
		try {input = br.readLine();}
		catch (IOException e) {e.printStackTrace();}
		int int2put = Integer.parseInt(input);
		outSay = new KamuRollable(int2put,intput);	
		return outSay;
	}

	@Override
	public void SaidIsSmaller() {
		System.out.println("Az el�z�n�l nagyobbat kell mondanod!");
		
	}

	@Override
	public void Guggolas() {
		// TODO Auto-generated method stub
		
	}

	
}

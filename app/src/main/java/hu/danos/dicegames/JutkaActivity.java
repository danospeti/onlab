package hu.danos.dicegames;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import hu.danos.dicegames.Jutka.JutkaDie;
import hu.danos.dicegames.Jutka.JutkaGameController;
import hu.danos.dicegames.Jutka.JutkaPlayer;

public class JutkaActivity extends AppCompatActivity {

    private ImageButton btnsAsztalon[];
    private ImageButton btnsInHand[];
    private ArrayList<JutkaDie> table = new ArrayList<JutkaDie>();
    private ArrayList<JutkaDie> hand = new ArrayList<JutkaDie>();
    private TextView txtRoundPoints;
    private TextView nameLabel;
    private TextView points;
    private TextView negativeCountLabel;
    private ProgressBar progressBar;
    private Button btnMegallas;
    private boolean hasRolled = false;
    private boolean hasKept = false;
    private Button btnDobas;
    int roundPoints = 0;
    private JutkaGameController gameController;
    private MediaPlayer slide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jutka);
        btnsAsztalon = new ImageButton[6];
        btnsAsztalon[0] = (ImageButton) findViewById(R.id.btnDice1);
        btnsAsztalon[1] = (ImageButton) findViewById(R.id.btnDice2);
        btnsAsztalon[2] = (ImageButton) findViewById(R.id.btnDice3);
        btnsAsztalon[3] = (ImageButton) findViewById(R.id.btnDice4);
        btnsAsztalon[4] = (ImageButton) findViewById(R.id.btnDice5);
        btnsAsztalon[5] = (ImageButton) findViewById(R.id.btnDice6);
        txtRoundPoints = (TextView) findViewById(R.id.txtRoundPoints);
        btnDobas = (Button) findViewById(R.id.btnDobas);
        final Button btnKeep = (Button) findViewById(R.id.btnKeep);
        btnMegallas = (Button) findViewById(R.id.btnPassz);

        btnsInHand = new ImageButton[6];
        btnsInHand[0] = (ImageButton) findViewById(R.id.btnDiceHand1);
        btnsInHand[1] = (ImageButton) findViewById(R.id.btnDiceHand2);
        btnsInHand[2] = (ImageButton) findViewById(R.id.btnDiceHand3);
        btnsInHand[3] = (ImageButton) findViewById(R.id.btnDiceHand4);
        btnsInHand[4] = (ImageButton) findViewById(R.id.btnDiceHand5);
        btnsInHand[5] = (ImageButton) findViewById(R.id.btnDiceHand6);
        for (int i = 0; i < 6; i++)
        {
            btnsInHand[i].setImageAlpha(0);
            btnsInHand[i].setAlpha((float)0);
        }

        nameLabel = (TextView) findViewById(R.id.nameLabel);
        points = (TextView) findViewById(R.id.scoreLabel);
        negativeCountLabel = (TextView) findViewById(R.id.negativeCount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final TextView roundNuberLbl = (TextView) findViewById(R.id.roundNumberLabel);
        gameController = JutkaGameController.getInstance();
        gameController.initGame();

        JutkaPlayer currentPlayer = gameController.getCurrentPlayer();
        nameLabel.setText(currentPlayer.getName());
        points.setText(String.valueOf(currentPlayer.getPoint()));
        progressBar.setProgress(currentPlayer.getnegativeSzeria());
        negativeCountLabel.setText(String.valueOf(currentPlayer.getnegativePoint()));
        txtRoundPoints.setText(R.string.zeroPoints);
        roundNuberLbl.setText(String.valueOf(gameController.getRoundNumber()+1) + getString(R.string.kor));

        final MediaPlayer roll_1 = MediaPlayer.create(this, R.raw.dice1);
        final MediaPlayer roll_2 = MediaPlayer.create(this, R.raw.dice2);
        final MediaPlayer roll_3 = MediaPlayer.create(this, R.raw.dice3);
        slide = MediaPlayer.create(this, R.raw.slide);
        btnDobas.setSoundEffectsEnabled(false);
        btnKeep.setSoundEffectsEnabled(false);

        btnMegallas.setEnabled(false);
        btnDobas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!hasRolled || hasKept)
                {
                    Random r = new Random();
                    int rollSound = r.nextInt(2);
                    if (rollSound == 1) roll_1.start();
                    else if (rollSound == 2) roll_2.start();
                    else  roll_3.start();

                    for (int i = 0; i < 6; i++) {
                        table.get(i).Roll();
                        table.get(i).setKeep(false);
                    }
                    hasRolled = true;
                    hasKept = false;
                    PlayableCalc();
                    SetTableImages();

                }
                else Toast.makeText(getBaseContext(), R.string.getAtLeastOneDie,
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (hasRolled) {
                    //PlayableCalc();
                    int pointsToAdd = CalculatePoints();
                    roundPoints += pointsToAdd;
                    txtRoundPoints.setText(String.valueOf(roundPoints) + getString(R.string.point));
                    SetTableImages();

                    SetHandImages();
                    if (roundPoints>=350) btnMegallas.setEnabled(true);
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.rollAtLeastOnce,
                          Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnMegallas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                gameController.setResultForCurrentPlayer(roundPoints);
                gameController.switchToNextPlayer();
                roundNuberLbl.setText(String.valueOf(gameController.getRoundNumber()+1) + getString(R.string.kor));
                Reset();
            }
        });
        btnsAsztalon[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DiceOnClick(0);
            }
        });
        btnsAsztalon[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DiceOnClick(1);
            }
        });
        btnsAsztalon[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DiceOnClick(2);
            }
        });
        btnsAsztalon[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DiceOnClick(3);
            }
        });
        btnsAsztalon[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DiceOnClick(4);
            }
        });
        btnsAsztalon[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DiceOnClick(5);
            }
        });

        for (int i = 0; i < 6; i++)
        {
            table.add(new JutkaDie());
            hand.add(new JutkaDie());
            hand.get(i).setEnabled(false);
        }

        Reset();
    }


    private void Reset()
    {
        for (int i = 0; i < 6; i++)
        {
            table.get(i).setEnabled(true);
            table.get(i).setKeep(false);
            hand.get(i).setEnabled(false);
            btnsInHand[i].setImageAlpha(0);
            btnsInHand[i].setAlpha((float)0);
            SetTableImages();
        }
        JutkaPlayer currentPlayer = gameController.getCurrentPlayer();
        nameLabel.setText(currentPlayer.getName());
        points.setText(String.valueOf(currentPlayer.getPoint()));
        progressBar.setProgress(currentPlayer.getnegativeSzeria());
        negativeCountLabel.setText(String.valueOf(currentPlayer.getnegativePoint()));
        txtRoundPoints.setText(R.string.zeroPoints);
        btnMegallas.setEnabled(false);
        hasRolled = false;
        hasKept = false;
        roundPoints = 0;
        btnDobas.setEnabled(true);
    }
    private void DiceOnClick(int i)
    {

        if (hasRolled && table.get(i).getEnabled()) {
                if (table.get(i).getKeep()) {
                    table.get(i).setKeep(false);
                    SetTableImages();
                } else {
                    table.get(i).setKeep(true);
                    SetTableImages();
                }
        }
        else if (!hasRolled)
            Toast.makeText(getBaseContext(), R.string.rollAtLeastOnce,
                    Toast.LENGTH_SHORT).show();
    }
    private void SetTableImages()
    {

        for (int i = 0; i < 6; i++)
        {
            int number = table.get(i).getValue();
            boolean keep = table.get(i).getKeep();
            if (keep)
            {
                if (number == 1) btnsAsztalon[i].setImageResource(R.drawable.dice1_t);
                if (number == 2) btnsAsztalon[i].setImageResource(R.drawable.dice2_t);
                if (number == 3) btnsAsztalon[i].setImageResource(R.drawable.dice3_t);
                if (number == 4) btnsAsztalon[i].setImageResource(R.drawable.dice4_t);
                if (number == 5) btnsAsztalon[i].setImageResource(R.drawable.dice5_t);
                if (number == 6) btnsAsztalon[i].setImageResource(R.drawable.dice6_t);
            }
            else
            {
                if (number == 1) btnsAsztalon[i].setImageResource(R.drawable.dice1);
                if (number == 2) btnsAsztalon[i].setImageResource(R.drawable.dice2);
                if (number == 3) btnsAsztalon[i].setImageResource(R.drawable.dice3);
                if (number == 4) btnsAsztalon[i].setImageResource(R.drawable.dice4);
                if (number == 5) btnsAsztalon[i].setImageResource(R.drawable.dice5);
                if (number == 6) btnsAsztalon[i].setImageResource(R.drawable.dice6);
            }

            if (table.get(i).getEnabled())
            {
                btnsAsztalon[i].setAlpha((float)1);
                btnsAsztalon[i].setImageAlpha(255);
            }
            else
            {
                btnsAsztalon[i].setAlpha((float)0);
                btnsAsztalon[i].setImageAlpha(0);
            }
        }
    }

    private void SetHandImages()
    {
        for (int i = 0; i < 6; i++)
        {
            int number = hand.get(i).getValue();
            if (number == 1) btnsInHand[i].setImageResource(R.drawable.dice1);
            if (number == 2) btnsInHand[i].setImageResource(R.drawable.dice2);
            if (number == 3) btnsInHand[i].setImageResource(R.drawable.dice3);
            if (number == 4) btnsInHand[i].setImageResource(R.drawable.dice4);
            if (number == 5) btnsInHand[i].setImageResource(R.drawable.dice5);
            if (number == 6) btnsInHand[i].setImageResource(R.drawable.dice6);
            if (hand.get(i).getEnabled())
            {
                btnsInHand[i].setAlpha((float)1);
                btnsInHand[i].setImageAlpha(255);
            }
            else
            {
                btnsInHand[i].setAlpha((float)0);
                btnsInHand[i].setImageAlpha(0);
            }
        }

    }
    private void PlayableCalc()
    {
        int[] numberOfValue = new int[6]; //0-tól 6-ig!! +1
        for (int i = 0; i < table.size(); i++)
            if (table.get(i).getEnabled())numberOfValue[table.get(i).getValue()-1]++;
        //set all dice playable false
        for (int i = 0; i < table.size(); i++) table.get(i).setPlayable(false);

        int jutka = 0;
        for (int i = 0; i < table.size(); i++)
            if (table.get(i).getEnabled() && table.get(i).getValue() == 1 ||table.get(i).getValue() == 5 ||numberOfValue[table.get(i).getValue()-1]>=3) {
                table.get(i).setPlayable(true);
            }
        for (int i = 0; i < table.size(); i++) if (table.get(i).getEnabled() && !table.get(i).getPlayable()) jutka++;
        if (jutka == 6) Toast.makeText(getBaseContext(), R.string.jutka,
                Toast.LENGTH_SHORT).show();

        boolean unPlayable = true;
        for (int i = 0; i < table.size(); i++)
        {
            if (table.get(i).getEnabled() && table.get(i).getPlayable()) {
                unPlayable = false;
            }
        }
        if (unPlayable)
        {
            roundPoints = -1*Math.abs(roundPoints);
            txtRoundPoints.setText(String.valueOf(roundPoints));
            Toast.makeText(getBaseContext(), R.string.badRoll,
                    Toast.LENGTH_SHORT).show();
            btnMegallas.setEnabled(true);
        }
    }
    private int CalculatePoints()
    {
        int points =0;
        for (int v = 1; v<=6; v++)
        {
            int count = 0;
            boolean allKeptArePlayable = true;
            for(int i = 0; i < table.size(); i++) {
                if (table.get(i).getEnabled() && table.get(i).getPlayable() && table.get(i).getKeep() && table.get(i).getValue() == v) {
                    count++;
                }
                if (table.get(i).getKeep() && !table.get(i).getPlayable())
                allKeptArePlayable = false;
            }
            if ((v == 5 || v == 1 || count>=3) && count > 0 && allKeptArePlayable)
            {
                for (int i = 0; i < table.size(); i++) {
                    if (table.get(i).getEnabled() && table.get(i).getPlayable() && table.get(i).getKeep() && table.get(i).getValue() == v) {
                        table.get(i).setEnabled(false);
                        hand.get(i).setEnabled(true);
                        hand.get(i).setValue(table.get(i).getValue());
                    }
                }
                if (v == 5 && count < 3) {
                    points += count * 50;
                }
                else if (v == 1 && count < 3) {
                    points += count * 100;
                }else if (v == 1) {
                    points += 1000 * Math.pow(2, count - 3);
                } else {
                    points += 100 * v * Math.pow(2, count - 3);
                }
                slide.start();
                hasKept = true;
                btnDobas.setEnabled(true);

            }
        }
        boolean noDiceLeft = true;
        for (int i = 0; i < 6; i++) {
            if (table.get(i).getEnabled())
                noDiceLeft = false;
        }
        if (noDiceLeft) {
            for (int i = 0; i < 6; i++) {
                table.get(i).setEnabled(true);
                table.get(i).setKeep(false);
                hand.get(i).setEnabled(false);
            }
            //btnDobas.setEnabled(false);
            hasRolled = false;
            hasKept = false;
        }
        return points;
    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}

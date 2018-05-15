package hu.danos.dicegames;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Engine.Bazs.BazsBotUI;
import Engine.Bazs.BazsDice;
import Engine.Bazs.BazsGameController;
import Engine.Bazs.BazsPlayer;
import Engine.Bazs.BazsPlayerAndroid;
import Engine.Bazs.BazsRollable;
import Engine.Bazs.BazsTurnData;
import Engine.Bazs.UIDataType;
import hu.danos.dicegames.Bazs.BazsUpdate;
import hu.danos.dicegames.Bazs.TurnStage;

public class BazsActivity extends AppCompatActivity {
    private TextView txtBotName_1;
    private TextView txtBotName_2;
    private TextView txtBotName_3;
    private TextView txtBotPoints_1;
    private TextView txtBotPoints_2;
    private TextView txtBotPoints_3;
    private TextView txtTablePoints;
    private TextView txtRound;
    private TextView txtBotSays_1;
    private TextView txtBotSays_2;
    private TextView txtBotSays_3;
    private TextView txtPlayerSays;
    private TextView txtPlayerPoints;
    private TextView txtMessage;
    private TextView txtPlayerName;

    private Button btnBelieve;
    private Button btnDontBelieve;
    private Button btnSay;
    private Button btnRoll;

    private ImageView imgDie_1;
    private ImageView imgDie_2;
    private ImageView imgBubble1;
    private ImageView imgBubble2;
    private ImageView imgBubble3;
    private ImageView imgBubble4;
    private Spinner spinDie_1;
    private Spinner spinDie_2;

    private BazsGameController gc;
    private BazsPlayerAndroid player;
    private BazsDice dice;
    private int roundNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazs);
        txtBotName_1 = (TextView) findViewById(R.id.txtBotName_1);
        txtBotName_2= (TextView) findViewById(R.id.txtBotName_2);
        txtBotName_3= (TextView) findViewById(R.id.txtBotName_3);
        txtBotPoints_1= (TextView) findViewById(R.id.txtBotPoints_1);
        txtBotPoints_2= (TextView) findViewById(R.id.txtBotPoints_2);
        txtBotPoints_3= (TextView) findViewById(R.id.txtBotPoints_3);
        txtTablePoints= (TextView) findViewById(R.id.txtTablePoints);
        txtRound= (TextView) findViewById(R.id.txtRound);
        txtBotSays_1= (TextView) findViewById(R.id.txtBotSays_1);
        txtBotSays_2= (TextView) findViewById(R.id.txtBotSays_2);
        txtBotSays_3= (TextView) findViewById(R.id.txtBotSays_3);
        txtPlayerSays= (TextView) findViewById(R.id.txtPlayerSays);
        txtPlayerPoints= (TextView) findViewById(R.id.txtPlayerPoints);
        txtMessage= (TextView) findViewById(R.id.txtMessage);
        txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);


        btnDontBelieve = (Button) findViewById(R.id.btnDontBelieve);
        btnBelieve = (Button) findViewById(R.id.btnBelieve);
        btnSay = (Button) findViewById(R.id.btnSay);
        btnRoll = (Button) findViewById(R.id.btnRoll);

        imgDie_1 = (ImageView) findViewById(R.id.imgDie_1);
        imgDie_2 = (ImageView) findViewById(R.id.imgDie_2);
        imgBubble1 = (ImageView) findViewById(R.id.imgBubble1);
        imgBubble2 = (ImageView) findViewById(R.id.imgBubble2);
        imgBubble3 = (ImageView) findViewById(R.id.imgBubble3);
        imgBubble4 = (ImageView) findViewById(R.id.imgBubble4);




        spinDie_1 = (Spinner) findViewById(R.id.spinDie_1);
        spinDie_2 = (Spinner) findViewById(R.id.spinDie_2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDie_1.setAdapter(adapter);
        spinDie_2.setAdapter(adapter);



        InitializeGameController();
        btnBelieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                setTurnStage(TurnStage.ROLL);
                new SleepThenWrite2().execute(new BazsUpdate(false,UIDataType.PLAYERBELIEVES, "Nem hiszem el."));
                new SleepThenWrite2().execute(new BazsUpdate(false,UIDataType.PLAYERBELIEVES, "delete"));
            }
        });

        btnDontBelieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (player.getData().getRoll().getRollNumber() == player.getData().getSaidRoll().getRollNumber())
                {
                    //new SleepThenWrite().execute("msg: " + player.getData().getPlayer().getName() + "Igazat mondott");
                    new SleepThenWrite2().execute(new BazsUpdate(false,UIDataType.MESSAGE, player.getData().getPlayer().getName() + " igazat mondott."));
                    new SleepThenWrite2().execute(new BazsUpdate(true,UIDataType.MESSAGE, "delete"));
                    if (!gc.getPenaltyReverse()) //ha van m�g b�ntet�pont h�z�s van
                    {
                        player.AddPenaltyPoint();
                        txtPlayerPoints.setText(String.valueOf(player.getPenaltyPoints()));
                        gc.RemovePenaltyPoint();
                    }
                    else //ha b�ntet�pont visszatev�s van
                    {
                        //TODO nyertˇˇ
                        /*
                        if(data.getPlayer().RemovePenaltyPoint())
                            outData.setWinner(data.getPlayer());
                            */
                        gc.AddPenaltyPoint();
                    }
                }
                else
                {
                    //new SleepThenWrite().execute("msg: " + player.getData().getPlayer().getName() + " hazudott");
                    new SleepThenWrite2().execute(new BazsUpdate(false,UIDataType.MESSAGE, player.getData().getPlayer().getName() + " hazudott."));
                    new SleepThenWrite2().execute(new BazsUpdate(true,UIDataType.MESSAGE, "delete"));
                    if (!gc.getPenaltyReverse()) //ha van m�g b�ntet�pont h�z�s van
                    {
                        player.getData().getPlayer().AddPenaltyPoint();
                        gc.RemovePenaltyPoint();
                    }
                    else //ha b�ntet�pont visszatev�s van
                    {
                        //TODO nyertˇˇ
                        /*
                        if (RemovePenaltyPoint()) // nyertes be�ll�t�sa ha nyert
                            outData.setWinner(this);
                            */
                        gc.AddPenaltyPoint();
                    }
                }
                player.getData().setGuggolas(true);
                //new SleepThenWrite().execute("setTurnState:ROLL");
                new SleepThenWrite2().execute(new BazsUpdate(false,UIDataType.PLAYERBELIEVES, "Nem hiszem el."));
                new SleepThenWrite2().execute(new BazsUpdate(false,UIDataType.PLAYERBELIEVES, "delete"));
                new SleepThenWrite2().execute(new BazsUpdate(true,UIDataType.STATE_ROLL, ""));
            }
        });

        btnSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                 int sayInt = Integer.parseInt((String)spinDie_1.getSelectedItem())*10+Integer.parseInt((String)spinDie_2.getSelectedItem());
                    BazsRollable say = dice.intToRollable(sayInt);
                    if (say == null)
                    {
                        Toast.makeText(getBaseContext(), "Érvénytelen input!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (!player.getData().getGuggolas())
                    {
                        if (say.getValue() <= player.getData().getSaidRoll().getValue())
                        {
                            Toast.makeText(getBaseContext(), "Nagyobbat kell mondanod!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            player.getOutData().setSaidRoll(say);
                            EndTurn();
                        }
                    }
                    else
                    {
                        player.getOutData().setSaidRoll(say);
                        EndTurn();
                    }
                /*
                String blyat = String.valueOf(spinDie_1.getSelectedItem()) + String.valueOf(spinDie_2.getSelectedItem());
                Toast.makeText(getBaseContext(), blyat,
                        Toast.LENGTH_SHORT).show();
                        */
            }
        });

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                BazsRollable roll = dice.Roll();
                player.getOutData().setRoll(roll);
                //TODO dobáshang
                int dieNumber1 = (int) Math.floor(roll.getRollNumber()/10);
                int dieNumber2 = roll.getRollNumber() % 10;

                if (dieNumber1 == 1) imgDie_1.setImageResource(R.drawable.dice1);
                if (dieNumber1 == 2) imgDie_1.setImageResource(R.drawable.dice2);
                if (dieNumber1 == 3) imgDie_1.setImageResource(R.drawable.dice3);
                if (dieNumber1 == 4) imgDie_1.setImageResource(R.drawable.dice4);
                if (dieNumber1 == 5) imgDie_1.setImageResource(R.drawable.dice5);
                if (dieNumber1 == 6) imgDie_1.setImageResource(R.drawable.dice6);

                if (dieNumber2 == 1) imgDie_2.setImageResource(R.drawable.dice1);
                if (dieNumber2 == 2) imgDie_2.setImageResource(R.drawable.dice2);
                if (dieNumber2 == 3) imgDie_2.setImageResource(R.drawable.dice3);
                if (dieNumber2 == 4) imgDie_2.setImageResource(R.drawable.dice4);
                if (dieNumber2 == 5) imgDie_2.setImageResource(R.drawable.dice5);
                if (dieNumber2 == 6) imgDie_2.setImageResource(R.drawable.dice6);

                if (player.getData().getRoll() != null && (player.getData().getRoll().getRollNumber() == 21 || player.getData().getRoll().getRollNumber() == 66))
                {
                    if (roll.getRollNumber() !=21)
                    {
                        if (!gc.getPenaltyReverse())
                        {
                            player.AddPenaltyPoint();
                            //TODO ez így kurvára nem állapotˇˇ
                            txtPlayerPoints.setText(String.valueOf(player.getPenaltyPoints()));
                            gc.RemovePenaltyPoint();
                        }
                        else
                        {
                            player.getData().getPlayer().RemovePenaltyPoint();
                            gc.AddPenaltyPoint();
                        }
                        player.getOutData().setGuggolas(true);
                       //kör vége
                        EndTurn();
                    }
                }
                else //ha az előző nem 21
                {
                    setTurnStage(TurnStage.SAY);
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void EndTurn()
    {
        if (player.getOutData().getWinner() != null)
        {
            //TODO nyertél
        }
        else
        {
            player.getOutData().setPlayer(player);
            setTurnStage(TurnStage.WAIT);
            player.setData(gc.PlayARound(player.getOutData()));//egy botkör lemegy
            PlayUIUpdates();
            new SleepThenWrite2().execute(new BazsUpdate(false, UIDataType.ROUND, String.valueOf(++roundNumber)));
            if (player.getData().getGuggolas())
            {
                //new SleepThenWrite().execute("setTurnState:ROLL");
                new SleepThenWrite2().execute(new BazsUpdate(true,UIDataType.STATE_ROLL, ""));
            }
            else
            {
                //TODO if 21
                if (player.getData().getRoll().getRollNumber() == 21)
                {
                    //new SleepThenWrite().execute("setTurnState:ROLL");
                    new SleepThenWrite2().execute(new BazsUpdate(true,UIDataType.STATE_ROLL, ""));
                }
                else
                {
                    //new SleepThenWrite().execute("setTurnState:BELIEVE");
                    new SleepThenWrite2().execute(new BazsUpdate(true,UIDataType.STATE_BELIEVE, ""));
                }
            }
        }
    }
    private void InitializeGameController()
    {
        BazsBotUI botUI = new BazsBotUI();
        BazsBotUI botUI2 = new BazsBotUI();
        BazsBotUI botUI3= new BazsBotUI();

        String playerName = getIntent().getStringExtra("PLAYER_NAME");
        BazsPlayer jatekos = new BazsPlayerAndroid(playerName,0);
        BazsPlayer bot = new BazsPlayer("Bot Ond",botUI,0);
        BazsPlayer bot2 = new BazsPlayer("Bot Kond",botUI2,0);
        BazsPlayer bot3 = new BazsPlayer("Bot Tas",botUI3,0);


        ArrayList<BazsPlayer> players = new ArrayList<BazsPlayer>();
        players.add(jatekos);
        players.add(bot);
        players.add(bot2);
        players.add(bot3);
        int tablepoints = 10;
        gc = new BazsGameController(players, tablepoints, this);

        roundNumber = 1;
        txtBotName_1.setText(bot.getName());
        txtBotName_2.setText(bot2.getName());
        txtBotName_3.setText(bot3.getName());
        txtPlayerName.setText(jatekos.getName());
        txtBotPoints_1.setText( "0 pont");
        txtBotPoints_2.setText( "0 pont");
        txtBotPoints_3.setText( "0 pont");
        txtPlayerPoints.setText( "0 pont");
        txtTablePoints.setText(String.valueOf(tablepoints) + " pont");

        txtPlayerSays.setText("");
        txtBotSays_1.setText("");
        txtBotSays_2.setText("");
        txtBotSays_3.setText("");

        imgBubble1.setImageAlpha(0);
        imgBubble2.setImageAlpha(0);
        imgBubble3.setImageAlpha(0);
        imgBubble4.setImageAlpha(0);

        this.player = (BazsPlayerAndroid) gc.getPlayerAndroid();

        BazsTurnData data = new BazsTurnData();
        data.setGuggolas(true);
        data.setWinner(null);
        player.setData(data);
        player.setOutData(new BazsTurnData());
        setTurnStage(TurnStage.ROLL);

        dice = new BazsDice();
    }

    private void setTurnStage(TurnStage stage)
    {
        if (stage == TurnStage.ROLL)
        {
            btnBelieve.setEnabled(false);
            btnDontBelieve.setEnabled(false);
            btnSay.setEnabled(false);
            spinDie_1.setEnabled(false);
            spinDie_2.setEnabled(false);
            btnRoll.setEnabled(true);
        }
        else if (stage == TurnStage.BELIEVE)
        {
            btnBelieve.setEnabled(true);
            btnDontBelieve.setEnabled(true);
            btnSay.setEnabled(false);
            spinDie_1.setEnabled(false);
            spinDie_2.setEnabled(false);
            btnRoll.setEnabled(false);
        }

        else if (stage == TurnStage.SAY)
        {
            btnBelieve.setEnabled(false);
            btnDontBelieve.setEnabled(false);
            btnSay.setEnabled(true);
            spinDie_1.setEnabled(true);
            spinDie_2.setEnabled(true);
            btnRoll.setEnabled(false);
        }
        else if (stage == TurnStage.WAIT)
        {
            btnBelieve.setEnabled(false);
            btnDontBelieve.setEnabled(false);
            btnSay.setEnabled(false);
            spinDie_1.setEnabled(false);
            spinDie_2.setEnabled(false);
            btnRoll.setEnabled(false);
        }
    }

    private void PlayUIUpdates()
    {
        ArrayList<BazsUpdate> updates = gc.getUIUpdates();
        for (BazsUpdate update:updates)
        {
            //először minden marad és minden előtt van delay
            //if (update.startsWith("msg")) {}
            new SleepThenWrite2().execute(update);
            //System.out.println(update);
        }
    }
    private class SleepThenWrite extends AsyncTask<String, Void, String> {
        private TextView txt;
        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return params[0];
        }
        protected void onPostExecute(String update) {
            //txt = (TextView) findViewById(R.id.label);
            //txt.setText(result); // txt.setText(result);
            String[] splitUpdate = update.split(":");
            if (splitUpdate[0].equals("msg"))
            {
                txt = (TextView) findViewById(R.id.txtMessage);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("tpts"))
            {
                txt = (TextView) findViewById(R.id.txtTablePoints);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("bot1say"))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_1);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("bot2say"))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_2);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("bot3say"))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_3);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("bot1pts"))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_1);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("bot2pts"))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_2);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("bot3pts"))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_3);
                txt.setText(splitUpdate[1]);
            }
            if (splitUpdate[0].equals("setTurnState"))
            {
                if (splitUpdate[1].equals("ROLL"))
                {
                    btnBelieve.setEnabled(false);
                    btnDontBelieve.setEnabled(false);
                    btnSay.setEnabled(false);
                    spinDie_1.setEnabled(false);
                    spinDie_2.setEnabled(false);
                    btnRoll.setEnabled(true);
                }
                else if (splitUpdate[1].equals("BELIEVE"))
                {
                    btnBelieve.setEnabled(true);
                    btnDontBelieve.setEnabled(true);
                    btnSay.setEnabled(false);
                    spinDie_1.setEnabled(false);
                    spinDie_2.setEnabled(false);
                    btnRoll.setEnabled(false);
                }

            }
        }

    }

    private class SleepThenWrite2 extends AsyncTask<BazsUpdate, Void, BazsUpdate> {
        private TextView txt;
        private ImageView img;
        @Override
        protected BazsUpdate doInBackground(BazsUpdate... params) {
            if (params[0].isWait()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return params[0];
        }
        protected void onPostExecute(BazsUpdate update) {
            if (update.getType().equals(UIDataType.MESSAGE))
            {
                txt = (TextView) findViewById(R.id.txtMessage);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                }
                else
                {
                    txt.setText(update.getValue());
                }
            }
            else if (update.getType().equals(UIDataType.BOT1SAYS))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_1);
                img = (ImageView) findViewById(R.id.imgBubble2);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.down);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.BOT2SAYS))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_2);
                img = (ImageView) findViewById(R.id.imgBubble3);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.right);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.BOT3SAYS))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_3);
                img = (ImageView) findViewById(R.id.imgBubble4);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.up);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.PLAYERSAYS))
            {
                txt = (TextView) findViewById(R.id.txtPlayerSays);
                img = (ImageView) findViewById(R.id.imgBubble1);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.left);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.BOT1BELIEVES))
            {
                txt = (TextView) findViewById(R.id.txtPlayerSays);
                img = (ImageView) findViewById(R.id.imgBubble1);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.up);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.BOT2BELIEVES))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_1);
                img = (ImageView) findViewById(R.id.imgBubble2);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.left);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.BOT3BELIEVES))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_2);
                img = (ImageView) findViewById(R.id.imgBubble3);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.down);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.PLAYERBELIEVES))
            {
                txt = (TextView) findViewById(R.id.txtBotSays_3);
                img = (ImageView) findViewById(R.id.imgBubble4);
                if (update.getValue().equals("delete"))
                {
                    txt.setText("");
                    img.setImageAlpha(0);

                }
                else
                {
                    txt.setText(update.getValue());
                    img.setImageResource(R.drawable.left);
                    img.setImageAlpha(255);
                }
            }
            else if (update.getType().equals(UIDataType.BOT1POINTS))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_1);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(UIDataType.BOT2POINTS))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_2);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(UIDataType.BOT3POINTS))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_3);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(UIDataType.PLAYERPOINTS))
            {
                txt = (TextView) findViewById(R.id.txtPlayerPoints);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(UIDataType.TABLEPOINTS))
            {
                txt = (TextView) findViewById(R.id.txtTablePoints);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(UIDataType.ROUND))
            {
                txt = (TextView) findViewById(R.id.txtRound);
                txt.setText(update.getValue() + ". kör");
            }
            else if (update.getType().equals(UIDataType.STATE_ROLL))
            {
                btnBelieve.setEnabled(false);
                btnDontBelieve.setEnabled(false);
                btnSay.setEnabled(false);
                spinDie_1.setEnabled(false);
                spinDie_2.setEnabled(false);
                btnRoll.setEnabled(true);
            }
            else if (update.getType().equals(UIDataType.STATE_BELIEVE))
            {
                btnBelieve.setEnabled(true);
                btnDontBelieve.setEnabled(true);
                btnSay.setEnabled(false);
                spinDie_1.setEnabled(false);
                spinDie_2.setEnabled(false);
                btnRoll.setEnabled(false);
            }

        }

    }
}

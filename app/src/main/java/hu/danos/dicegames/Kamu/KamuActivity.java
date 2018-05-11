package hu.danos.dicegames.Kamu;

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

import hu.danos.dicegames.R;

public class KamuActivity extends AppCompatActivity {
    private TextView txtBotName_1;
    private TextView txtBotName_2;
    private TextView txtBotName_3;
    private TextView txtBotDice_1;
    private TextView txtBotDice_2;
    private TextView txtBotDice_3;
    private TextView txtRound;
    private TextView txtBotSays_1;
    private TextView txtBotSays_2;
    private TextView txtBotSays_3;
    private TextView txtPlayerSays;
    private TextView txtMessage;
    private TextView txtPlayerName;

    private Button btnBelieve;
    private Button btnDontBelieve;
    private Button btnSay;

    private ImageView imgDie_1;
    private ImageView imgDie_2;
    private ImageView imgDie_3;
    private ImageView imgDie_4;
    private ImageView imgDie_5;
    private ImageView imgBubble1;
    private ImageView imgBubble2;
    private ImageView imgBubble3;
    private ImageView imgBubble4;

    private Spinner spinDie_1;
    private Spinner spinDie_2;

    private KamuGameController gc;
    private KamuPlayerAndroid player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamu);
        txtBotName_1 = (TextView) findViewById(R.id.txtBotName_1);
        txtBotName_2 = (TextView) findViewById(R.id.txtBotName_2);
        txtBotName_3 = (TextView) findViewById(R.id.txtBotName_3);
        txtBotDice_1 = (TextView) findViewById(R.id.txtBotPoints_1);
        txtBotDice_1 = (TextView) findViewById(R.id.txtBotPoints_2);
        txtBotDice_1 = (TextView) findViewById(R.id.txtBotPoints_3);
        txtRound = (TextView) findViewById(R.id.txtRound);
        txtBotSays_1 = (TextView) findViewById(R.id.txtBotSays_1);
        txtBotSays_2 = (TextView) findViewById(R.id.txtBotSays_1);
        txtBotSays_3 = (TextView) findViewById(R.id.txtBotSays_1);
        txtPlayerSays = (TextView) findViewById(R.id.txtPlayerSays);
        txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);
        txtMessage = (TextView) findViewById(R.id.txtMessage);

        btnBelieve = (Button) findViewById(R.id.btnBelieve);
        btnDontBelieve = (Button) findViewById(R.id.btnDontBelieve);
        btnSay = (Button) findViewById(R.id.btnSay);

        imgDie_1 = (ImageView) findViewById(R.id.imgDie_1);
        imgDie_2 = (ImageView) findViewById(R.id.imgDie_2);
        imgDie_3 = (ImageView) findViewById(R.id.imgDie_3);
        imgDie_4 = (ImageView) findViewById(R.id.imgDie_4);
        imgDie_5 = (ImageView) findViewById(R.id.imgDie_5);

        imgBubble1 = (ImageView) findViewById(R.id.imgBubble1);
        imgBubble2 = (ImageView) findViewById(R.id.imgBubble2);
        imgBubble3 = (ImageView) findViewById(R.id.imgBubble3);
        imgBubble4 = (ImageView) findViewById(R.id.imgBubble4);

        spinDie_1 = (Spinner) findViewById(R.id.spinDie_1);
        spinDie_1 = (Spinner) findViewById(R.id.spinDie_2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.numbers_array_20, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDie_1.setAdapter(adapter2);
        spinDie_2.setAdapter(adapter);

        btnBelieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

            }});
        btnDontBelieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

            }});
        btnSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int sayQuantity = Integer.parseInt((String)spinDie_1.getSelectedItem());
                int sayNumber = Integer.parseInt((String)spinDie_2.getSelectedItem());
                KamuRollable say = new KamuRollable(sayNumber, sayQuantity);
                //TODO ellenőrzés, hogy van-e annyi
                if (player.getData().isGuggolas())
                {
                    player.getOutData().setPreviousSaid(say);
                    EndTurn();
                }
                else if ( player.getData().getPreviousSaid().getQuantity()>= sayQuantity)
                {
                    Toast.makeText(getBaseContext(), "Nagyobbat kell mondanod!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    player.getOutData().setPreviousSaid(say);
                    EndTurn();
                }
                player.getOutData().setGuggolas(false);
                player.getOutData().setPreviousPlayer(player);
            }});
    }

    private void InitializeGameController()
    {
        ArrayList<KamuPlayer> players = new  ArrayList<KamuPlayer>();
        players.add(new KamuPlayerAndroid("Berci", null));
        players.add(new KamuPlayer("Bot Ond", new KamuBotUI()));
        players.add(new KamuPlayer("Bot Kond", new KamuBotUI()));
        players.add(new KamuPlayer("Bot Tas", new KamuBotUI()));

        gc = new KamuGameController(players);
        player = gc.getPlayerAndroid();
        txtBotName_1.setText(players.get(1).getName());
        txtBotName_2.setText(players.get(2).getName());
        txtBotName_3.setText(players.get(3).getName());
        txtPlayerName.setText(player.getName());

        txtBotDice_1.setText(players.get(1).getDiceSize());
        txtBotDice_1.setText(players.get(2).getDiceSize());
        txtBotDice_1.setText(players.get(3).getDiceSize());

        txtPlayerSays.setText("");
        txtBotSays_1.setText("");
        txtBotSays_2.setText("");
        txtBotSays_3.setText("");

        imgBubble1.setImageAlpha(0);
        imgBubble2.setImageAlpha(0);
        imgBubble3.setImageAlpha(0);
        imgBubble4.setImageAlpha(0);

        KamuTurnData data = new KamuTurnData();
        data.setGuggolas(true);
        KamuRoll roll = gc.RollAll();
        data.setRoll(roll);
        player.setData(data);

        new SleepThenWrite2().execute(new KamuUpdate(false, KamuUIDataType.STATE_SAY,""));
    }
    private void EndTurn()
    {
        player.getOutData().setPreviousPlayer(player);
        player.setData(gc.PlayARound(player.getOutData()));
        new SleepThenWrite2().execute(new KamuUpdate(false, KamuUIDataType.STATE_WAIT, ""));
    }
    private class SleepThenWrite2 extends AsyncTask<KamuUpdate, Void, KamuUpdate> {
        private TextView txt;
        private ImageView img;
        @Override
        protected KamuUpdate doInBackground(KamuUpdate... params) {
            if (params[0].isWait()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return params[0];
        }
        protected void onPostExecute(KamuUpdate update) {
            if (update.getType().equals(KamuUIDataType.MESSAGE))
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
            else if (update.getType().equals(KamuUIDataType.BOT1SAYS))
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
            else if (update.getType().equals(KamuUIDataType.BOT2SAYS))
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
            else if (update.getType().equals(KamuUIDataType.BOT3SAYS))
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
            else if (update.getType().equals(KamuUIDataType.PLAYERSAYS))
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
            else if (update.getType().equals(KamuUIDataType.BOT1BELIEVES))
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
            else if (update.getType().equals(KamuUIDataType.BOT2BELIEVES))
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
            else if (update.getType().equals(KamuUIDataType.BOT3BELIEVES))
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
            else if (update.getType().equals(KamuUIDataType.PLAYERBELIEVES))
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
            else if (update.getType().equals(KamuUIDataType.BOT1DICE))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_1);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(KamuUIDataType.BOT2DICE))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_2);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(KamuUIDataType.BOT3DICE))
            {
                txt = (TextView) findViewById(R.id.txtBotPoints_3);
                txt.setText(update.getValue() + " pont");
            }
            else if (update.getType().equals(KamuUIDataType.PLAYERDICE))
            {

                txt = (TextView) findViewById(R.id.txtPlayerPoints);
                txt.setText(update.getValue() + " pont");
            }

            else if (update.getType().equals(KamuUIDataType.ROUND))
            {
                txt = (TextView) findViewById(R.id.txtRound);
                txt.setText(update.getValue() + ". kör");
            }

            else if (update.getType().equals(KamuUIDataType.STATE_BELIEVE))
            {
                btnBelieve.setEnabled(true);
                btnDontBelieve.setEnabled(true);
                btnSay.setEnabled(false);
                spinDie_1.setEnabled(false);
                spinDie_2.setEnabled(false);
            }
            else if (update.getType().equals(KamuUIDataType.STATE_SAY))
            {
                btnBelieve.setEnabled(false);
                btnDontBelieve.setEnabled(false);
                btnSay.setEnabled(true);
                spinDie_1.setEnabled(true);
                spinDie_2.setEnabled(true);
            }
            else if (update.getType().equals(KamuUIDataType.STATE_WAIT))
            {
                btnBelieve.setEnabled(false);
                btnDontBelieve.setEnabled(false);
                btnSay.setEnabled(false);
                spinDie_1.setEnabled(false);
                spinDie_2.setEnabled(false);
            }
        }

    }
}

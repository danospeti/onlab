package hu.danos.dicegames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hu.danos.dicegames.Jutka.JutkaGameController;
import hu.danos.dicegames.Jutka.JutkaPlayer;

public class AddPlayerActivity extends AppCompatActivity {

    private EditText playerNameTxt;
    private Button addBtn;
    private Button doneBtn;
    private LinearLayout listOfRows;
    private LayoutInflater inflater;
    ArrayList<String> playerNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        playerNameTxt = (EditText) findViewById(R.id.playerName);
        addBtn = (Button) findViewById(R.id.btnAdd);
        doneBtn = (Button) findViewById(R.id.btnDone);
        listOfRows = (LinearLayout) findViewById(R.id.list_of_rows);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        playerNames = new ArrayList<String>();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerNameTxt.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.setName, Toast.LENGTH_LONG).show();

                }
                else
                {
                    playerNames.add(playerNameTxt.getText().toString());

                    View rowItem = inflater.inflate(R.layout.player_row, null);
                    TextView rowItemPlayerName = (TextView) rowItem.findViewById(R.id.row_player_name);
                    rowItemPlayerName.setText(playerNameTxt.getText().toString());
                    listOfRows.addView(rowItem);
                    playerNameTxt.setText("");
                    doneBtn.setEnabled(true);
                }
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playerNames.size() == 0)
                {
                    Toast.makeText(getBaseContext(), R.string.addAtleastOnePlayer, Toast.LENGTH_LONG).show();
                    return;
                }
                JutkaGameController controller = JutkaGameController.getInstance();
                JutkaGameController.getInstance().resetGame();
                for (int i = 0; i < playerNames.size(); i++)
                    controller.addPlayer(new JutkaPlayer(playerNames.get(i)));

                Intent i = new Intent(AddPlayerActivity.this, JutkaActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
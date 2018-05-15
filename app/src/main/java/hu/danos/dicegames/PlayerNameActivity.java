package hu.danos.dicegames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlayerNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);
        final Button start = (Button)findViewById(R.id.btnGo);
        final EditText name = (EditText) findViewById(R.id.eTxtName);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String type = getIntent().getStringExtra("GAME_TYPE");
                Intent intent;
                if (type.equals("BAZS"))
                {
                    intent = new Intent(getBaseContext(), BazsActivity.class);
                }
                else
                {
                    intent = new Intent(getBaseContext(), KamuActivity.class);
                }
                intent.putExtra("PLAYER_NAME", name.getText().toString());
                startActivity(intent);
                finish();
            }});
    }
}

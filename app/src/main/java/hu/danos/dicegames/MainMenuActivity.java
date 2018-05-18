package hu.danos.dicegames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final Button bazsButton = (Button)findViewById(R.id.btnBazs);
        final Button kamuButton = (Button)findViewById(R.id.btnKamu);
        final Button jutkaButton = (Button)findViewById(R.id.btnJutka);
        final Button loadButton = (Button)findViewById(R.id.btnLoad);

        bazsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent;
                intent = new Intent(getBaseContext(), PlayerNameActivity.class);
                intent.putExtra("GAME_TYPE", "BAZS");
                startActivity(intent);
                finish();
            }});
        kamuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent;
                intent = new Intent(getBaseContext(), PlayerNameActivity.class);
                intent.putExtra("GAME_TYPE", "KAMU");
                startActivity(intent);
                finish();
            }});
        jutkaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent;
                intent = new Intent(getBaseContext(), AddPlayerActivity.class);
                startActivity(intent);
                finish();
            }});
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
            }});
    }

}

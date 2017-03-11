package np.com.amrittwanabasu.orchestrateai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import np.com.amrittwanabasu.orchestrateai.lyrics.predictor.OrchestrateAPIService;
import np.com.amrittwanabasu.orchestrateai.network.OrchestrateServiceResolver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText lyricsEditText;
    private TextView predictionText;
    private ProgressBar predictionProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button predictButton = (Button) findViewById(R.id.predictButton);
        lyricsEditText = (EditText) findViewById(R.id.lyricsEditTxt);
        predictionText = (TextView) findViewById(R.id.predictionText);
        predictionProgress = (ProgressBar) findViewById(R.id.predictionProgress);

        this.predictionProgress.setVisibility(View.INVISIBLE);

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.predictLyrics();
            }
        });
    }

    /**
     * Predicts Lyrics.
     */
    private void predictLyrics() {
        String lyrics = lyricsEditText.getText().toString();

        if(lyrics.contentEquals("")) {
            Toast.makeText(this, "Drop a lyrics to predict it's mood", Toast.LENGTH_LONG).show();
            return;
        }

        this.sendPredictionRequest(lyrics);
    }

    /**
     * Sends Prediction Request to server.
     *
     * @param lyrics    Lyrics to be predicted.
     */
    private void sendPredictionRequest(String lyrics) {
        this.predictionProgress.setVisibility(View.VISIBLE);

        this.predictionText.setText("");

        OrchestrateServiceResolver.resolveService(OrchestrateAPIService.class)
        .predictLyrics(lyrics).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                MainActivity.this.predictionProgress.setVisibility(View.INVISIBLE);

                if( ! response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Something went wrong. Try again later", Toast.LENGTH_LONG).show();
                } else {
                    MainActivity.this.handlePrediction(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MainActivity.this.predictionProgress.setVisibility(View.INVISIBLE);

                Toast.makeText(MainActivity.this, "Are you sure you're connected to internet?", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Handles Prediction.
     *
     * @param prediction    Prediction of lyrics.
     */
    private void handlePrediction(String prediction) {
        this.predictionText.setText("We predicted the lyrics to be '" + prediction + "'");
    }
}

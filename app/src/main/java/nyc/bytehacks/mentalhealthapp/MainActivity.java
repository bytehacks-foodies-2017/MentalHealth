
 /*Button neutralFace = (Button) findViewById(R.id.radioButton);
        Intent intentNeutral = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intentNeutral);

        Button happyFace = (Button) findViewById(R.id.radioButton2);
                Intent intentHappy = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentHappy);

        Button angryFace = (Button) findViewById(R.id.radioButton3);
            */
/* NEW CODE JUST PASTED */

package nyc.bytehacks.mentalhealthapp;

        import android.os.Bundle;
        import android.os.StrictMode;
        import android.support.v7.app.AppCompatActivity;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.EditorInfo;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
        import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
        import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
        import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
        import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.URLEncoder;

        import static org.apache.http.protocol.HTTP.USER_AGENT;


public class MainActivity extends AppCompatActivity {

    Button button;
    EditText inputText;
    TextView content;
    ProgressBar angerp;
    ProgressBar disgustp;
    ProgressBar fearp;
    ProgressBar joyp;
    ProgressBar sadnessp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.submitButton);
        inputText = (EditText) findViewById(R.id.editText);
        content = (TextView) findViewById(R.id.entry);
        angerp = (ProgressBar) findViewById(R.id.anger);
        disgustp = (ProgressBar) findViewById(R.id.disgust);
        fearp = (ProgressBar) findViewById(R.id.fear);
        joyp = (ProgressBar) findViewById(R.id.joy);
        sadnessp = (ProgressBar) findViewById(R.id.sadness);


        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // Perform action on click
//                if (TextUtils.isEmpty(inputText.getText().toString())) {
//                    String test = "Hello! My name is Alice";
//                    double[] emo = makeGetRequest(test);
//                }

                double[] emo = makeGetRequest(inputText.getText().toString());

                String display = "Anger: "+emo[0];
                display += "\n Disgust: "+emo[1];
                display += "\n Fear: "+emo[2];
                display += "\n Joy: "+emo[3];
                display += "\n Sadness: "+emo[4];

                System.out.println(display);

                content.setText(inputText.getText().toString());
                angerp.setProgress((int)(emo[0]*100));
                disgustp.setProgress((int)(emo[1]*100));
                fearp.setProgress((int)(emo[2]*100));
                joyp.setProgress((int)(emo[3]*100));
                sadnessp.setProgress((int)(emo[4]*100));
            }
        });

    }


        public double[] makeGetRequest (String input){

            // PREV CODE
            try {

                // "username": "",
                //       "password": "htj6fXEtIaX1"

                ToneAnalyzer service = new ToneAnalyzer("2016-05-19");
                service.setUsernameAndPassword("6759832b-73de-40ed-8762-20d0f96fd78f", "htj6fXEtIaX1");
                ToneOptions options = new ToneOptions.Builder()
                        .addTone(Tone.EMOTION).build();
                ToneAnalysis tone =
                        service.getTone(input, options).execute();

                StringBuilder toneString = new StringBuilder();

                for (ToneScore toneScore : tone.getDocumentTone().getTones().get(0).getTones()) {
                    String t = toneScore.getName() + ": " + toneScore.getScore() + "\n";
                    toneString.append(t);
                }


//            HttpClient client = new DefaultHttpClient();
                //  HttpGet request = new HttpGet(url);

//          add request header

//            HttpResponse response;
                //request.addHeader("User-Agent", USER_AGENT);
//            //response = client.execute();
//            System.out.println("Response Code : "
//                    + response.getStatusLine().getStatusCode());
//
//            BufferedReader rd = new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent()));
//
//            StringBuffer result = new StringBuffer();
//            String line = "";
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }


                JSONObject json = new JSONObject(tone.toString());
                JSONArray categories = json.getJSONObject("document_tone").getJSONArray("tone_categories");
                JSONArray tones = categories.getJSONObject(0).getJSONArray("tones");
                System.out.println(tones);

                //  Log.e("Error","This went wrong");

                double[] test = {};

                return test;
//            double anger = Double.valueOf(tones.getJSONObject(0).getString("score"));
//            double disgust = Double.valueOf(tones.getJSONObject(1).getString("score"));
//            double fear = Double.valueOf(tones.getJSONObject(2).getString("score"));
//            double joy = Double.valueOf(tones.getJSONObject(3).getString("score"));
//            double sadness = Double.valueOf(tones.getJSONObject(4).getString("score"));
//
//
//            double[] results = {anger, disgust, fear, joy, sadness};
//
//            return results;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;


        }


    }




package commons.stockmarket.com.stockpredictor;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

import static commons.stockmarket.com.stockpredictor.StockPredictor.STOCK_ACCURACY;


public class HTTPClient {

    private final AsyncHttpClient client;
    private final RequestParams urlParams;
    private Context context;

    public HTTPClient(Context context) {
        this.context = context;
        final String username = "add_your_username";
        final String password = "add_your_password";
        client = new AsyncHttpClient();
        client.setBasicAuth(username, password);
        urlParams = new RequestParams();
        urlParams.put("path", "/StockPredictor.txt");

    }

    public void calculateStockDirection() {

        final String databricksServerURL = "https://dbc-828bcca4-35da.cloud.databricks.com";
        final String databricksAPI = "/api/2.0/dbfs/read";
        client.get(databricksServerURL + databricksAPI, urlParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {
                    final String stockJSON = new String(response, "UTF-8");
                    final String stockEncoded = new JSONObject(stockJSON).getString("data");
                    byte[] stockDecoded = Base64.decode(stockEncoded, Base64.DEFAULT);
                    final String stockPredictionAccuracy = String.valueOf(Double.valueOf(new String(stockDecoded, "UTF-8")) * 100).split("\\.")[0];

                    final Intent intent = new Intent(context, DisplayStockDirection.class);
                    intent.putExtra(STOCK_ACCURACY, stockPredictionAccuracy);
                    context.startActivity(intent);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("http", "Unable to establish connection with Databricks");
                Toast.makeText(context, "Unable to establish connection with Databricks", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

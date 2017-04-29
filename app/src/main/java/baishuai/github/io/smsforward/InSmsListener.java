package baishuai.github.io.smsforward;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.io.IOException;

import baishuai.github.io.smsforward.forward.FeigeApi;
import baishuai.github.io.smsforward.forward.Result;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by bai on 17-4-28.
 */

public class InSmsListener extends BroadcastReceiver {

    FeigeApi service;

    public InSmsListener() {
        super();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        HttpUrl url = request.url().newBuilder().addQueryParameter("uid", "159")
                                .addQueryParameter("secret", BuildConfig.FEIGE_SECRET)
                                .build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                }
        ).addInterceptor(logging).build();


        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl("https://u.ifeige.cn")
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(FeigeApi.class);

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                Log.d(this.getClass().getCanonicalName(), messageBody);
                Log.d(this.getClass().getCanonicalName(), smsMessage.getOriginatingAddress());
                Call<Result> call = service.forward("status", messageBody.substring(0, Math.min(5, messageBody.length())), smsMessage.getOriginatingAddress(), messageBody);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                        Log.d(this.getClass().getCanonicalName(), response.toString());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.d(this.getClass().getCanonicalName(), t.toString());
                    }
                });
            }
        }


    }
}

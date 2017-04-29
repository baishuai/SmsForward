package baishuai.github.io.smsforward;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import java.util.Arrays;

import baishuai.github.io.smsforward.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    private static final int REQUEST_SMS_RECEIVE = 10010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        boolean isGranted = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
        mainBinding.permissionSwitch.setChecked(isGranted);

        if (isGranted) {
            mainBinding.permissionSwitch.setText(getString(R.string.permission_granted));
            mainBinding.permissionSwitch.setEnabled(false);
        } else {
            mainBinding.permissionSwitch.setText(getString(R.string.request_sms_permission));
            mainBinding.permissionSwitch.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_RECEIVE);
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SMS_RECEIVE: {
                int idx = Arrays.asList(permissions).indexOf(Manifest.permission.RECEIVE_SMS);
                mainBinding.permissionSwitch.setChecked(grantResults[idx] == PackageManager.PERMISSION_GRANTED);
                if (mainBinding.permissionSwitch.isChecked()) {
                    mainBinding.permissionSwitch.setEnabled(false);
                    mainBinding.permissionSwitch.setOnCheckedChangeListener(null);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

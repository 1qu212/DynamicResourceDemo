package com.example.xydzjnq.hostapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private TextView textV;
    private ImageView imgV;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textV = (TextView) findViewById(R.id.text);
        imgV = (ImageView) findViewById(R.id.imageview);
        layout = (LinearLayout) findViewById(R.id.layout);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PluginInfo pluginInfo = plugins.get("plugin1.apk");

                loadResources(pluginInfo.getDexPath());

                doSomething(pluginInfo.getClassLoader());
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginInfo pluginInfo = plugins.get("plugin2.apk");

                loadResources(pluginInfo.getDexPath());

                doSomething(pluginInfo.getClassLoader());
            }
        });
    }

    private void doSomething(ClassLoader cl) {
        try {
            Class stringClass = cl.loadClass("com.example.xydzjnq.plugin.R$string");
            int resId1 = (int) RefInvoke.getStaticFieldObject(stringClass, "hello_message");
            textV.setText(getResources().getString(resId1));

            Class drawableClass = cl.loadClass("com.example.xydzjnq.plugin.R$drawable");
            int resId2 = (int) RefInvoke.getStaticFieldObject(drawableClass, "robert");
            imgV.setBackgroundDrawable(getResources().getDrawable(resId2));

            Class layoutClazz = cl.loadClass("com.example.xydzjnq.plugin.R$layout");
            int resId3 = (int) RefInvoke.getStaticFieldObject(layoutClazz, "main_activity");
            View view = (View) LayoutInflater.from(this).inflate(resId3, null);
            layout.removeAllViews();
            layout.addView(view);

        } catch (Exception e) {
            Log.e("DEMO", "msg:" + e.getMessage());
        }
    }
}

package cn.demonk.resolveannotation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import cn.demonk.processor.CustomAnnotation;
import cn.demonk.resolveannotation.fruit.Fruit;
import cn.demonk.resolveannotation.fruit.Watermelon;

public class MainActivity extends ActionBarActivity {

    @CustomAnnotation(type = Watermelon.class)
    Fruit fruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}

package cn.demonk.resolveannotation.fruit;

import android.util.Log;

/**
 * Created by ligs on 8/21/16.
 */
public class Watermelon implements Fruit {

    @Override
    public void eat() {
        Log.e("demonk", "cut");
    }
}

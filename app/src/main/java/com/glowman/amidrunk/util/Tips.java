package com.glowman.amidrunk.util;

/**
 * Created by Максим on 09.10.2014.
 */
public class Tips {

    public static String[] ALCO_TIPS;
    public static String[] NONE_ALCO_TIPS;

    private static Tips _inst;

    public static Tips i() {
        if (_inst != null) {
            return _inst;
        }
        _inst = new Tips();
        return _inst;
    }

    public Tips() {
        ALCO_TIPS = new String[2];
        ALCO_TIPS[0] = "Call taxi! Go to sleep.";
        ALCO_TIPS[1] = "Call your ex-girlfriend! Say what you feeling ;)";
        NONE_ALCO_TIPS = new String[2];
        NONE_ALCO_TIPS[0] = "Not enough alco! Drink better :)";
        NONE_ALCO_TIPS[1] = "Looks like you're not drunk!/nCall your best friends and go to sho for alco!";
    }

}

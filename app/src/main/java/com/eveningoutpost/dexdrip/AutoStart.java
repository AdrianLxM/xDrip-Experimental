package com.eveningoutpost.dexdrip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.eveningoutpost.dexdrip.UtilityModels.CollectionServiceStarter;
import com.eveningoutpost.dexdrip.utils.DatabaseUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by stephenblack on 11/3/14.
 */
public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("DexDrip", "Service auto starter, starting!");

        //import DB if existent
        try {
            String importDB = Environment.getExternalStorageDirectory().getCanonicalPath() + "/DCIM/import.sqlite";
            File replacement = new File(importDB);
            if (replacement.exists()) {
                DatabaseUtil.loadSql(context, importDB);
                //rename DB to not import it again
                replacement.renameTo(new File(replacement.getPath() + System.currentTimeMillis()));
            }
        } catch (IOException e) {
            Log.e("DexDrip", "DB-import failed", e);
        }


        CollectionServiceStarter.newStart(context);
    }
}

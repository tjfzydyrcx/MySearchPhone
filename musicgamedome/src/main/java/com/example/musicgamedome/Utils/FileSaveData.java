package com.example.musicgamedome.Utils;

import android.content.Context;

import com.example.musicgamedome.data.Const;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017-08-22 0022.
 */
public class FileSaveData {

    public static void SaveData(Context context, int stageIndex, int coins) {

        FileOutputStream fis = null;
        DataOutputStream dos = null;
        try {
            fis = context.openFileOutput(Const.FiILE_NAME_SAVE_DATA, Context.MODE_PRIVATE);
            dos = new DataOutputStream(fis);
            dos.writeInt(stageIndex);
            dos.writeInt(coins);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    dos.close();
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取数据
     *
     * @param context
     * @return
     */
    public static int[] loadData(Context context) {
        FileInputStream fis = null;
        int[] datas = {-1, Const.TOTAL_COINS};
        DataInputStream dis = null;
        try {
            fis = context.openFileInput(Const.FiILE_NAME_SAVE_DATA);
            dis = new DataInputStream(fis);
            datas[Const.INDEX_LOAD_DATA_STAGE] = dis.readInt();
            datas[Const.INDEX_LOAD_DATA_COINS] = dis.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    dis.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }

}

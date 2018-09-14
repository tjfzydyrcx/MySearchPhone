package com.example.administrator.mysearchphone;

/**
 * Created by Administrator on 2017-09-19 0019.
 */
public class maopao {
    public static void main(String[] args) {
        int srore[] = {42, 32, 56, 12, 78, 9, 88};
        for (int i = 0; i < srore.length-1; i++) {
            for (int j = i+1 ; j < srore.length; j++) {
                if (srore[i] > srore[j]) {
                    int tem = srore[j];
                    srore[j] = srore[i];
                    srore[i] = tem;
                }
            }
        }
        for (int a = 0; a < srore.length; a++) {
            System.out.println(srore[a]);
        }
    }
}

package com.example.nhom10_laptrinhgame2048;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DataGame {
    private int size = 4;
    private boolean checkSum = true, checkSwap = false;
    private static DataGame dataGame;
    private ArrayList<Integer> arrSo = new ArrayList<>();
    private int[][] matrix = new int[size][size];
    private int[] mangMau;
    private Random r = new Random();
    private int point;
    private int max;

    static  {
        dataGame = new DataGame();
    }

    public static DataGame getDataGame() {
        return dataGame;
    }

    public void init(Context context) {
        arrSo.clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
                arrSo.add(0);
            }
        }
        TypedArray ta = context.getResources().obtainTypedArray(R.array.mau_nen_cua_so);
        mangMau = new int[ta.length()];
        for (int i=0; i < ta.length(); i++) {
            mangMau[i]=ta.getColor(i, 0);
        }
        ta.recycle();

        taoSo();
        chuyenDoi();
        point = 0;
        max = 0;
    }

    public ArrayList<Integer> getArrSo() {
        return arrSo;
    }

    public int colorr(int so) {
        if (so == 0)
            return Color.WHITE;
        else {
            int a = (int)(Math.log(so) / Math.log(2));
            return mangMau[a-1];
        }
    }

    public int getPoint() {
        return point;
    }

    public int getMax() {
        return Collections.max(arrSo);
    }

    public void taoSo() {
        int soO = 0;
        for (int i = 0; i <size*size; i ++) {
            if (arrSo.get(i) == 0) {
                soO++;
            }
        }
        int soOTao;
        if (soO > 1) {
            soOTao = r.nextInt(2) + 1;
        }
        else {
            if (soO == 1) {
                soOTao = 1;
            }
            else {
                soOTao = 0;
            }
        }
        while (soOTao != 0) {
            int i = r.nextInt(size), j = r.nextInt(size);
            if (matrix[i][j] == 0) {
                matrix[i][j] = 2;
                soOTao--;
            }
        }
    }

    public void chuyenDoi() {
        arrSo.clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arrSo.add(matrix[i][j]);
            }
        }
    }

    public void vuotTrai() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int so = matrix[i][j];
                if (so == 0) {
                    continue;
                }
                else {
                    for (int k = j + 1; k < size; k++) {
                        int soX = matrix[i][k];
                        if (soX == 0) {
                            continue;
                        }
                        else {
                            if (soX == so) {
                                matrix[i][j] = so*2;
                                matrix[i][k] = 0;
                                point += so*2;
                                checkSum = true;
                                break;
                            }
                            else
                                break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int so = matrix[i][j];
                if (so == 0) {
                    for (int k = j + 1; k < size; k++) {
                        int so1 = matrix[i][k];
                        if (so1 == 0) {
                            continue;
                        }
                        else {
                            matrix[i][j] = matrix[i][k];
                            matrix[i][k] = 0;
                            checkSwap = true;
                            break;
                        }
                    }
                }
            }
        }
        if (checkSum || checkSwap) {
            taoSo();
            checkSum = false;
            checkSwap = false;
        }
        chuyenDoi();
    }

    public void vuotPhai() {
        for (int i = size - 1; i >= 0; i--) {
            for (int j = size - 1; j >= 0; j--) {
                int so = matrix[i][j];
                if (so == 0) {
                    continue;
                }
                else {
                    for (int k = j - 1; k >= 0; k--) {
                        int soX = matrix[i][k];
                        if (soX == 0) {
                            continue;
                        }
                        else {
                            if (soX == so) {
                                matrix[i][j] = so*2;
                                matrix[i][k] = 0;
                                point += so*2;
                                checkSum = true;
                                break;
                            }
                            else
                                break;
                        }
                    }
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            for (int j = size - 1; j >= 0; j--) {
                int so = matrix[i][j];
                if (so == 0) {
                    for (int k = j - 1; k >= 0; k--) {
                        int so1 = matrix[i][k];
                        if (so1 == 0) {
                            continue;
                        }
                        else {
                            matrix[i][j] = matrix[i][k];
                            matrix[i][k] = 0;
                            checkSwap = true;
                            break;
                        }
                    }
                }
            }
        }
        if (checkSum || checkSwap) {
            taoSo();
            checkSum = false;
            checkSwap = false;
        }
        chuyenDoi();
    }

    public void vuotLen() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int so = matrix[j][i];
                if (so == 0) {
                    continue;
                }
                else {
                    for (int k = j + 1; k < size; k++) {
                        int soX = matrix[k][i];
                        if (soX == 0) {
                            continue;
                        }
                        else {
                            if (soX == so) {
                                matrix[j][i] = so*2;
                                matrix[k][i] = 0;
                                point += so*2;
                                checkSum = true;
                                break;
                            }
                            else
                                break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int so = matrix[j][i];
                if (so == 0) {
                    for (int k = j + 1; k < size; k++) {
                        int so1 = matrix[k][i];
                        if (so1 == 0) {
                            continue;
                        }
                        else {
                            matrix[j][i] = matrix[k][i];
                            matrix[k][i] = 0;
                            checkSwap = true;
                            break;
                        }
                    }
                }
            }
        }
        if (checkSum || checkSwap) {
            taoSo();
            checkSum = false;
            checkSwap = false;
        }
        chuyenDoi();
    }

    public void vuotXuong() {
        for (int i = size - 1; i >= 0; i--) {
            for (int j = size - 1; j >= 0; j--) {
                int so = matrix[j][i];
                if (so == 0) {
                    continue;
                }
                else {
                    for (int k = j - 1; k >= 0; k--) {
                        int soX = matrix[k][i];
                        if (soX == 0) {
                            continue;
                        }
                        else {
                            if (soX == so) {
                                matrix[j][i] = so*2;
                                matrix[k][i] = 0;
                                point += so*2;
                                checkSum = true;
                                break;
                            }
                            else
                                break;
                        }
                    }
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            for (int j = size -1; j >= 0; j--) {
                int so = matrix[j][i];
                if (so == 0) {
                    for (int k = j - 1; k >= 0; k--) {
                        int so1 = matrix[k][i];
                        if (so1 == 0) {
                            continue;
                        }
                        else {
                            matrix[j][i] = matrix[k][i];
                            matrix[k][i] = 0;
                            checkSwap = true;
                            break;
                        }
                    }
                }
            }
        }
        if (checkSum || checkSwap) {
            taoSo();
            checkSum = false;
            checkSwap = false;
        }
        chuyenDoi();
    }

    public boolean checkGameOver() {
        if(arrSo.contains(0)){
            return true;
        }else{
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if(0<=i && i < matrix.length-1){
                        if(matrix[i][j]==matrix[i+1][j]){
                            return true;
                        }
                    }
                    if(0<=j&&j<matrix.length-1){
                        if(matrix[i][j]==matrix[i][j+1]){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

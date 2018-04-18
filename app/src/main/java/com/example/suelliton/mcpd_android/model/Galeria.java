package com.example.suelliton.mcpd_android.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.suelliton.mcpd_android.R;

/**
 * Created by Aluno on 24/11/2017.
 */

public class Galeria {
    String name;
    Bitmap img_01,img_02,img_03,img_04,img_05;
    Bitmap cacau_01,cacau_02,cacau_03,cacau_04,cacau_05;
    Bitmap banana_01,banana_02,banana_03,banana_04,banana_05;
    Bitmap mamao_01,mamao_02,mamao_03,mamao_04,mamao_05;


    public Galeria(String name, Context context){
        this.name = name;
        img_01 = BitmapFactory.decodeResource(context.getResources(), R.drawable.a);
        img_02 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b);
        img_03 = BitmapFactory.decodeResource(context.getResources(), R.drawable.c);
        img_04 = BitmapFactory.decodeResource(context.getResources(), R.drawable.d);
        img_05 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e);
        cacau_01 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cacau_antracanose);
        cacau_02 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cacau_podridao_parda);
        cacau_03 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cacau_praga);
        cacau_04 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cacau_vassoura_bruxa);
        cacau_05 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e);
        banana_01 = BitmapFactory.decodeResource(context.getResources(), R.drawable.banana_moleque_bananeira);
        banana_02 = BitmapFactory.decodeResource(context.getResources(), R.drawable.banana_tripe_erupcao);
        banana_03 = BitmapFactory.decodeResource(context.getResources(), R.drawable.banana_tripes_ferrugem);
        banana_04 = BitmapFactory.decodeResource(context.getResources(), R.drawable.banana_broca_pseudocaule);
        banana_05 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e);
        mamao_01 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mamao_acaro_rajado);
        mamao_02 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mamao_cigarrinha_verde);
        mamao_03 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mamao_acaro_branco);
        mamao_04 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mamao_lagarta_rosca);
        mamao_05 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mamao_mosca_frutas);
    }

    public Bitmap getImage(int img, String cultura, String doenca_praga) {
        if (cultura.equals("Cacau")) {
            switch (img) {
                case 1:
                    return cacau_01;
                case 2:
                    return cacau_02;
                case 3:
                    return cacau_03;
                case 4:
                    return cacau_04;
                case 5:
                    return cacau_05;
                default:
                    return cacau_01;
            }
        }else if (cultura.equals("Banana")) {
            switch (img) {
                case 1:
                    return banana_01;
                case 2:
                    return banana_02;
                case 3:
                    return banana_03;
                case 4:
                    return banana_04;
                case 5:
                    return banana_05;
                default:
                    return banana_01;
            }
        }else if (cultura.equals("Mamão")) {
            switch (img) {
                case 1:
                    return mamao_01;
                case 2:
                    return mamao_02;
                case 3:
                    return mamao_03;
                case 4:
                    return mamao_04;
                case 5:
                    return mamao_05;
                default:
                    return mamao_01;
            }
        };
        return null;
    }
}
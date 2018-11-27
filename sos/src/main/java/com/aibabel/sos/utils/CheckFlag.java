package com.aibabel.sos.utils;


import com.aibabel.sos.R;

/**
 * 作者：SunSH on 2018/6/27 15:26
 * 功能：
 * 版本：1.0
 */
public class CheckFlag {
    public static int getFlag(String flag) {
        int icon = R.mipmap.ic_launcher;
        String flagLower = flag.toLowerCase();
        switch (flagLower) {
            case "jpy":
                icon = R.mipmap.jpy;
                break;
            case "crc":
                icon = R.mipmap.crc;
                break;
            case "krw":
                icon = R.mipmap.krw;
                break;
            case "vnd":
                icon = R.mipmap.vnd;
                break;
            case "khr":
                icon = R.mipmap.khr;
                break;
            case "sgd":
                icon = R.mipmap.sgd;
                break;
            case "idr":
                icon = R.mipmap.idr;
                break;
            case "myr":
                icon = R.mipmap.myr;
                break;
            case "npr":
                icon = R.mipmap.npr;
                break;
            case "ita":
                icon = R.mipmap.ita;
                break;
            case "xpf":
                icon = R.mipmap.xpf;
                break;
            case "try":
                icon = R.mipmap.try1;
                break;
            case "ger":
                icon = R.mipmap.ger;
                break;
            case "rub":
                icon = R.mipmap.rub;
                break;
            case "chf":
                icon = R.mipmap.chf;
                break;
            case "spa":
                icon = R.mipmap.spa;
                break;
            case "bel":
                icon = R.mipmap.bel;
                break;
            case "gbp":
                icon = R.mipmap.gbp;
                break;
            case "gre":
                icon = R.mipmap.gre;
                break;
            case "isk":
                icon = R.mipmap.isk;
                break;
            case "hol":
                icon = R.mipmap.hol;
                break;
            case "fin":
                icon = R.mipmap.fin;
                break;
            case "nok":
                icon = R.mipmap.nok;
                break;
            case "swe":
                icon = R.mipmap.swe;
                break;
            case "por":
                icon = R.mipmap.por;
                break;
            case "aut":
                icon = R.mipmap.aut;
                break;
            case "czk":
                icon = R.mipmap.czk;
                break;
            case "dkk":
                icon = R.mipmap.dkk;
                break;
            case "pol":
                icon = R.mipmap.pol;
                break;
            case "hrk":
                icon = R.mipmap.hrk;
                break;
            case "huf":
                icon = R.mipmap.huf;
                break;
            case "sit":
                icon = R.mipmap.sit;
                break;
            case "usd":
                icon = R.mipmap.usd;
                break;
            case "cad":
                icon = R.mipmap.cad;
                break;
            case "cuc":
                icon = R.mipmap.cuc;
                break;
            case "aud":
                icon = R.mipmap.aud;
                break;
            case "nzd":
                icon = R.mipmap.nzd;
                break;
            case "brl":
                icon = R.mipmap.brl;
                break;
            case "arg":
                icon = R.mipmap.arg;
                break;
            case "per":
                icon = R.mipmap.per;
                break;
            case "mor":
                icon = R.mipmap.mor;
                break;
            case "kes":
                icon = R.mipmap.kes;
                break;
            case "zar":
                icon = R.mipmap.zar;
                break;
            case "mur":
                icon = R.mipmap.mur;
                break;
        }
        return icon;
    }
}

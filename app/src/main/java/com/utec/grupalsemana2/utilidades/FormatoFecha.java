package com.utec.grupalsemana2.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoFecha {

    public static Date StrToDate(String fecha, String hora) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechastr = fecha + " " + hora + ":00";
        System.out.println("FechaSTR= " + fechastr);
        Date date = null;
        try {
            date = format.parse(fechastr);
            System.out.println(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String DateToString(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaString = format.format(fecha);
        return fechaString;
    }

}

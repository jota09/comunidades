/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author ferney.medina
 */
public class Calendario {
    private static Calendar cal;
    
    
    public static int obtenerDiaMes(int anio,int mes,int dia)
    {
        cal=new GregorianCalendar(anio,mes,dia);
        int diasMes=cal.getActualMaximum(Calendar.DAY_OF_MONTH);        
        return diasMes;
    }
    
    public static int obtenerMesActualNumero()
    {        
        cal=new GregorianCalendar();
        return cal.get(Calendar.MONTH);
    }
    
    public static int obtenerAnioActualNumero()
    {        
        cal=new GregorianCalendar();
        return cal.get(Calendar.YEAR);
    }
    
    public static int obtenerDiaSemana(int anio,int mes,int dia)
    {
        cal=new GregorianCalendar(anio,mes,dia);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    public static int obtenerDiaActualNumero()
    {        
        cal=new GregorianCalendar();
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static String obtenerMesActualCadena()
    {
        cal=new GregorianCalendar();
        return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }
    
    
}

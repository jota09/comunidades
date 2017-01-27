/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;

/**
 *
 * @author ferney.medina
 */
public class Pruebas {
    public static void main(String[] args)
    {
        int numRegistrosTabla=75;
        int cant=8;
        double result=(double)numRegistrosTabla/(double)cant;
        int rangos=(int)result;
        int j=1;
        
        ArrayList<String> list=new ArrayList<String>();
        int i=1;
        
        String rang=i+","+cant;
        System.out.println("rangos:"+rang);
        list.add(rang);
        for(;i<=rangos;i++)
        {            
            j=i*cant;
            rang=j+","+cant;
            System.out.println("rangos:"+rang);
            list.add(rang);
        }
        System.out.println("Tamaño lista:"+list.size());
        System.out.println("ultimo dato lista:"+list.get(list.size()-1));        
        
        int res=numRegistrosTabla % cant;
        System.out.println("modulo:"+res);
        if(res!=0)
        {
            String nuevoValor=list.get(list.size()-1);
            list.remove(list.size()-1);
            list.add(rangos*cant+","+res);
        }
        System.out.println("ultimo dato lista:"+list.get(list.size()-1));
        
        /*int mod = numRegistrosTabla % cant;
        double p=rangos+(mod > 0?1:0);
        
        System.out.println(Math.max((cant * (2 - 1)) + 1, 1));
        
        System.out.println("primero:"+p);*/
        System.out.println(rangos);
        System.out.println((int)Math.ceil(result));
        System.out.println(result);
        
                    
    }
}

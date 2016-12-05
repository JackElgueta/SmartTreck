package com.example.jack.smarttreck;

import java.lang.reflect.Array;

/**
 * Created by Jack on 29-11-2016.
 */

public class Desafios {
     String nombre = null;
     String ruta = null;
     String Equipamiento = null;
     String Cantidad = null;
   //  Equipo equipamiento = null;

     public Desafios(String nombre, String ruta, String Equipamiento, String Cantidad) {
          super();
          this.nombre = nombre;
          this.ruta = ruta;
          this.Equipamiento = Equipamiento;
          this.Cantidad = Cantidad;
         // this.equipamiento = equipamiento;
     }
     public String getNombre() {
          return nombre;
     }
     public void setNombre(String nombre) {
          this.nombre = nombre;
     }
     public String getRuta() {
          return ruta;
     }
     public void setRuta(String ruta) {
          this.ruta = ruta;
     }
     public String getEquipamiento() {
          return Equipamiento;
     }
     public void setEquipamiento(String Equipamiento) {
          this.Equipamiento = Equipamiento;
     }
     public String getCantidad() {
          return Cantidad;
     }
     public void setCantidad(String Cantidad) {
          this.Cantidad = Cantidad;
     }
}

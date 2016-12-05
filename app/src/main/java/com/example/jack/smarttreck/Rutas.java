package com.example.jack.smarttreck;

/**
 * Created by Jack on 05-12-2016.
 */

public class Rutas {
    String nombreRuta = null;
    String lat = null;
    String lon = null;
    String tiempo = null;
    String distancia = null;


    public Rutas(String nombreRuta, String lat, String lon, String tiempo, String distancia) {
        super();
        this.nombreRuta = nombreRuta;
        this.lat = lat;
        this.lon = lon;
        this.tiempo = tiempo;
        this.distancia = distancia;

    }
    public String getNombreRuta() {
        return nombreRuta;
    }
    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLon() {
        return lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getTiempo() {
        return tiempo;
    }
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
    public String getDistancia() {
        return distancia;
    }
    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }
}

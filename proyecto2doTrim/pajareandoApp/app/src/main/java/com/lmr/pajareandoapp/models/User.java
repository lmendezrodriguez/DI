package com.lmr.pajareandoapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario en la aplicación Pajareando.
 * Contiene información personal como UID, nombre, correo electrónico, teléfono y dirección.
 */
public class User {
    private String uid;
    private String name;
    private String email;
    private String telephone;
    private String address;
    private List<Integer> observations;
    private List<String> favorites;


    public User() {
    }

    /**
     * Crea una nueva instancia de usuario con los detalles especificados.
     *
     * @param uid            el identificador único del usuario
     * @param name           el nombre del usuario
     * @param email          el correo electrónico del usuario
     * @param telephone      el número de teléfono del usuario
     * @param address        la dirección del usuario
     * @param observationIds la lista de observaciones del usuario
     * @param favorites      la lista de aves favoritas del usuario
     */
    public User(String uid, String name, String email, String telephone, String address, List<Integer> observationIds, List<String> favorites) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.observations = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public List<Integer> getObservations() {
        return observations;
    }

    public List<String> getFavorites() {
        return favorites;
    }
}

package com.lmr.pajareandoapp.models;

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

    /**
     * Crea una nueva instancia de usuario con los detalles especificados.
     *
     * @param uid       el identificador único del usuario
     * @param name      el nombre del usuario
     * @param email     el correo electrónico del usuario
     * @param telephone el número de teléfono del usuario
     * @param address   la dirección del usuario
     */
    public User(String uid, String name, String email, String telephone, String address) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
    }

    /**
     * Devuelve una representación en cadena del objeto Usuario.
     *
     * @return una cadena que contiene los detalles del usuario
     */
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

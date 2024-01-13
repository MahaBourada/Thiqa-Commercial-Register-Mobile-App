package com.example.miniprojet.model;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class Demandes implements Serializable {
    /* public Demandes(int id, String typeIdentite, String numIdentite, String nomEntreprise, String adresse, String activity,
                    String numFiscale, String ribBanque, String etat) {
        this.id = id;
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
    }

    public Demandes(String typeIdentite, String numIdentite, String nomEntreprise, String adresse, String activity, String numFiscale,
                    String ribBanque, String etat, String identitePath, String contratEndroitPath, String fiscalePath, String ribPath) {
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
        this.identitePath = identitePath;
        this.contratEndroitPath = contratEndroitPath;
        this.fiscalePath = fiscalePath;
        this.ribPath = ribPath;
    } */

    public Demandes() {}

    public Demandes(String userID, String typeIdentite, String numIdentite, String nomEntreprise, String adresse, String activity, String numFiscale,
                    String ribBanque, String identitePath, String contratEndroitPath, String fiscalePath, String ribPath, String etat) {
        this.userID = userID;
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.identitePath = identitePath;
        this.contratEndroitPath = contratEndroitPath;
        this.fiscalePath = fiscalePath;
        this.ribPath = ribPath;
        this.etat = etat;
    }

    /* public Demandes(String typeIdentite, String numIdentite, String nomEntreprise, String adresse, String activity, String numFiscale,
                    String ribBanque, String identitePath, String contratEndroitPath, String fiscalePath, String ribPath) {
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.identitePath = identitePath;
        this.contratEndroitPath = contratEndroitPath;
        this.fiscalePath = fiscalePath;
        this.ribPath = ribPath;
    } */

    /* public Demandes(int id, String typeIdentite, String numIdentite, String nomEntreprise, String adresse, String activity, String numFiscale, String ribBanque,
                    String etat, String identitePath, String contratEndroitPath, String fiscalePath, String ribPath) {
        this.id = id;
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
        this.identitePath = identitePath;
        this.contratEndroitPath = contratEndroitPath;
        this.fiscalePath = fiscalePath;
        this.ribPath = ribPath;
    } */

    @PropertyName("id") // Specify the name of the ID field in your database
    private String id;
    String userID;
    String typeIdentite;
    String numIdentite;
    String nomEntreprise;
    String adresse;
    String activity;
    String numFiscale;
    String ribBanque;
    String etat;
    String identitePath;
    String contratEndroitPath;
    String fiscalePath;
    String ribPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTypeIdentite() {
        return typeIdentite;
    }

    public void setTypeIdentite(String typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    public String getNumIdentite() {
        return numIdentite;
    }

    public void setNumIdentite(String numIdentite) {
        this.numIdentite = numIdentite;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getNumFiscale() {
        return numFiscale;
    }

    public void setNumFiscale(String numFiscale) {
        this.numFiscale = numFiscale;
    }

    public String getRibBanque() {
        return ribBanque;
    }

    public void setRibBanque(String ribBanque) {
        this.ribBanque = ribBanque;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getIdentitePath() {
        return identitePath;
    }

    public void setIdentitePath(String identitePath) {
        this.identitePath = identitePath;
    }

    public String getContratEndroitPath() {
        return contratEndroitPath;
    }

    public void setContratEndroitPath(String contratEndroitPath) {
        this.contratEndroitPath = contratEndroitPath;
    }

    public String getFiscalePath() {
        return fiscalePath;
    }

    public void setFiscalePath(String fiscalePath) {
        this.fiscalePath = fiscalePath;
    }

    public String getRibPath() {
        return ribPath;
    }

    public void setRibPath(String ribPath) {
        this.ribPath = ribPath;
    }
}

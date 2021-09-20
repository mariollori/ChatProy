package com.example.poryectochat.models;

public class Post {
    private String id;
    private String titulo;
    private String descripcion;
    private String img1;
    private String img2;
    private String iduser;
    private String category;


    public Post() {
    }

    public Post(String id, String titulo, String descripcion, String img1, String img2, String iduser, String category) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.img1 = img1;
        this.img2 = img2;
        this.iduser = iduser;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return titulo;
    }

    public void setName(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

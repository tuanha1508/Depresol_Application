package com.example.depresol;

public class FindCard {

    private int Id;
    private int imageFind;
    private String findTitle;
    private String quantityFind;
    private String urlFind;

    public FindCard(int id, int imageFind, String findTitle, String quantityFind) {
        Id = id;
        this.imageFind = imageFind;
        this.findTitle = findTitle;
        this.quantityFind = quantityFind;
    }

    public FindCard(int imageFind, String findTitle, String quantityFind) {
        this.imageFind = imageFind;
        this.findTitle = findTitle;
        this.quantityFind = quantityFind;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImageFind() {
        return imageFind;
    }

    public void setImageFind(int imageFind) {
        this.imageFind = imageFind;
    }

    public String getFindTitle() {
        return findTitle;
    }

    public void setFindTitle(String findTitle) {
        this.findTitle = findTitle;
    }

    public String getQuantityFind() {
        return quantityFind;
    }

    public void setQuantityFind(String quantityFind) {
        this.quantityFind = quantityFind;
    }

    public String getUrlFind() {
        return urlFind;
    }

    public void setUrlFind(String urlFind) {
        this.urlFind = urlFind;
    }

    @Override()
    public boolean equals(Object other) {
        if (other instanceof FindCard) {
            FindCard findCard = (FindCard) other;
            return findCard.getId()==(this.getId());
        }
        return false;
    }
}

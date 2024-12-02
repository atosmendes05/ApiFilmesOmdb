package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action","Ação"),
    DRAMA("Drama","Drama"),
    TERRO("Terror","Terro"),
    COMEDIA("Comedia","Comedia"),
    ROMANCE("Romance","Romance"),
    AVENTURA("Adventure","Aventura"),
    ANIMACAO("Animation","Animação");

    private String categoriaOmdb;
    private String categoriaEmBr;

    Categoria(String categoriaOmdb,String categoriaEmBr){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEmBr = categoriaEmBr;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromBr(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEmBr.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}

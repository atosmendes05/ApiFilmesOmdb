package br.com.alura.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer temporada;
    private  String titulo;
    private Integer numeroEpisodio;
    private double avaliacao;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie ;


    public Episodio(Integer numeroTemporada , DadosEpisodio dadosEpisodio){
        this.temporada = numeroTemporada;
        this.titulo  = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numero();
        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        }catch (NumberFormatException e){
            this.avaliacao = 0.0;
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }


    };

    public Episodio(){}


    public Integer getTemporada() {
        return temporada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento ;
    }
}

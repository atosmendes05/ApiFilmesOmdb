package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Serie,Long> {
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String atores, double avaliacao);
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findBytotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int maxTemporada, double avaliacaoMinima);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :maxTemporada AND s.avaliacao >= :avaliacao")
    List<Serie> buscaSerieMaxTemporada(int maxTemporada, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> buscaPorTreco(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND  YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodioPorDataDeLancamento(Serie serie,int anoLancamento);
}


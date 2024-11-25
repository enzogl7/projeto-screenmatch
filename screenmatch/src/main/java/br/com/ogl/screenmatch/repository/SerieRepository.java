package br.com.ogl.screenmatch.repository;

import br.com.ogl.screenmatch.model.Categoria;
import br.com.ogl.screenmatch.model.Episodio;
import br.com.ogl.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    Boolean existsByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findSerieByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalTemporadas, Double avaliacao);

    @Query("select s from Serie s where s.totalTemporadas <= :totalTemporadas and s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);

    @Query("select e from Serie s join s.episodios e where e.titulo ilike %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("select e from Serie s join s.episodios e where s = :serie order by e.avaliacao desc limit 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("select e from Serie s join s.episodios e where s = :serie and year(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);
}

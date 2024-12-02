package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SeriesRepository;
import br.com.alura.screenmatch.service.ConsumirApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("KEY");
    private ConsumirApi minhaApi = new ConsumirApi();
    private ConverteDados conversor = new ConverteDados();
    private Scanner ler  = new Scanner(System.in);
    private List<DadosSerie> dadosSerie = new ArrayList<>();
    private List<Serie> listaSerie;
    private Optional<Serie> serieBusca;
    private SeriesRepository repositorio;

    public Principal(SeriesRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void exibirMenu(){
        var i = 1;
        while (i != 0){
            var menu = """
                1 - Buscar series
                2 - Buscar episodios
                3 - lista series
                4 - Buscar serie pelo titulo
                5 - Buscar serie pelo ator
                6 - Buscar as top 5 series
                7 - Buscar serie por genero
                8 - Buscar serie por maximo de temporada
                9 - Busca episodio por trecho
                10 - top episodios por serie
                11 - Busca episodios a parte de uma data
                
                0 - sair
                """;

            System.out.println(menu);

            var opcao = ler.nextInt();
            ler.nextLine();

            switch (opcao){
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3 :
                    listaSeries();
                    break;
                case 4 :
                    buscaSerieTitulo();
                    break;
                case 5:
                    buscaPorator();
                    break;
                case 6:
                    bucarTopSeries();
                    break;
                case 7 :
                    buscarPorGenero();
                case 8 :
                    buscaSerieTemporadaMax();
                case 9:
                    buscaEpisodioPorTreco();
                    break;
                case 10:
                    topEpisodioPorSerie();
                    break;
                case 11:
                    buscaEpisodioAparteDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo......");
                    i = 0;
                    break;
                default:
                    System.out.println("Opção invalida");
            }

        }
    }


    private void buscarSerieWeb(){
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repositorio.save(serie);
        //dadosSerie.add(dados) 052512At@;
        //APY0OWThTVFl0Xmw7XjVRMiLQrKr0ACbWGRAUBw0jPhvEySfEI8LSF7n7NlAACa4vUAGeUY6c
        System.out.println(dados);
    }

    private  DadosSerie getDadosSerie(){
        System.out.println("Digite o nome da serie para busca");
        var nomeSerie = ler.nextLine().replace(" ","%20").toLowerCase();
        var json  = minhaApi.ApiOmdb(ENDERECO + nomeSerie + API_KEY);
        DadosSerie dados = conversor.obterDados(json,DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listaSeries();
        System.out.println("Escolha a serie desejada pelo nome: ");
        var nomeSerie = ler.nextLine();

        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()){
            var serieEncontrada = serieBusca.get();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1 ; i <= serieEncontrada.getTotalTemporadas() ; i++) {
                var json = minhaApi.ApiOmdb(ENDERECO +  serieEncontrada.getTitulo() + "&season=" + i +  API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.temporada(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);

            repositorio.save(serieEncontrada);

        }else {
            System.out.println("Serie não encontrada!");
        }



    }

    private void listaSeries(){
        listaSerie = repositorio.findAll();

        listaSerie.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscaSerieTitulo() {
        System.out.println("Digite o nome da serie Desejada: ");
        var nomeSerie = ler.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()){
            System.out.println("Dados da serie: " + serieBusca.get());
        }else {
            System.out.println("Serie nao encontrada!");
        }


    }

    private void buscaPorator() {
        System.out.println("Digite o nome do ator: ");
        var nomeAtor = ler.nextLine();
        System.out.println("Avaliação aparte de qual valor: ");
        var notaSerie = ler.nextDouble();
        List<Serie> atorBuscado = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor,notaSerie);
        atorBuscado.forEach(System.out::println);
    }

    private void bucarTopSeries() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(System.out::println);
    }

    private void buscarPorGenero() {
        System.out.println("Digite o genero desejado: ");
        var genero = ler.nextLine();
        Categoria categoria = Categoria.fromBr(genero);
        List<Serie> generoBuscado = repositorio.findByGenero(categoria);
        generoBuscado.forEach(System.out::println);
    }

    private void buscaSerieTemporadaMax() {
        System.out.println("Digite o maximo de temporada da serie");
        var maxTemporada = ler.nextInt();
        System.out.println("Digite o valor minimo para avaliaçao");
        var avaliacaoMinima = ler.nextDouble();
        List<Serie> seriesMaxTemporada = repositorio.buscaSerieMaxTemporada(maxTemporada,avaliacaoMinima);
        seriesMaxTemporada.forEach(System.out::println);
    }


    private void buscaEpisodioPorTreco() {
        System.out.println("Digite o treco do episodio desejado:");
        var trechoEpisodio = ler.nextLine();
        List<Episodio> episodioBuscado = repositorio.buscaPorTreco(trechoEpisodio);
        episodioBuscado.forEach(e -> System.out.printf("Serie: %s Temporada %s - Episodio %s - %s\n",
                e.getSerie().getTitulo(),e.getTemporada(),e.getNumeroEpisodio(),e.getTitulo()));
    }

    private void topEpisodioPorSerie(){
        buscaSerieTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s Temporada %s - Episodio %s - %s\n",
                    e.getSerie().getTitulo(),e.getTemporada(),e.getNumeroEpisodio(),e.getTitulo(),e.getAvaliacao()));
        }


    };

    private void buscaEpisodioAparteDeUmaData() {
        buscaSerieTitulo();

        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            System.out.println("Digite o ano limite de lançamento: ");
            var anoLancemento = ler.nextInt();
            ler.nextLine();
            List<Episodio> episodioPorData = repositorio.episodioPorDataDeLancamento(serie,anoLancemento);
            episodioPorData.forEach(System.out::println);

        }
    }



}

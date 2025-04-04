package ru.mirea.trpp_second_10.controllers;

import com.opencsv.bean.CsvToBeanBuilder;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import ru.mirea.trpp_second_10.entity.Game;

/** Контроллер для работы с играми. */
@Controller("/game")
public class GameController {

    /** Список игр. */
    private final List<Game> gameList;

    /** Конструктор. */
    public GameController() {
        gameList = new CsvToBeanBuilder<Game>(new InputStreamReader(this.getClass().getResourceAsStream("/MOCK_DATA.csv"))).withType(Game.class).build().parse();
    }

    /**
     * Получить список игр.
     * @return список игр
     */
    @Get()
    public HttpResponse<List<Game>> getGames() {
        return HttpResponse.ok(gameList);
    }

    /**
     * Найти игру по идентификатору.
     * @param id идентификатор игры
     * @return Игра, если есть, иначе 404 ошибка
     */
    @Get("/{id}")
    public HttpResponse<Game> findById(Long id) {
        Optional<Game> result = gameList.stream().filter(it -> it.getId().equals(id)).findAny();
        if (result.isPresent()) {
            return HttpResponse.ok(result.get());
        } else {
            return HttpResponse.notFound();
        }
    }
}

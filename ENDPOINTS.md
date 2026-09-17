# Endpoints da API

Base URL (rodando via Docker): `http://localhost:8080`

Exemplo: `GET /animes` → `http://localhost:8080/animes`

Interface visual (Swagger UI, gerada automaticamente pelo `springdoc-openapi` a partir das anotações `@Tag`/`@Operation`): `http://localhost:8080/swagger-ui.html`

## Users (`/users`)

| Método | Path | Body | Resposta | Erros |
|---|---|---|---|---|
| GET | `/users/{userLogin}` | — | `UserResponse` | 404 |
| GET | `/users` | — | `List<UserResponse>` | — |
| POST | `/users` | `UserRequest` | `UserResponse` (201) | 409 |

## Seasons (`/seasons`)

| Método | Path | Body | Resposta | Erros |
|---|---|---|---|---|
| GET | `/seasons/{year}/{seasonal}` | — | `Season` | 404 |
| GET | `/seasons` | — | `List<Season>` | — |
| POST | `/seasons` | `Season` | `Season` (201) | 409 |
| PUT | `/seasons/{currentYear}/{currentSeasonal}` | `Season` (novo year/seasonal) | `Season` | 404, 409 |
| DELETE | `/seasons/{year}/{seasonal}` | — | 204 | 404 |

## Animes (`/animes`)

| Método | Path | Body | Resposta | Erros |
|---|---|---|---|---|
| GET | `/animes/{id}` | — | `AnimeResponse` | 404 |
| GET | `/animes` | — (query: `page`, `size`, `sort`) | `Page<AnimeResponse>` | — |
| GET | `/animes/search` | — (query: `title`, `page`, `size`, `sort`) | `Page<AnimeResponse>` | — |
| GET | `/animes/season/{seasonYear}/{seasonal}` | — (query: `page`, `size`, `sort`) | `Page<AnimeResponse>` | 404 (season não existe) |
| POST | `/animes` | `AnimeRequest` | `AnimeResponse` (201) | 409 (título duplicado), 404 (season não existe) |
| PUT | `/animes/{id}` | `AnimeRequest` (novo título/dados/season) | `AnimeResponse` | 404 (anime/season não existe), 409 (novo título já usado) |
| DELETE | `/animes/{id}` | — | 204 | 404 |

## Watchlist (`/watchlist`)

| Método | Path | Body | Resposta | Erros |
|---|---|---|---|---|
| POST | `/watchlist` | `WatchlistRequest` (inclui `userLogin`/`animeTitle`) | `WatchlistResponse` (201) | 409 (já está na watchlist), 404 (user/anime não existe) |
| GET | `/watchlist/{userLogin}/{animeTitle}` | — | `WatchlistResponse` | 404 |
| GET | `/watchlist/{userLogin}` | — (query: `page`, `size`, `sort`) | `Page<WatchlistResponse>` | 404 (user não existe) |
| GET | `/watchlist/{userLogin}/season/{seasonYear}/{seasonal}` | — (query: `page`, `size`, `sort`) | `Page<WatchlistResponse>` | 404 (user/season não existe) |
| PUT | `/watchlist/{userLogin}/{animeTitle}` | `WatchlistRequest` (status/episódios/notas) | `WatchlistResponse` | 404 |
| DELETE | `/watchlist/{userLogin}/{animeTitle}` | — | 204 | 404 |

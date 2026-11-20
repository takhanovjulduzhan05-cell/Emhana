# 1–11 апта: M1, M2, M3

| Апта | Кезең | Жергілікті нәтижелер | GitHub-та команда орындайтын әрекет |
|---|---|---|---|
| 1–2 | M1 (v0.1) | 10 user story, қабаттар, starter, ADR | Private repository, collaborator, protected main, issues, board, CI, tag v0.1 |
| 3–6 | M2 (v0.2) | Domain + services + in-memory; Factory, Adapter, Observer, Command, Iterator; консоль | Tests/PR reviews, tag v0.2 |
| 7 | Audit | Үлгілер аудиті, қабылданбаған Singleton/Mediator | Issue + review + ADR |
| 8–11 | M3 (v0.3) | State, Strategy, Builder, Facade, Proxy; 11 pattern registry entries; composed patterns, new strategy extension, tests | Merged PR, green CI, tag v0.3, demo |

**Маңызды:** GitHub репозиторийі, branch protection, PR, ревью, CI статус және релиз тегтері ZIP арқылы автоматты түрде жасалмайды. Олар авторлардың аккаунтында нақты жасалуы тиіс. `mvn verify` және `scripts/check-deliverables.sh` арқылы тексеріледі; deliverables скрипті тектерді талап етеді.

**M4/M5 ауқымына кірмейді:** SQLite/файл Repository, 60% coverage gate-тің толық тексерілгені, v0.4/v1.0, 15-апталық қорғау.

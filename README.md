# Expooosed

Kotlin Exposedでなんか良い感じのDB実装を考える。

# ルール
- Exposedをやめても問題ないような設計にする
- DIにはMinimaCakePatternを採用する

```shell
podman run --rm --name expooosed-postgres -e POSTGRES_PASSWORD=develop -p 5432:5432 docker.io/postgres
```
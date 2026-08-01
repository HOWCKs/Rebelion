# Rebelion — teste de migração para Flutter

## Decisão

Vamos testar Flutter em paralelo ao protótipo Kotlin, sem apagar o app Android nativo.

## Estrutura

```txt
apps/android  → protótipo Kotlin/Compose existente
apps/mobile   → novo protótipo Flutter
```

## Por que testar Flutter

Flutter pode dar mais liberdade criativa para:

- liquid glass/frosted glass;
- animações;
- layouts customizados;
- personalização visual;
- Android agora e iOS no futuro.

## Primeira meta

Recriar a experiência principal do Rebelion:

- Hub;
- Núcleo;
- DMs;
- Espaços;
- Radar;
- Eu;
- Editar Cartão;
- Barra liquid glass com blur real via `BackdropFilter`.

## Critério de decisão

Depois de instalar o APK Flutter, comparar:

- liberdade visual;
- sensação de produto premium;
- fluidez;
- facilidade de evolução;
- conforto de desenvolvimento.

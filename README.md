# Rebelion

Rebelion nasce para ser comunicativo e interativo para mentes que procuram estabelecer uma comunidade, grupo ou sala e conectar seus gostos a outras pessoas.

## Protótipo Android V1

O projeto agora possui uma primeira base instalável/testável em Android nativo:

- Kotlin
- Jetpack Compose
- Material 3
- Interface mockada focada em identidade, conversas, grupos, explorar e perfil
- GitHub Actions para gerar APK debug na nuvem

Código principal:

```txt
apps/android/src/main/java/com/rebelion/app/MainActivity.kt
```

Documentação do protótipo:

```txt
docs/prototype/v1-android-prototype.md
```

## Gerar APK pela nuvem

Ao fazer push na branch `arena/019fb964-rebelion`, o workflow abaixo compila o APK:

```txt
.github/workflows/android-prototype.yml
```

Artifact gerado:

```txt
rebelion-prototype-debug-apk
```

## Visão

O Rebelion quer ser um universo social mobile onde o usuário possa criar sua identidade, encontrar sua turma, conversar do seu jeito e controlar seus espaços de comunidade.

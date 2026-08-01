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




## Teste paralelo em Flutter

Além do protótipo Kotlin em `apps/android`, agora existe um protótipo Flutter em:

```txt
apps/mobile
```

Objetivo do teste Flutter:

- avaliar mais liberdade criativa de UI;
- testar glassmorphism com blur real via `BackdropFilter`;
- comparar sensação visual com o protótipo Kotlin;
- preparar uma possível base Android/iOS.

Como o workflow ativo atual ainda compila o app Kotlin, o workflow Flutter está como template em:

```txt
docs/prototype/flutter-prototype-workflow-template.yml
```

Para ativar pelo Termux:

```bash
cd ~/Rebelion/Rebelion
git pull origin arena/019fb964-rebelion
mkdir -p .github/workflows
cp docs/prototype/flutter-prototype-workflow-template.yml .github/workflows/flutter-prototype.yml
git add .github/workflows/flutter-prototype.yml
git commit -m "Enable Flutter APK workflow"
git push origin arena/019fb964-rebelion
```

Depois, para baixar o APK Flutter:

```bash
REBELION_WORKFLOW="Flutter Prototype APK" bash scripts/termux/download-latest-apk.sh --open
```

## Direção visual atual

O protótipo usa o tema padrão **Rebelion Mono**: preto, branco, grafite e cinza.

A decisão é manter a interface inicial mais neutra e madura, deixando cores fortes para o futuro sistema de personalização de perfil e tema do usuário.

Documento:

```txt
docs/ux/personalization-system.md
```

## Baixar APK pelo Termux diretamente para Downloads

Depois que o GitHub Actions gerar um build com sucesso, no Termux rode:

```bash
git pull origin arena/019fb964-rebelion
bash scripts/termux/download-latest-apk.sh --open
```

O script baixa o último APK bem-sucedido e salva em:

```txt
Downloads/Rebelion/Rebelion-prototype-latest.apk
```

Se o Termux ainda não tiver acesso ao armazenamento do Android, rode antes:

```bash
termux-setup-storage
```

## Visão

O Rebelion quer ser um universo social mobile onde o usuário possa criar sua identidade, encontrar sua turma, conversar do seu jeito e controlar seus espaços de comunidade.

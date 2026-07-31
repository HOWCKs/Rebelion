# Rebelion — Protótipo Android V1

## Objetivo

Criar a primeira versão instalável e testável do Rebelion em Android nativo com Kotlin + Jetpack Compose.

Esta etapa ainda não possui backend real. O foco é testar:

- sensação visual;
- navegação;
- primeira impressão;
- proposta de identidade;
- separação entre conversas, grupos, explorar e perfil;
- linguagem de produto voltada para comunidades jovens/adultas.

## Stack

- Kotlin
- Jetpack Compose
- Material 3
- Gradle
- GitHub Actions para gerar APK debug na nuvem

## Telas presentes no protótipo

1. Boas-vindas.
2. Criação de identidade.
3. Seleção de interesses.
4. Início.
5. Conversas diretas.
6. Grupos.
7. Explorar.
8. Perfil.

## Como gerar APK pela nuvem

Ao fazer push na branch `arena/019fb964-rebelion`, o GitHub Actions executa:

```bash
gradle :apps:android:assembleDebug
```

O APK será publicado como artifact com o nome:

```txt
rebelion-prototype-debug-apk
```


## Baixar direto para a pasta Downloads no Termux

O projeto possui um script para buscar o último APK gerado com sucesso e salvar direto na pasta de downloads do Android:

```bash
bash scripts/termux/download-latest-apk.sh --open
```

Ele salva dois arquivos:

```txt
Downloads/Rebelion/Rebelion-prototype-latest.apk
Downloads/Rebelion/Rebelion-prototype-YYYYMMDD-HHMMSS.apk
```

O arquivo `latest` é sempre sobrescrito com o APK mais recente. O arquivo com data/hora mantém um histórico local dos APKs baixados.

Caso o Termux não tenha permissão de armazenamento, execute:

```bash
termux-setup-storage
```

## O que testar no celular

- O app passa a sensação de comunidade?
- O visual agrada público jovem/adulto sem parecer infantil?
- A personalização aparece cedo o suficiente?
- As áreas principais fazem sentido?
- O grupo parece um espaço vivo, não apenas uma conversa?
- O perfil transmite identidade?
- A navegação inferior está clara?

## Próximas melhorias do protótipo

- tela real de chat 1v1;
- tela real de chat de grupo;
- criação de grupo mockada;
- edição de perfil mockada;
- preview de gravação de áudio;
- seletor visual de efeitos de voz;
- persistência local simples com DataStore/Room;
- backend real na fase seguinte.

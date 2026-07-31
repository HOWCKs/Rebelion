# Rebelion — sistema de personalização visual

## Decisão de produto

O tema padrão inicial do Rebelion deve ser neutro: preto, branco, grafite e cinza. Isso cria uma base madura, limpa e menos infantil.

As cores fortes não devem ser a identidade fixa do aplicativo. Elas devem virar ferramenta de expressão do usuário.

## Dois níveis de personalização

### 1. Personalização do perfil

Afeta apenas como o usuário aparece para outras pessoas.

Exemplos:

- cor de destaque do perfil;
- banner;
- avatar;
- moldura de avatar;
- status visual;
- tags/interesses;
- efeitos de perfil;
- card público de apresentação.

Objetivo:

> Permitir que o usuário seja visto do jeito que escolheu ser.

### 2. Personalização da interface

Afeta como o usuário enxerga o próprio app no celular dele.

Exemplos:

- tema global do app;
- intensidade de contraste;
- cor principal da navegação;
- estilo dos cards;
- fundo com textura ou gradiente;
- modo preto/branco;
- modo neon;
- modo minimalista.

Objetivo:

> Dar controle visual sem bagunçar a experiência para os outros usuários.

## Tema padrão V1

Nome sugerido:

```txt
Rebelion Mono
```

Características:

- fundo quase preto;
- cards grafite;
- texto branco suave;
- bordas cinza discretas;
- botões brancos com texto escuro;
- avatares em escala de cinza;
- ausência de roxo/ciano como padrão global.

## Por que mudar para preto/branco no padrão

1. Fica mais maduro.
2. Não briga com a personalização futura dos perfis.
3. Dá sensação de app mais premium.
4. Permite que cada usuário traga sua própria cor.
5. Evita o app parecer preso a uma única identidade visual.

## Estratégia futura

Na próxima fase, criar tokens de tema:

```kotlin
data class RebelionThemeTokens(
    val background: Color,
    val surface: Color,
    val elevated: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accent: Color,
    val profileAccent: Color
)
```

Assim o app poderá separar:

```txt
Tema do app ≠ Tema do perfil
```

Essa separação é importante porque um usuário pode ter perfil rosa/neon, mas preferir usar o app em tema preto/branco.

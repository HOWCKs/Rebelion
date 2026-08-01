# Rebelion — navegação Liquid Glass

## Objetivo

A barra inferior do Rebelion deve parecer mais premium, menos padrão Android e mais próxima de uma navegação flutuante moderna.

A referência visual é o padrão de barra translúcida arredondada visto em apps modernos de mídia/streaming, mas adaptado à identidade do Rebelion.

## Decisão

A navegação inferior deixa de ser uma `NavigationBar` padrão e passa a ser uma barra própria:

- flutuante;
- arredondada em formato de cápsula;
- translúcida;
- com borda suave;
- com brilho/gradiente interno simulando vidro;
- com ícones sem texto visível;
- com item selecionado em um círculo translúcido.

## Importante

Não copiamos uma interface específica. Usamos o conceito visual de glassmorphism/liquid glass e aplicamos ao Rebelion.

## Acessibilidade

Mesmo sem texto visível na barra, os ícones mantêm `contentDescription`, permitindo leitura por acessibilidade.

## Próximas evoluções

- animação ao trocar de aba;
- feedback tátil;
- ripple customizado;
- blur real quando disponível/viável;
- botão central de ação;
- variação do glass conforme estilo do Cartão Rebelion.

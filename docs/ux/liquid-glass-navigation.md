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
- com item selecionado dentro de uma bolha circular;
- com indicador selecionado deslizando horizontalmente entre os ícones;
- com sensação visual mais 3D.

## Versão atual

A barra usa uma simulação de liquid glass/glassmorphism com:

- transparência maior;
- gradiente vertical de brilho;
- borda com gradiente;
- sombra externa;
- bolha selecionada com radial gradient;
- ícone ativo preenchido em cor clara;
- ícones inativos com menor opacidade;
- animação de slide entre abas.

## Importante

Não copiamos uma interface específica. Usamos o conceito visual de glassmorphism/liquid glass e aplicamos ao Rebelion.

## Acessibilidade

Mesmo sem texto visível na barra, os ícones mantêm `contentDescription`, permitindo leitura por acessibilidade.

## Próximas evoluções

- feedback tátil;
- ripple customizado;
- blur real quando disponível/viável;
- botão central de ação;
- movimento líquido mais orgânico;
- ícones personalizados próprios do Rebelion.

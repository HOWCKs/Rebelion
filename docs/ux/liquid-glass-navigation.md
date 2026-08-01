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

A barra agora é desenhada como **overlay sobre o conteúdo**, em vez de ocupar uma área própria de rodapé. Isso permite que o conteúdo da tela apareça por trás da navegação e reduz a sensação de fundo preto sólido.

A simulação de liquid glass/glassmorphism usa:

- camada base mais transparente;
- gradiente vertical de brilho;
- borda com gradiente;
- sombra externa;
- bolha selecionada com radial gradient mais translúcido;
- ícone ativo preenchido em cor clara;
- ícones inativos com menor opacidade;
- animação de slide entre abas.

## Limite técnico atual

Esta versão ainda não usa blur real do conteúdo atrás da barra. No Android/Compose, blur real com bom desempenho precisa ser implementado com cuidado em etapa própria. Por enquanto, a barra usa transparência, gradiente, borda e sombra para simular vidro.

## Importante

Não copiamos uma interface específica. Usamos o conceito visual de glassmorphism/liquid glass e aplicamos ao Rebelion.

## Acessibilidade

Mesmo sem texto visível na barra, os ícones mantêm `contentDescription`, permitindo leitura por acessibilidade.

## Próximas evoluções

- blur real quando disponível/viável;
- feedback tátil;
- ripple customizado;
- botão central de ação;
- movimento líquido mais orgânico;
- ícones personalizados próprios do Rebelion.

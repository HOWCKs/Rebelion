# Rebelion — Estratégia UI/UX V1

## Objetivo da interface

A interface da primeira versão do Rebelion deve fazer o usuário sentir que entrou em um espaço próprio, social e personalizável. O app precisa transmitir liberdade, identidade e comunidade, sem parecer infantil ou confuso.

A experiência deve misturar três sensações:

1. **Meu lugar** — o usuário tem perfil, nickname, avatar, status, cores e presença própria.
2. **Minha turma** — grupos e comunidades parecem vivos, acolhedores e fáceis de acessar.
3. **Meu controle** — donos/admins conseguem configurar o espaço, aceitar pessoas, fixar elementos e definir regras.

## Público e tom visual

O público busca socialização, reconhecimento e expressão. Muitos usuários querem ser vistos não apenas como eles são na vida real, mas como a identidade que escolhem apresentar online.

A UI deve respeitar isso com:

- avatares destacados;
- nicknames fortes;
- banners de perfil;
- status customizado;
- badges/conquistas sem exagero;
- espaços para interesses;
- grupos com identidade visual própria;
- formas rápidas de reagir, falar, gravar e participar.

O tom visual deve ser:

- escuro como padrão;
- neon discreto;
- moderno;
- expressivo;
- não infantil;
- com opção futura de temas.

## Direção de marca visual

### Tema base

- Fundo principal: preto azulado ou grafite profundo.
- Superfícies: cinza escuro com leve contraste.
- Cor primária: roxo/índigo energético.
- Cor secundária: azul elétrico ou ciano.
- Cor de destaque: verde, rosa ou laranja para eventos e reações.

### Sensação desejada

O Rebelion deve parecer uma mistura de:

- sala de comunidade;
- perfil de rede social;
- hub de entretenimento;
- chat rápido;
- espaço de voz;
- universo próprio do grupo.

## Personalização como recurso central

A personalização não deve ser um detalhe. Ela deve ser uma das primeiras coisas que o usuário percebe.

### Perfil do usuário

Campos planejados:

- avatar;
- banner;
- nickname;
- nome de usuário único;
- bio curta;
- status atual;
- cor de destaque do perfil;
- interesses/tags;
- links sociais opcionais;
- badges futuras;
- opção de perfil público/privado.

### Status sugeridos

- Online;
- Ocupado;
- Invisível;
- Jogando;
- Assistindo;
- Criando;
- Estudando;
- Querendo conversar;
- Personalizado.

### Identidade dentro de grupos

No futuro, permitir que o usuário tenha nickname/avatar diferente por grupo ou servidor. Isso ajuda usuários a se expressarem conforme o contexto.

## Arquitetura de navegação V1

A navegação inicial deve ser simples e mobile-first.

### Abas principais

1. **Início**
2. **Conversas**
3. **Grupos**
4. **Explorar**
5. **Perfil**

### 1. Início

Tela de retorno rápido ao que importa.

Componentes:

- saudação com nickname;
- status do usuário;
- cards de grupos ativos;
- mensagens recentes;
- convites/solicitações;
- atalhos para gravar áudio, entrar em grupo, criar grupo.

Objetivo: o usuário abrir o app e sentir que há movimento.

### 2. Conversas

Área separada para conversas diretas 1v1.

Componentes:

- lista de DMs;
- indicador online;
- última mensagem;
- botão nova conversa;
- filtros: todas, não lidas, favoritas.

A conversa direta deve parecer íntima, rápida e menos caótica que os grupos.

### 3. Grupos

Área dos grupos privados, onde o diferencial começa.

Componentes:

- grupos do usuário;
- solicitações pendentes;
- botão criar grupo;
- imagem/nome do grupo;
- membros ativos;
- mensagem fixada/núcleo;
- indicadores de voz/texto.

### 4. Explorar

Área futura para descoberta de comunidades, interesses e servidores públicos.

Na V1 pode ser simples:

- grupos recomendados;
- tags de interesse;
- comunidades populares;
- destaque de criadores.

### 5. Perfil

Tela onde o usuário sente controle sobre sua identidade.

Componentes:

- avatar grande;
- banner;
- nickname;
- status;
- bio;
- tags de interesse;
- botão editar perfil;
- configurações;
- privacidade.

## Fluxo de onboarding V1

O onboarding deve ser curto, mas emocional.

### Etapa 1 — Boas-vindas

Mensagem sugerida:

> Seu espaço, sua voz, sua comunidade.

Ações:

- criar conta;
- entrar.

### Etapa 2 — Criar identidade

Campos:

- nickname;
- avatar;
- nome de usuário único.

Mensagem:

> Escolha como o mundo vai te encontrar no Rebelion.

### Etapa 3 — Escolher interesses

Tags iniciais:

- jogos;
- anime;
- música;
- filmes/séries;
- tecnologia;
- estudo;
- arte;
- humor;
- esportes;
- criadores;
- comunidades.

### Etapa 4 — Primeira ação

Dar três opções:

- criar meu primeiro grupo;
- encontrar comunidades;
- começar uma conversa.

## Experiência de grupos

O grupo deve ser tratado como um espaço vivo, não apenas uma lista de mensagens.

### Elementos do grupo

- nome;
- foto;
- descrição;
- dono;
- admins;
- membros;
- regras;
- entrada por solicitação;
- mensagem fixada central;
- mural/núcleo do grupo;
- chat;
- áudio;
- reações;
- stickers.

### Núcleo do grupo

O núcleo é uma área fixada visível para todos. Pode conter:

- mensagem importante;
- imagem;
- aviso;
- desafio;
- enquete futura;
- link;
- regra;
- evento.

Isso dá sensação de identidade coletiva.

## Conversa direta 1v1

A DM deve ser livre, mas simples.

Recursos V1:

- texto;
- emojis;
- envio de áudio;
- áudio modificado;
- imagem futura;
- indicador digitando;
- status online;
- bloquear/denunciar.

A interface da conversa deve ter um botão de áudio bem acessível, pois a voz modificada é diferencial do Rebelion.

## Áudio modificado

Na V1, o modificador será aplicado em áudio gravado, não em chamada ao vivo.

### Fluxo sugerido

1. Usuário segura botão de microfone.
2. Grava áudio.
3. Tela/preview mostra opções de efeito.
4. Usuário escuta antes de enviar.
5. Envia no chat.

### Efeitos iniciais

Evitar usar nomes de personagens protegidos oficialmente. Usar nomes genéricos:

- voz fina;
- voz grave;
- robô;
- fantasma;
- eco;
- rápido;
- lento;
- monstro;
- rádio antigo.

## Servidores

Na V1, servidores podem ser preparados no design, mas não precisam estar completos no primeiro build.

### Estrutura futura

- servidor;
- canais de texto;
- canais de voz;
- cargos;
- permissões;
- convites;
- regras;
- moderação;
- sala compartilhada.

## Sala compartilhada

A sala compartilhada deve ser pensada como entretenimento social, não como ferramenta de pirataria.

Recursos futuros:

- host controla navegação;
- membros assistem juntos;
- chat lateral;
- voz em grupo;
- sincronização de link;
- bloqueio de conteúdo abusivo/ilegal;
- suporte a conteúdo próprio e sites permitidos.

## Segurança, privacidade e moderação

Como o app mira público jovem e comunidades abertas, segurança precisa nascer junto com a UX.

Recursos essenciais:

- bloquear usuário;
- denunciar usuário;
- denunciar grupo;
- remover membro;
- banir membro;
- aprovar solicitação de entrada;
- grupos privados por padrão;
- controle de quem pode mandar DM;
- ocultar informações sensíveis;
- política contra assédio, exploração, pirataria e conteúdo ilegal.

A liberdade do usuário deve existir junto com controle e proteção.

## Evitar armadilhas de UX

O Rebelion deve ser envolvente sem usar padrões nocivos.

Evitar:

- pressão artificial para responder;
- ranking agressivo de popularidade;
- exposição pública de rejeição;
- incentivo a spam;
- notificações excessivas;
- mecânicas que explorem insegurança social.

Preferir:

- pertencimento;
- expressão criativa;
- controle de privacidade;
- descoberta saudável;
- moderação clara;
- comunidades com regras.

## Componentes Compose planejados

Para Kotlin + Jetpack Compose, os principais componentes da V1 serão:

- `RebelionTheme`;
- `MainScaffold`;
- `BottomNavigationBar`;
- `Avatar`;
- `UserStatusBadge`;
- `ConversationListItem`;
- `GroupCard`;
- `MessageBubble`;
- `AudioMessageBubble`;
- `VoiceEffectSelector`;
- `PinnedCoreCard`;
- `ProfileHeader`;
- `InterestChip`;
- `PrimaryButton`;
- `RebelionTextField`.

## Telas V1

### Obrigatórias

1. Splash.
2. Login.
3. Cadastro.
4. Criar identidade.
5. Escolher interesses.
6. Home.
7. Conversas.
8. Chat 1v1.
9. Grupos.
10. Criar grupo.
11. Chat do grupo.
12. Perfil.
13. Editar perfil.
14. Configurações básicas.

### Preparadas para futuro

1. Servidores.
2. Canais.
3. Sala de voz.
4. Sala compartilhada.
5. Explorar comunidades.

## Métrica de sucesso da V1

A primeira versão deve provar que usuários conseguem:

- criar identidade;
- entrar no app sem confusão;
- conversar por texto;
- participar de grupo;
- personalizar perfil;
- gravar/enviar áudio;
- entender que o app é sobre comunidade e expressão.

## Frase-guia de design

> O Rebelion deve fazer o usuário sentir que encontrou um lugar onde pode ser visto do jeito que escolheu ser.

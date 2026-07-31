# Rebelion — interface voltada ao consumidor

## Decisão

A interface do Rebelion não deve exibir textos de protótipo, versão, desenvolvimento ou dados falsos que pareçam atividade real.

O app deve se comportar como um produto real desde cedo:

- se o usuário não tem conversas, a tela mostra estado vazio;
- se o usuário não tem grupos, a tela mostra estado vazio;
- se não existem comunidades públicas, o explorar mostra estado vazio;
- conteúdos só devem aparecer quando forem criados pelo usuário, por outros usuários ou pelo backend.

## Motivo

O usuário final não precisa saber que está usando uma versão de teste dentro da interface principal. Informações de versão, changelog, build e desenvolvimento devem ficar fora do fluxo comum, como em:

- página da loja;
- tela Sobre;
- configurações avançadas;
- documentação interna;
- GitHub/Releases.

## Regra de experiência

> O app só deve mostrar aquilo que existe para o usuário.

## Aplicação na V1

Foram removidos da interface principal:

- grupos falsos;
- conversas falsas;
- comunidades/categorias futuras falsas;
- textos de protótipo;
- botões com “em breve”.

Foram adicionados:

- estados vazios;
- chamadas de ação;
- ícones Material mais representativos;
- textos mais naturais para usuário final.

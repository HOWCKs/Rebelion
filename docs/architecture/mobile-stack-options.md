# Rebelion — opções de stack mobile Android

## Contexto

O Rebelion precisa de recursos avançados para Android:

- conversa direta 1v1;
- grupos e servidores;
- mensagens em tempo real;
- gravação e envio de áudio;
- modificadores de voz;
- chamadas de voz/vídeo;
- sala compartilhada para assistir/navegar junto;
- notificações push;
- boa experiência em celulares Android intermediários;
- build em nuvem usando GitHub Actions.

Como React Native + Expo/Expo Go pode travar o projeto quando entra em recursos nativos avançados, a opção mais segura para Android é iniciar com app nativo.

## Recomendação principal

### Android nativo com Kotlin + Jetpack Compose

Stack recomendada:

- Kotlin
- Jetpack Compose
- Android Gradle Plugin
- Material 3
- Navigation Compose
- ViewModel + StateFlow
- Room
- DataStore
- Ktor Client ou Retrofit
- OkHttp WebSocket
- Firebase Cloud Messaging
- LiveKit Android SDK para áudio/vídeo em tempo real
- Media3/ExoPlayer para reprodução de mídia
- WebView controlada para sala compartilhada
- Coil para imagens
- Hilt/Koin para injeção de dependência

## Por que essa stack combina com o Rebelion

1. Melhor compatibilidade com Android real.
2. Menos dependência de bridges como em React Native.
3. Melhor acesso a áudio, microfone, câmera, WebRTC, WebView e serviços em segundo plano.
4. Melhor performance em listas de mensagens e telas complexas.
5. Melhor controle sobre notificações, permissões e ciclo de vida do app.
6. Build confiável em GitHub Actions com Gradle.
7. Possibilidade de começar simples e adicionar recursos avançados por módulos.

## Alternativas analisadas

### Flutter

Boa opção se o objetivo for Android e iOS com uma única base visual. Tem ótima UI e performance, mas recursos como modificação de voz em tempo real, WebRTC avançado e integração nativa podem exigir plugins ou código nativo adicional.

Indicado se o projeto quiser app bonito e multiplataforma desde cedo.

### Kotlin Multiplatform

Boa opção para compartilhar regras de negócio entre Android e iOS, mas ainda exige UI nativa separada. Pode ser uma evolução futura, não necessariamente a melhor primeira etapa.

### React Native sem Expo

Daria mais liberdade que Expo Go, mas ainda manteria dependência da ponte nativa e de configuração complexa. Para este projeto, não é a opção mais segura se a prioridade inicial é Android forte.

## Decisão recomendada

Começar com Android nativo:

```txt
Mobile Android: Kotlin + Jetpack Compose
Backend: NestJS + PostgreSQL + Prisma
Realtime: WebSocket/Socket.IO ou protocolo próprio via WebSocket
Áudio/Vídeo: LiveKit
Storage: Cloudflare R2 ou S3 compatível
Push: Firebase Cloud Messaging
CI/CD: GitHub Actions + Gradle
```

## Estratégia de evolução

### Fase 1 — Base Android

- splash/onboarding;
- login/cadastro;
- tela inicial;
- lista de conversas;
- chat 1v1 com texto;
- conexão WebSocket;
- cache local com Room.

### Fase 2 — Grupos

- criar grupo;
- aprovar solicitação de entrada;
- enviar mensagens;
- fixar mensagem;
- permissões de dono/admin.

### Fase 3 — Áudio gravado

- gravar áudio;
- reproduzir áudio;
- enviar áudio;
- aplicar modificadores em áudio gravado.

### Fase 4 — Servidores

- criar servidor;
- canais;
- cargos simples;
- permissões;
- membros.

### Fase 5 — Voz/vídeo ao vivo

- LiveKit;
- salas de voz;
- chamada 1v1;
- chamada em grupo;
- preparação para efeitos de voz em tempo real.

### Fase 6 — Sala compartilhada

- WebView sincronizada;
- chat lateral;
- controle do host/admin;
- links compartilhados;
- política contra pirataria e abuso.

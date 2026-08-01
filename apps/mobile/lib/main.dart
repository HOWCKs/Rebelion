import 'dart:ui';

import 'package:flutter/material.dart';

void main() => runApp(const RebelionApp());

class RebelionApp extends StatefulWidget {
  const RebelionApp({super.key});

  @override
  State<RebelionApp> createState() => _RebelionAppState();
}

class _RebelionAppState extends State<RebelionApp> {
  String nickname = 'Nox';
  String bio = 'criando meu universo, encontrando minha turma e deixando minha marca no Rebelion.';
  String status = 'Querendo conversar ✦';
  String cardStyle = 'Mono';
  final Set<String> interests = {};

  bool onboarded = false;

  void saveCard({
    required String nickname,
    required String bio,
    required String status,
    required String cardStyle,
    required Set<String> interests,
  }) {
    setState(() {
      this.nickname = nickname.trim().isEmpty ? 'Nox' : nickname.trim();
      this.bio = bio.trim();
      this.status = status;
      this.cardStyle = cardStyle;
      this.interests
        ..clear()
        ..addAll(interests);
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Rebelion',
      theme: ThemeData(
        useMaterial3: true,
        brightness: Brightness.dark,
        scaffoldBackgroundColor: RebelionColors.deepSpace,
        fontFamily: 'Roboto',
      ),
      home: onboarded
          ? MainShell(
              nickname: nickname,
              bio: bio,
              status: status,
              cardStyle: cardStyle,
              interests: interests,
              onSaveCard: saveCard,
            )
          : WelcomeFlow(
              nickname: nickname,
              interests: interests,
              onNicknameChanged: (value) => setState(() => nickname = value),
              onToggleInterest: (value) {
                setState(() {
                  interests.contains(value) ? interests.remove(value) : interests.add(value);
                });
              },
              onEnter: () => setState(() => onboarded = true),
            ),
    );
  }
}

enum MainTab {
  hub(Icons.home_rounded, 'Hub'),
  dms(Icons.chat_bubble_rounded, 'DMs'),
  spaces(Icons.groups_rounded, 'Espaços'),
  radar(Icons.search_rounded, 'Radar'),
  me(Icons.person_rounded, 'Eu');

  const MainTab(this.icon, this.label);
  final IconData icon;
  final String label;
}

class MainShell extends StatefulWidget {
  const MainShell({
    super.key,
    required this.nickname,
    required this.bio,
    required this.status,
    required this.cardStyle,
    required this.interests,
    required this.onSaveCard,
  });

  final String nickname;
  final String bio;
  final String status;
  final String cardStyle;
  final Set<String> interests;
  final void Function({
    required String nickname,
    required String bio,
    required String status,
    required String cardStyle,
    required Set<String> interests,
  }) onSaveCard;

  @override
  State<MainShell> createState() => _MainShellState();
}

class _MainShellState extends State<MainShell> {
  MainTab selectedTab = MainTab.hub;
  bool editingCard = false;

  @override
  Widget build(BuildContext context) {
    if (editingCard) {
      return EditCardScreen(
        nickname: widget.nickname,
        bio: widget.bio,
        status: widget.status,
        cardStyle: widget.cardStyle,
        interests: widget.interests,
        onBack: () => setState(() => editingCard = false),
        onSave: ({required nickname, required bio, required status, required cardStyle, required interests}) {
          widget.onSaveCard(
            nickname: nickname,
            bio: bio,
            status: status,
            cardStyle: cardStyle,
            interests: interests,
          );
          setState(() {
            selectedTab = MainTab.me;
            editingCard = false;
          });
        },
      );
    }

    return Scaffold(
      body: Stack(
        children: [
          Positioned.fill(
            child: IndexedStack(
              index: selectedTab.index,
              children: [
                HubPage(nickname: widget.nickname, onPersonalize: () => setState(() => editingCard = true)),
                const DmsPage(),
                const SpacesPage(),
                RadarPage(interests: widget.interests),
                MePage(
                  nickname: widget.nickname,
                  bio: widget.bio,
                  status: widget.status,
                  cardStyle: widget.cardStyle,
                  interests: widget.interests,
                  onEdit: () => setState(() => editingCard = true),
                ),
              ],
            ),
          ),
          Align(
            alignment: Alignment.bottomCenter,
            child: RebelionGlassNav(
              selectedTab: selectedTab,
              onSelect: (tab) => setState(() => selectedTab = tab),
            ),
          ),
        ],
      ),
    );
  }
}

class WelcomeFlow extends StatefulWidget {
  const WelcomeFlow({
    super.key,
    required this.nickname,
    required this.interests,
    required this.onNicknameChanged,
    required this.onToggleInterest,
    required this.onEnter,
  });

  final String nickname;
  final Set<String> interests;
  final ValueChanged<String> onNicknameChanged;
  final ValueChanged<String> onToggleInterest;
  final VoidCallback onEnter;

  @override
  State<WelcomeFlow> createState() => _WelcomeFlowState();
}

class _WelcomeFlowState extends State<WelcomeFlow> {
  int step = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [RebelionColors.deepSpace, RebelionColors.panel, RebelionColors.graphite],
          ),
        ),
        child: SafeArea(
          child: Padding(
            padding: const EdgeInsets.all(24),
            child: step == 0 ? _welcome() : step == 1 ? _identity() : _interests(),
          ),
        ),
      ),
    );
  }

  Widget _welcome() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const SizedBox(height: 24),
        const RebelionMark(size: 76),
        const SizedBox(height: 28),
        const Text('REBELION', style: TextStyle(fontSize: 44, fontWeight: FontWeight.w900, letterSpacing: 2)),
        const SizedBox(height: 8),
        const Text('Seu hub. Sua voz. Seus espaços.', style: TextStyle(fontSize: 18, color: RebelionColors.mist)),
        const SizedBox(height: 28),
        const RebelionCard(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('Construa seu universo social', style: TextStyle(fontSize: 22, fontWeight: FontWeight.w900)),
              SizedBox(height: 10),
              Text('Crie espaços, inicie laços e personalize como você aparece para sua comunidade.', style: TextStyle(color: RebelionColors.mist, height: 1.35)),
              SizedBox(height: 14),
              Wrap(spacing: 8, children: [MiniPill('DMs'), MiniPill('Espaços'), MiniPill('Voz')]),
            ],
          ),
        ),
        const Spacer(),
        RebelionButton(text: 'Criar minha identidade', onPressed: () => setState(() => step = 1)),
        const SizedBox(height: 12),
        GhostButton(text: 'Entrar', onPressed: widget.onEnter),
      ],
    );
  }

  Widget _identity() {
    return ListView(
      children: [
        const SizedBox(height: 18),
        const SectionTitle(title: 'Escolha como vão te ver', subtitle: 'No Rebelion, seu perfil é parte da sua presença.'),
        const SizedBox(height: 18),
        ProfilePreviewCard(nickname: widget.nickname.isEmpty ? 'Nox' : widget.nickname, status: 'Querendo conversar ✦', cardStyle: 'Mono'),
        const SizedBox(height: 16),
        RebelionInput(label: 'Nickname', value: widget.nickname, onChanged: widget.onNicknameChanged),
        const SizedBox(height: 12),
        RebelionInput(label: 'Nome de usuário único', value: '@${slug(widget.nickname)}', enabled: false, onChanged: (_) {}),
        const SizedBox(height: 16),
        RebelionButton(text: 'Continuar', onPressed: () => setState(() => step = 2)),
      ],
    );
  }

  Widget _interests() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const SizedBox(height: 24),
        const SectionTitle(title: 'Monte seu radar social', subtitle: 'Escolha interesses para guiar seus espaços, DMs e descobertas.'),
        const SizedBox(height: 22),
        InterestWrap(selected: widget.interests, onToggle: widget.onToggleInterest),
        const Spacer(),
        Text('Selecionados: ${widget.interests.length}', style: const TextStyle(color: RebelionColors.mist)),
        const SizedBox(height: 10),
        RebelionButton(text: 'Entrar no Rebelion', onPressed: widget.onEnter),
      ],
    );
  }
}

class RebelionGlassNav extends StatelessWidget {
  const RebelionGlassNav({super.key, required this.selectedTab, required this.onSelect});

  final MainTab selectedTab;
  final ValueChanged<MainTab> onSelect;

  @override
  Widget build(BuildContext context) {
    const horizontalMargin = 22.0;
    const height = 86.0;
    const verticalMargin = 14.0;

    return Padding(
      padding: const EdgeInsets.fromLTRB(horizontalMargin, 0, horizontalMargin, verticalMargin),
      child: LayoutBuilder(
        builder: (context, constraints) {
          final itemWidth = constraints.maxWidth / MainTab.values.length;
          final indicatorWidth = itemWidth - 4;
          final targetLeft = selectedTab.index * itemWidth + 2;

          return ClipRRect(
            borderRadius: BorderRadius.circular(44),
            child: BackdropFilter(
              filter: ImageFilter.blur(sigmaX: 22, sigmaY: 22),
              child: Container(
                height: height,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(44),
                  color: Colors.white.withOpacity(0.16),
                  border: Border.all(color: Colors.white.withOpacity(0.48), width: 1.1),
                  gradient: LinearGradient(
                    begin: Alignment.topCenter,
                    end: Alignment.bottomCenter,
                    colors: [
                      Colors.white.withOpacity(0.42),
                      Colors.white.withOpacity(0.16),
                      Colors.white.withOpacity(0.08),
                    ],
                  ),
                  boxShadow: [
                    BoxShadow(color: Colors.black.withOpacity(0.38), blurRadius: 28, offset: const Offset(0, 12)),
                    BoxShadow(color: RebelionColors.glassGlow.withOpacity(0.18), blurRadius: 18, offset: const Offset(0, -2)),
                  ],
                ),
                child: Stack(
                  alignment: Alignment.centerLeft,
                  children: [
                    AnimatedPositioned(
                      duration: const Duration(milliseconds: 430),
                      curve: Curves.easeOutCubic,
                      left: targetLeft,
                      top: 4,
                      bottom: 4,
                      width: indicatorWidth,
                      child: DecoratedBox(
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(40),
                          gradient: LinearGradient(
                            begin: Alignment.topCenter,
                            end: Alignment.bottomCenter,
                            colors: [
                              Colors.white.withOpacity(0.44),
                              RebelionColors.glassSelected.withOpacity(0.58),
                              RebelionColors.glassSelected.withOpacity(0.25),
                            ],
                          ),
                          border: Border.all(color: Colors.white.withOpacity(0.28)),
                          boxShadow: [BoxShadow(color: Colors.black.withOpacity(0.26), blurRadius: 16, offset: const Offset(0, 8))],
                        ),
                      ),
                    ),
                    Row(
                      children: MainTab.values.map((tab) {
                        final selected = selectedTab == tab;
                        return SizedBox(
                          width: itemWidth,
                          child: InkWell(
                            borderRadius: BorderRadius.circular(40),
                            onTap: () => onSelect(tab),
                            child: Center(
                              child: Icon(
                                tab.icon,
                                semanticLabel: tab.label,
                                size: selected ? 33 : 28,
                                color: selected ? RebelionColors.navActive : RebelionColors.mist.withOpacity(0.68),
                              ),
                            ),
                          ),
                        );
                      }).toList(),
                    ),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}

class HubPage extends StatelessWidget {
  const HubPage({super.key, required this.nickname, required this.onPersonalize});

  final String nickname;
  final VoidCallback onPersonalize;

  @override
  Widget build(BuildContext context) {
    return RebelionScroll(
      children: [
        Row(
          children: [
            Expanded(child: SectionTitle(title: 'Hub', subtitle: '$nickname, escolha o que quer mover agora.')),
            AvatarBubble(label: initialOf(nickname, fallback: 'R'), color: RebelionColors.steel),
          ],
        ),
        const CoreNucleusCard(),
        const SectionHeader('Mover agora'),
        GridActions(onPersonalize: onPersonalize),
        const EmptyStateCard(icon: Icons.home_rounded, title: 'O pulso está quieto', body: 'Quando algo acontecer nas suas DMs, espaços ou convites, o Núcleo muda primeiro.'),
      ],
    );
  }
}

class GridActions extends StatelessWidget {
  const GridActions({super.key, required this.onPersonalize});
  final VoidCallback onPersonalize;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(children: [Expanded(child: QuickAction(icon: Icons.groups_rounded, label: 'Criar espaço')), const SizedBox(width: 12), Expanded(child: QuickAction(icon: Icons.chat_bubble_rounded, label: 'Nova DM'))]),
        const SizedBox(height: 12),
        Row(children: [Expanded(child: QuickAction(icon: Icons.mic_rounded, label: 'Gravar voz')), const SizedBox(width: 12), Expanded(child: QuickAction(icon: Icons.edit_rounded, label: 'Personalizar', onTap: onPersonalize))]),
      ],
    );
  }
}

class CoreNucleusCard extends StatelessWidget {
  const CoreNucleusCard({super.key});

  @override
  Widget build(BuildContext context) {
    return RebelionCard(
      radius: 30,
      child: Column(
        children: [
          const Text('Núcleo', style: TextStyle(color: RebelionColors.muted, fontWeight: FontWeight.w800)),
          const SizedBox(height: 18),
          Container(
            height: 154,
            width: 154,
            decoration: const BoxDecoration(shape: BoxShape.circle, gradient: RadialGradient(colors: [RebelionColors.graphiteLight, RebelionColors.graphite, RebelionColors.panel])),
            child: Center(
              child: Container(
                height: 112,
                width: 112,
                decoration: const BoxDecoration(color: RebelionColors.deepSpace, shape: BoxShape.circle),
                child: const Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text('QUIETO', style: TextStyle(fontSize: 22, fontWeight: FontWeight.w900)),
                    Text('agora', style: TextStyle(color: RebelionColors.muted)),
                  ],
                ),
              ),
            ),
          ),
          const SizedBox(height: 18),
          const Text('Seu Núcleo mostra presença, movimento e sinais importantes dos seus espaços.', style: TextStyle(color: RebelionColors.mist, height: 1.35)),
          const SizedBox(height: 16),
          const Row(children: [Expanded(child: NucleusMetric(label: 'DMs')), SizedBox(width: 8), Expanded(child: NucleusMetric(label: 'Espaços')), SizedBox(width: 8), Expanded(child: NucleusMetric(label: 'Convites'))]),
          const SizedBox(height: 14),
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(14),
            decoration: BoxDecoration(color: RebelionColors.deepSpace, borderRadius: BorderRadius.circular(18)),
            child: const Text('Presença: querendo conversar ✦', style: TextStyle(fontWeight: FontWeight.w900)),
          ),
        ],
      ),
    );
  }
}

class NucleusMetric extends StatelessWidget {
  const NucleusMetric({super.key, required this.label});
  final String label;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 14),
      decoration: BoxDecoration(color: RebelionColors.deepSpace, borderRadius: BorderRadius.circular(18)),
      child: Column(children: [const Text('0', style: TextStyle(fontWeight: FontWeight.w900, fontSize: 20)), Text(label, style: const TextStyle(color: RebelionColors.muted, fontSize: 12))]),
    );
  }
}

class DmsPage extends StatelessWidget {
  const DmsPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const RebelionScroll(
      children: [
        SectionTitle(title: 'DMs', subtitle: 'Conexões diretas para conversar por texto, áudio normal ou voz com efeitos.'),
        RebelionButton(text: 'Iniciar DM'),
        EmptyStateCard(icon: Icons.chat_bubble_rounded, title: 'Nenhum laço iniciado', body: 'Comece uma DM e crie sua primeira conexão dentro do Rebelion.'),
      ],
    );
  }
}

class SpacesPage extends StatelessWidget {
  const SpacesPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const RebelionScroll(
      children: [
        SectionTitle(title: 'Espaços', subtitle: 'Crie lugares privados para sua turma conversar, organizar ideias e manter um núcleo vivo.'),
        RebelionButton(text: 'Criar espaço'),
        EmptyStateCard(icon: Icons.groups_rounded, title: 'Nenhum espaço criado', body: 'Crie um lugar para sua turma existir do jeito de vocês.'),
      ],
    );
  }
}

class RadarPage extends StatelessWidget {
  const RadarPage({super.key, required this.interests});
  final Set<String> interests;

  @override
  Widget build(BuildContext context) {
    return RebelionScroll(
      children: [
        const SectionTitle(title: 'Radar', subtitle: 'Encontre espaços, pessoas e comunidades próximas dos seus interesses.'),
        if (interests.isNotEmpty) ...[
          const SectionHeader('Sinais do seu radar'),
          InterestWrap(selected: interests, onToggle: (_) {}),
        ],
        const EmptyStateCard(icon: Icons.search_rounded, title: 'Nada no radar ainda', body: 'Quando comunidades públicas surgirem, elas aparecem aqui para descoberta.'),
      ],
    );
  }
}

class MePage extends StatelessWidget {
  const MePage({super.key, required this.nickname, required this.bio, required this.status, required this.cardStyle, required this.interests, required this.onEdit});

  final String nickname;
  final String bio;
  final String status;
  final String cardStyle;
  final Set<String> interests;
  final VoidCallback onEdit;

  @override
  Widget build(BuildContext context) {
    return RebelionScroll(
      children: [
        const SectionTitle(title: 'Eu', subtitle: 'Seu Cartão Rebelion reúne presença, identidade, estilo e controle.'),
        ProfilePreviewCard(nickname: nickname, status: status, cardStyle: cardStyle),
        RebelionIdentityCard(bio: bio, status: status, cardStyle: cardStyle, interests: interests),
        const RebelionSectionTile(icon: Icons.person_rounded, title: 'Presença', body: 'Status, bio e como você quer ser encontrado.'),
        const RebelionSectionTile(icon: Icons.edit_rounded, title: 'Identidade', body: 'Nickname, interesses e marcas que definem seu perfil.'),
        const RebelionSectionTile(icon: Icons.home_rounded, title: 'Estilo', body: 'Banner, avatar, cores e aparência pública.'),
        const RebelionSectionTile(icon: Icons.search_rounded, title: 'Controle', body: 'Privacidade, DMs e visibilidade dentro dos espaços.'),
        GhostButton(text: 'Editar Cartão', icon: Icons.edit_rounded, onPressed: onEdit),
      ],
    );
  }
}

class EditCardScreen extends StatefulWidget {
  const EditCardScreen({
    super.key,
    required this.nickname,
    required this.bio,
    required this.status,
    required this.cardStyle,
    required this.interests,
    required this.onBack,
    required this.onSave,
  });

  final String nickname;
  final String bio;
  final String status;
  final String cardStyle;
  final Set<String> interests;
  final VoidCallback onBack;
  final void Function({required String nickname, required String bio, required String status, required String cardStyle, required Set<String> interests}) onSave;

  @override
  State<EditCardScreen> createState() => _EditCardScreenState();
}

class _EditCardScreenState extends State<EditCardScreen> {
  late String nickname = widget.nickname;
  late String bio = widget.bio;
  late String status = widget.status;
  late String cardStyle = widget.cardStyle;
  late final Set<String> interests = {...widget.interests};

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: RebelionScroll(
        bottomPadding: 28,
        children: [
          Row(
            children: [
              const Expanded(child: SectionTitle(title: 'Editar Cartão', subtitle: 'Ajuste como sua presença aparece no Rebelion.')),
              GhostButton(text: 'Voltar', compact: true, onPressed: widget.onBack),
            ],
          ),
          ProfilePreviewCard(nickname: nickname, status: status, cardStyle: cardStyle),
          RebelionCard(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [const SectionHeader('Identidade'), const SizedBox(height: 12), RebelionInput(label: 'Nickname', value: nickname, onChanged: (v) => setState(() => nickname = v)), const SizedBox(height: 12), RebelionInput(label: 'Bio', value: bio, onChanged: (v) => setState(() => bio = v))])),
          RebelionCard(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [const SectionHeader('Presença'), const SizedBox(height: 12), ChoiceWrap(values: statusOptions, selected: {status}, onToggle: (v) => setState(() => status = v))])),
          RebelionCard(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [const SectionHeader('Interesses'), const SizedBox(height: 12), InterestWrap(selected: interests, onToggle: (v) => setState(() => interests.contains(v) ? interests.remove(v) : interests.add(v)))])),
          RebelionCard(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [const SectionHeader('Estilo do cartão'), const SizedBox(height: 8), const Text('O estilo muda o banner, avatar e destaque público do seu Cartão Rebelion.', style: TextStyle(color: RebelionColors.mist, height: 1.35)), const SizedBox(height: 12), ChoiceWrap(values: cardStyleOptions, selected: {cardStyle}, onToggle: (v) => setState(() => cardStyle = v))])),
          RebelionButton(text: 'Salvar Cartão', onPressed: () => widget.onSave(nickname: nickname, bio: bio, status: status, cardStyle: cardStyle, interests: interests)),
        ],
      ),
    );
  }
}

class RebelionScroll extends StatelessWidget {
  const RebelionScroll({super.key, required this.children, this.bottomPadding = 128});
  final List<Widget> children;
  final double bottomPadding;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: ListView.separated(
        padding: EdgeInsets.fromLTRB(18, 22, 18, bottomPadding),
        itemBuilder: (context, index) => children[index],
        separatorBuilder: (_, __) => const SizedBox(height: 14),
        itemCount: children.length,
      ),
    );
  }
}

class SectionTitle extends StatelessWidget {
  const SectionTitle({super.key, required this.title, required this.subtitle});
  final String title;
  final String subtitle;

  @override
  Widget build(BuildContext context) {
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [Text(title, style: const TextStyle(fontSize: 34, fontWeight: FontWeight.w900)), const SizedBox(height: 8), Text(subtitle, style: const TextStyle(color: RebelionColors.mist, fontSize: 16, height: 1.35))]);
  }
}

class SectionHeader extends StatelessWidget {
  const SectionHeader(this.text, {super.key});
  final String text;
  @override
  Widget build(BuildContext context) => Text(text, style: const TextStyle(fontSize: 22, fontWeight: FontWeight.w900));
}

class RebelionCard extends StatelessWidget {
  const RebelionCard({super.key, required this.child, this.radius = 24});
  final Widget child;
  final double radius;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(color: RebelionColors.panel.withOpacity(0.94), borderRadius: BorderRadius.circular(radius), border: Border.all(color: Colors.white.withOpacity(0.07))),
      child: child,
    );
  }
}

class ProfilePreviewCard extends StatelessWidget {
  const ProfilePreviewCard({super.key, required this.nickname, required this.status, required this.cardStyle});
  final String nickname;
  final String status;
  final String cardStyle;

  @override
  Widget build(BuildContext context) {
    final accent = cardStyleAccent(cardStyle);
    return Container(
      decoration: BoxDecoration(color: RebelionColors.panel, borderRadius: BorderRadius.circular(28), border: Border.all(color: accent.withOpacity(0.38))),
      clipBehavior: Clip.antiAlias,
      child: Column(
        children: [
          Container(height: 104, decoration: BoxDecoration(gradient: LinearGradient(colors: cardStyleGradient(cardStyle)))),
          Padding(
            padding: const EdgeInsets.all(18),
            child: Row(
              children: [
                AvatarBubble(label: initialOf(nickname, fallback: 'N'), color: accent, size: 64),
                const SizedBox(width: 14),
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [Text(nickname, style: const TextStyle(fontSize: 24, fontWeight: FontWeight.w900)), Text('@${slug(nickname)}', style: const TextStyle(color: RebelionColors.muted)), Text(status, style: TextStyle(color: accent))]),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class RebelionIdentityCard extends StatelessWidget {
  const RebelionIdentityCard({super.key, required this.bio, required this.status, required this.cardStyle, required this.interests});
  final String bio;
  final String status;
  final String cardStyle;
  final Set<String> interests;

  @override
  Widget build(BuildContext context) {
    return RebelionCard(
      child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Row(children: [const AvatarIcon(icon: Icons.person_rounded), const SizedBox(width: 12), const Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [Text('Cartão Rebelion', style: TextStyle(fontSize: 20, fontWeight: FontWeight.w900)), Text('Sua identidade pública começa aqui.', style: TextStyle(color: RebelionColors.muted))]))]),
        const SizedBox(height: 18),
        Wrap(crossAxisAlignment: WrapCrossAlignment.center, spacing: 8, runSpacing: 8, children: [Text(status, style: const TextStyle(fontSize: 18, fontWeight: FontWeight.w900)), MiniStylePill(cardStyle)]),
        const SizedBox(height: 14),
        Text('Bio: $bio', style: const TextStyle(color: RebelionColors.mist, height: 1.35)),
        const SizedBox(height: 14),
        if (interests.isEmpty)
          Container(width: double.infinity, padding: const EdgeInsets.all(14), decoration: BoxDecoration(color: RebelionColors.deepSpace, borderRadius: BorderRadius.circular(18)), child: const Text('Identidade em branco: adicione interesses, status e estilo para ser reconhecido do seu jeito.', style: TextStyle(color: RebelionColors.mist, height: 1.35)))
        else
          InterestWrap(selected: interests, onToggle: (_) {}),
      ]),
    );
  }
}

class RebelionSectionTile extends StatelessWidget {
  const RebelionSectionTile({super.key, required this.icon, required this.title, required this.body});
  final IconData icon;
  final String title;
  final String body;

  @override
  Widget build(BuildContext context) {
    return RebelionCard(
      radius: 22,
      child: Row(children: [AvatarIcon(icon: icon), const SizedBox(width: 12), Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [Text(title, style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w900)), Text(body, style: const TextStyle(color: RebelionColors.mist, height: 1.25))]))]),
    );
  }
}

class EmptyStateCard extends StatelessWidget {
  const EmptyStateCard({super.key, required this.icon, required this.title, required this.body});
  final IconData icon;
  final String title;
  final String body;

  @override
  Widget build(BuildContext context) {
    return RebelionCard(
      child: Column(children: [AvatarIcon(icon: icon, size: 54, iconSize: 30), const SizedBox(height: 12), Text(title, style: const TextStyle(fontSize: 19, fontWeight: FontWeight.w900)), const SizedBox(height: 10), Text(body, textAlign: TextAlign.center, style: const TextStyle(color: RebelionColors.mist, height: 1.35))]),
    );
  }
}

class QuickAction extends StatelessWidget {
  const QuickAction({super.key, required this.icon, required this.label, this.onTap});
  final IconData icon;
  final String label;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onTap,
      borderRadius: BorderRadius.circular(22),
      child: Container(
        height: 102,
        decoration: BoxDecoration(color: RebelionColors.panel, borderRadius: BorderRadius.circular(22), border: Border.all(color: Colors.white.withOpacity(0.08))),
        child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [Icon(icon, size: 30, color: RebelionColors.silver), const SizedBox(height: 10), Text(label, style: const TextStyle(fontWeight: FontWeight.w900))]),
      ),
    );
  }
}

class RebelionButton extends StatelessWidget {
  const RebelionButton({super.key, required this.text, this.onPressed});
  final String text;
  final VoidCallback? onPressed;

  @override
  Widget build(BuildContext context) {
    return SizedBox(width: double.infinity, height: 54, child: FilledButton(style: FilledButton.styleFrom(backgroundColor: RebelionColors.white, foregroundColor: RebelionColors.deepSpace, shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(18))), onPressed: onPressed ?? () {}, child: Text(text, style: const TextStyle(fontWeight: FontWeight.w900, fontSize: 16))));
  }
}

class GhostButton extends StatelessWidget {
  const GhostButton({super.key, required this.text, this.icon, this.onPressed, this.compact = false});
  final String text;
  final IconData? icon;
  final VoidCallback? onPressed;
  final bool compact;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: compact ? null : double.infinity,
      height: compact ? 44 : 54,
      child: OutlinedButton.icon(
        icon: icon == null ? const SizedBox.shrink() : Icon(icon, size: 18),
        label: Text(text, style: const TextStyle(fontWeight: FontWeight.w900)),
        style: OutlinedButton.styleFrom(foregroundColor: RebelionColors.white, side: BorderSide(color: RebelionColors.silver.withOpacity(0.80)), shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(18))),
        onPressed: onPressed ?? () {},
      ),
    );
  }
}

class RebelionInput extends StatelessWidget {
  const RebelionInput({super.key, required this.label, required this.value, required this.onChanged, this.enabled = true});
  final String label;
  final String value;
  final ValueChanged<String> onChanged;
  final bool enabled;

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      initialValue: value,
      enabled: enabled,
      onChanged: onChanged,
      style: const TextStyle(color: RebelionColors.white),
      decoration: InputDecoration(labelText: label, labelStyle: const TextStyle(color: RebelionColors.muted), enabledBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(18), borderSide: BorderSide(color: RebelionColors.silver.withOpacity(0.35))), focusedBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(18), borderSide: const BorderSide(color: RebelionColors.silver))),
    );
  }
}

class InterestWrap extends StatelessWidget {
  const InterestWrap({super.key, required this.selected, required this.onToggle});
  final Set<String> selected;
  final ValueChanged<String> onToggle;

  @override
  Widget build(BuildContext context) => ChoiceWrap(values: interestOptions, selected: selected, onToggle: onToggle);
}

class ChoiceWrap extends StatelessWidget {
  const ChoiceWrap({super.key, required this.values, required this.selected, required this.onToggle});
  final List<String> values;
  final Set<String> selected;
  final ValueChanged<String> onToggle;

  @override
  Widget build(BuildContext context) {
    return Wrap(
      spacing: 8,
      runSpacing: 8,
      children: values.map((value) {
        final isSelected = selected.contains(value);
        return ChoiceChip(
          selected: isSelected,
          label: Text(value),
          selectedColor: RebelionColors.white,
          backgroundColor: RebelionColors.panel,
          labelStyle: TextStyle(color: isSelected ? RebelionColors.deepSpace : RebelionColors.mist, fontWeight: FontWeight.w800),
          onSelected: (_) => onToggle(value),
        );
      }).toList(),
    );
  }
}

class AvatarBubble extends StatelessWidget {
  const AvatarBubble({super.key, required this.label, required this.color, this.size = 48});
  final String label;
  final Color color;
  final double size;

  @override
  Widget build(BuildContext context) {
    final textColor = color.computeLuminance() > 0.55 ? RebelionColors.deepSpace : RebelionColors.white;
    return Container(width: size, height: size, decoration: BoxDecoration(shape: BoxShape.circle, gradient: LinearGradient(colors: [color, RebelionColors.graphite])), child: Center(child: Text(label.toUpperCase(), style: TextStyle(color: textColor, fontSize: size / 2.4, fontWeight: FontWeight.w900))));
  }
}

class AvatarIcon extends StatelessWidget {
  const AvatarIcon({super.key, required this.icon, this.size = 42, this.iconSize = 22});
  final IconData icon;
  final double size;
  final double iconSize;

  @override
  Widget build(BuildContext context) => Container(width: size, height: size, decoration: const BoxDecoration(color: RebelionColors.elevated, shape: BoxShape.circle), child: Icon(icon, size: iconSize, color: RebelionColors.silver));
}

class RebelionMark extends StatelessWidget {
  const RebelionMark({super.key, required this.size});
  final double size;

  @override
  Widget build(BuildContext context) => Container(width: size, height: size, decoration: BoxDecoration(borderRadius: BorderRadius.circular(24), gradient: const LinearGradient(colors: [RebelionColors.graphite, RebelionColors.white])), child: Center(child: Text('R', style: TextStyle(color: RebelionColors.deepSpace, fontSize: size / 1.7, fontWeight: FontWeight.w900))));
}

class MiniPill extends StatelessWidget {
  const MiniPill(this.text, {super.key});
  final String text;
  @override
  Widget build(BuildContext context) => Container(padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6), decoration: BoxDecoration(color: RebelionColors.deepSpace, borderRadius: BorderRadius.circular(99)), child: Text(text, style: const TextStyle(color: RebelionColors.silver, fontWeight: FontWeight.w800, fontSize: 12)));
}

class MiniStylePill extends StatelessWidget {
  const MiniStylePill(this.style, {super.key});
  final String style;
  @override
  Widget build(BuildContext context) => Container(padding: const EdgeInsets.symmetric(horizontal: 9, vertical: 5), decoration: BoxDecoration(color: cardStyleAccent(style).withOpacity(0.16), borderRadius: BorderRadius.circular(99)), child: Text(style, style: TextStyle(color: cardStyleAccent(style), fontWeight: FontWeight.w800, fontSize: 11)));
}

Color cardStyleAccent(String style) => switch (style) {
      'Neon' => const Color(0xFF22D3EE),
      'Aurora' => const Color(0xFFFF5DA2),
      'Minimal' => RebelionColors.white,
      _ => RebelionColors.steel,
    };

List<Color> cardStyleGradient(String style) => switch (style) {
      'Neon' => const [Color(0xFF080A12), Color(0xFF12303A), Color(0xFF22D3EE)],
      'Aurora' => const [Color(0xFF0B0712), Color(0xFF44205E), Color(0xFFFF5DA2)],
      'Minimal' => const [Color(0xFF050609), Color(0xFF1C1D22), Color(0xFFF4F5F7)],
      _ => const [RebelionColors.ink, RebelionColors.graphite, RebelionColors.silver],
    };

String initialOf(String value, {String fallback = 'R'}) {
  final trimmed = value.trim();
  return trimmed.isEmpty ? fallback : trimmed[0];
}

String slug(String value) {
  final cleaned = value.toLowerCase().replaceAll(RegExp(r'[^a-z0-9]'), '');
  return cleaned.isEmpty ? 'usuario' : cleaned;
}

const interestOptions = ['Jogos', 'Anime', 'Música', 'Filmes', 'Séries', 'Tecnologia', 'Humor', 'Arte', 'Estudo', 'Criadores', 'Fandoms', 'Esportes'];
const statusOptions = ['Querendo conversar ✦', 'Criando', 'Jogando', 'Assistindo', 'Estudando', 'Ocupado'];
const cardStyleOptions = ['Mono', 'Neon', 'Aurora', 'Minimal'];

class RebelionColors {
  static const deepSpace = Color(0xFF050609);
  static const ink = Color(0xFF0A0B0F);
  static const panel = Color(0xFF11131A);
  static const elevated = Color(0xFF171A22);
  static const graphite = Color(0xFF252A34);
  static const graphiteLight = Color(0xFF3A404C);
  static const slate = Color(0xFF565D6B);
  static const steel = Color(0xFF7F8794);
  static const glassSelected = Color(0xFF5D536F);
  static const glassGlow = Color(0xFFB8A7D9);
  static const navActive = Color(0xFFF4F0FF);
  static const silver = Color(0xFFD8DBE2);
  static const white = Color(0xFFF4F5F7);
  static const mist = Color(0xFFE1E3E8);
  static const muted = Color(0xFF9197A3);
}

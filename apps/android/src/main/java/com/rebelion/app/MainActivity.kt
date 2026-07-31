package com.rebelion.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RebelionApp() }
    }
}

private enum class AppStage { Welcome, Identity, Interests, Main }
private enum class MainTab(val label: String, val emoji: String) {
    Home("Início", "✦"),
    Conversations("Conversas", "💬"),
    Groups("Grupos", "◎"),
    Explore("Explorar", "⌁"),
    Profile("Perfil", "◉")
}

private data class Conversation(
    val name: String,
    val status: String,
    val lastMessage: String,
    val unread: Int,
    val accent: Color
)

private data class GroupSpace(
    val name: String,
    val members: String,
    val core: String,
    val vibe: String,
    val accent: Color
)

@Composable
private fun RebelionApp() {
    MaterialTheme(
        colorScheme = rebelionColorScheme(),
        typography = MaterialTheme.typography
    ) {
        var stage by remember { mutableStateOf(AppStage.Welcome) }
        var nickname by remember { mutableStateOf("Nox") }
        val selectedInterests = remember { mutableStateListOf<String>() }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = RebelionColors.DeepSpace
        ) {
            when (stage) {
                AppStage.Welcome -> WelcomeScreen(
                    onStart = { stage = AppStage.Identity },
                    onEnterDemo = { stage = AppStage.Main }
                )

                AppStage.Identity -> IdentityScreen(
                    nickname = nickname,
                    onNicknameChange = { nickname = it },
                    onContinue = { stage = AppStage.Interests }
                )

                AppStage.Interests -> InterestsScreen(
                    selectedInterests = selectedInterests,
                    onToggleInterest = { interest ->
                        if (selectedInterests.contains(interest)) selectedInterests.remove(interest)
                        else selectedInterests.add(interest)
                    },
                    onContinue = { stage = AppStage.Main }
                )

                AppStage.Main -> MainExperience(nickname = nickname, interests = selectedInterests)
            }
        }
    }
}

@Composable
private fun WelcomeScreen(onStart: () -> Unit, onEnterDemo: () -> Unit) {
    RebelionGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(Modifier.height(40.dp))
                RebelionMark(size = 76)
                Spacer(Modifier.height(28.dp))
                Text(
                    text = "REBELION",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 42.sp,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Seu espaço. Sua voz. Sua comunidade.",
                    color = RebelionColors.Mist,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(Modifier.height(28.dp))
                HeroCard()
            }

            Column {
                RebelionButton(text = "Criar minha identidade", onClick = onStart)
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onEnterDemo,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, RebelionColors.Cyan.copy(alpha = 0.55f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RebelionColors.Cyan)
                ) {
                    Text("Entrar no protótipo")
                }
                Text(
                    text = "Protótipo visual V1: navegação, perfil, conversas, grupos e experiência base.",
                    color = RebelionColors.Muted,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 14.dp)
                )
            }
        }
    }
}

@Composable
private fun HeroCard() {
    RebelionCard {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(
                text = "Crie um universo social do seu jeito",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Converse, monte grupos, personalize sua presença e transforme salas comuns em espaços vivos.",
                color = RebelionColors.Mist,
                fontSize = 15.sp,
                lineHeight = 21.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MiniPill("DM 1v1")
                MiniPill("Grupos")
                MiniPill("Voz")
            }
        }
    }
}

@Composable
private fun IdentityScreen(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    RebelionGradientBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item { Spacer(Modifier.height(22.dp)) }
            item {
                SectionTitle(
                    title = "Escolha como vão te ver",
                    subtitle = "No Rebelion, seu perfil é parte da sua presença. Comece com um nickname e uma vibe visual."
                )
            }
            item {
                ProfilePreviewCard(nickname = nickname.ifBlank { "SeuNick" })
            }
            item {
                RebelionTextField(
                    value = nickname,
                    onValueChange = onNicknameChange,
                    label = "Nickname"
                )
            }
            item {
                RebelionTextField(
                    value = "@${nickname.lowercase().filter { it.isLetterOrDigit() }.ifBlank { "usuario" }}",
                    onValueChange = {},
                    label = "Nome de usuário único",
                    enabled = false
                )
            }
            item {
                RebelionButton(text = "Continuar", onClick = onContinue)
            }
        }
    }
}

@Composable
private fun InterestsScreen(
    selectedInterests: List<String>,
    onToggleInterest: (String) -> Unit,
    onContinue: () -> Unit
) {
    val interests = listOf(
        "Jogos", "Anime", "Música", "Filmes", "Séries", "Tecnologia",
        "Humor", "Arte", "Estudo", "Criadores", "Fandoms", "Esportes"
    )

    RebelionGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(Modifier.height(26.dp))
                SectionTitle(
                    title = "Monte seu radar social",
                    subtitle = "Esses interesses vão ajudar o Rebelion a sugerir grupos, salas e pessoas com a sua vibe."
                )
                Spacer(Modifier.height(22.dp))
                FlowLikeChips(
                    values = interests,
                    selectedValues = selectedInterests,
                    onToggle = onToggleInterest
                )
            }
            Column {
                Text(
                    text = "Selecionados: ${selectedInterests.size}",
                    color = RebelionColors.Mist,
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(10.dp))
                RebelionButton(text = "Entrar no Rebelion", onClick = onContinue)
            }
        }
    }
}

@Composable
private fun MainExperience(nickname: String, interests: List<String>) {
    var selectedTab by remember { mutableStateOf(MainTab.Home) }

    Scaffold(
        containerColor = RebelionColors.DeepSpace,
        bottomBar = {
            NavigationBar(containerColor = RebelionColors.Panel.copy(alpha = 0.98f)) {
                MainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Text(tab.emoji, fontSize = 18.sp) },
                        label = { Text(tab.label, fontSize = 11.sp) }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(RebelionColors.DeepSpace)
        ) {
            when (selectedTab) {
                MainTab.Home -> HomeScreen(nickname = nickname)
                MainTab.Conversations -> ConversationsScreen()
                MainTab.Groups -> GroupsScreen()
                MainTab.Explore -> ExploreScreen(interests = interests)
                MainTab.Profile -> ProfileScreen(nickname = nickname, interests = interests)
            }
        }
    }
}

@Composable
private fun HomeScreen(nickname: String) {
    RebelionScreen {
        item {
            TopGreeting(nickname = nickname)
        }
        item {
            PinnedCoreCard(
                title = "Núcleo em destaque",
                body = "Hoje: apresente seu talento, mande um áudio modificado ou chame sua turma para criar um grupo."
            )
        }
        item {
            SectionHeader("Movimento agora")
        }
        items(sampleGroups()) { group ->
            GroupCard(group = group)
        }
        item {
            SectionHeader("Ações rápidas")
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                QuickAction("＋", "Criar grupo", Modifier.weight(1f))
                QuickAction("🎙", "Gravar voz", Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ConversationsScreen() {
    RebelionScreen {
        item {
            SectionTitle(
                title = "Conversas diretas",
                subtitle = "Espaço mais limpo para falar 1v1 por texto, áudio normal ou áudio modificado."
            )
        }
        items(sampleConversations()) { conversation ->
            ConversationCard(conversation = conversation)
        }
    }
}

@Composable
private fun GroupsScreen() {
    RebelionScreen {
        item {
            SectionTitle(
                title = "Grupos",
                subtitle = "Crie espaços privados com dono, admins, solicitações e núcleo fixado para todos verem."
            )
        }
        item {
            RebelionButton(text = "Criar novo grupo", onClick = {})
        }
        items(sampleGroups()) { group ->
            GroupCard(group = group)
        }
    }
}

@Composable
private fun ExploreScreen(interests: List<String>) {
    RebelionScreen {
        item {
            SectionTitle(
                title = "Explorar",
                subtitle = "Descoberta futura de comunidades, criadores e servidores por país, idioma e interesse."
            )
        }
        item {
            PinnedCoreCard(
                title = "Comunidades por identidade",
                body = "O Rebelion vai recomendar grupos onde o usuário possa se reconhecer: jogos, fandoms, estudo, arte, música e entretenimento."
            )
        }
        item { SectionHeader("Seus interesses") }
        item {
            if (interests.isEmpty()) {
                Text("Nenhum interesse escolhido ainda.", color = RebelionColors.Muted)
            } else {
                FlowLikeChips(values = interests, selectedValues = interests, onToggle = {})
            }
        }
        item { SectionHeader("Categorias futuras") }
        items(listOf("Games BR", "Anime global", "Criadores iniciantes", "Filmes e séries", "Estudo em grupo")) { item ->
            DiscoveryCard(title = item, subtitle = "Comunidade em preparação para uma próxima fase do protótipo.")
        }
    }
}

@Composable
private fun ProfileScreen(nickname: String, interests: List<String>) {
    RebelionScreen {
        item { ProfilePreviewCard(nickname = nickname.ifBlank { "Nox" }) }
        item {
            RebelionCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Status", color = RebelionColors.Muted, fontSize = 13.sp)
                    Text("Querendo conversar ✦", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        "Bio: criando meu universo, encontrando minha turma e testando a primeira versão do Rebelion.",
                        color = RebelionColors.Mist,
                        lineHeight = 20.sp
                    )
                }
            }
        }
        item { SectionHeader("Interesses") }
        item {
            FlowLikeChips(
                values = if (interests.isEmpty()) listOf("Jogos", "Música", "Fandoms") else interests,
                selectedValues = if (interests.isEmpty()) listOf("Jogos", "Música", "Fandoms") else interests,
                onToggle = {}
            )
        }
        item {
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, RebelionColors.Purple.copy(alpha = 0.8f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("Editar perfil em breve")
            }
        }
    }
}

@Composable
private fun RebelionScreen(content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 22.dp, bottom = 22.dp),
        content = content
    )
}

@Composable
private fun RebelionGradientBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        RebelionColors.DeepSpace,
                        RebelionColors.Panel,
                        Color(0xFF101116)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
private fun TopGreeting(nickname: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Olá, $nickname", color = Color.White, fontWeight = FontWeight.Black, fontSize = 28.sp)
            Text("Seu universo está acordando.", color = RebelionColors.Mist, fontSize = 14.sp)
        }
        AvatarBubble(label = nickname.take(1).uppercase().ifBlank { "R" }, color = RebelionColors.Steel)
    }
}

@Composable
private fun ProfilePreviewCard(nickname: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Panel),
        border = BorderStroke(1.dp, RebelionColors.Silver.copy(alpha = 0.18f))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(98.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(RebelionColors.Ink, RebelionColors.Graphite, RebelionColors.Silver)
                        )
                    )
            )
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarBubble(label = nickname.take(1).uppercase(), color = RebelionColors.Steel, size = 64)
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(nickname, color = Color.White, fontWeight = FontWeight.Black, fontSize = 24.sp)
                    Text("@${nickname.lowercase().filter { it.isLetterOrDigit() }.ifBlank { "usuario" }}", color = RebelionColors.Muted)
                    Text("Querendo conversar ✦", color = RebelionColors.Cyan, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun ConversationCard(conversation: Conversation) {
    RebelionCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AvatarBubble(label = conversation.name.take(1), color = conversation.accent)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(conversation.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(conversation.status, color = RebelionColors.Cyan, fontSize = 12.sp)
                }
                Text(
                    conversation.lastMessage,
                    color = RebelionColors.Mist,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (conversation.unread > 0) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(RebelionColors.Purple),
                    contentAlignment = Alignment.Center
                ) {
                    Text(conversation.unread.toString(), color = RebelionColors.DeepSpace, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun GroupCard(group: GroupSpace) {
    RebelionCard {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarBubble(label = group.name.take(1), color = group.accent)
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(group.name, color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
                    Text("${group.members} membros • ${group.vibe}", color = RebelionColors.Muted, fontSize = 13.sp)
                }
                MiniPill("ativo")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(RebelionColors.DeepSpace)
                    .padding(12.dp)
            ) {
                Text("Núcleo: ${group.core}", color = RebelionColors.Mist, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun PinnedCoreCard(title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Elevated),
        border = BorderStroke(1.dp, RebelionColors.Silver.copy(alpha = 0.18f))
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("✦ $title", color = RebelionColors.Cyan, fontWeight = FontWeight.Bold)
            Text(body, color = Color.White, lineHeight = 21.sp)
        }
    }
}

@Composable
private fun DiscoveryCard(title: String, subtitle: String) {
    RebelionCard {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text(subtitle, color = RebelionColors.Mist, lineHeight = 20.sp)
        }
    }
}

@Composable
private fun QuickAction(symbol: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(92.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Panel)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(symbol, color = RebelionColors.Cyan, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun RebelionCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Panel.copy(alpha = 0.94f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.06f))
    ) {
        Box(modifier = Modifier.padding(16.dp)) { content() }
    }
}

@Composable
private fun RebelionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = RebelionColors.White)
    ) {
        Text(text, color = RebelionColors.DeepSpace, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RebelionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = RebelionColors.Muted,
            focusedLabelColor = RebelionColors.Cyan,
            unfocusedLabelColor = RebelionColors.Muted,
            focusedBorderColor = RebelionColors.Cyan,
            unfocusedBorderColor = RebelionColors.Purple.copy(alpha = 0.45f),
            cursorColor = RebelionColors.Cyan
        )
    )
}

@Composable
private fun FlowLikeChips(values: List<String>, selectedValues: List<String>, onToggle: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        values.chunked(3).forEach { rowValues ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowValues.forEach { value ->
                    val selected = selectedValues.contains(value)
                    FilterChip(
                        selected = selected,
                        onClick = { onToggle(value) },
                        label = { Text(value) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RebelionColors.Purple,
                            selectedLabelColor = RebelionColors.DeepSpace,
                            containerColor = RebelionColors.Panel,
                            labelColor = RebelionColors.Mist
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun AvatarBubble(label: String, color: Color, size: Int = 48) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    listOf(color, RebelionColors.Graphite)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val textColor = if (color.luminance() > 0.55f) RebelionColors.DeepSpace else RebelionColors.White
        Text(label.uppercase(), color = textColor, fontWeight = FontWeight.Black, fontSize = (size / 2.4).sp)
    }
}

@Composable
private fun RebelionMark(size: Int) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(RebelionColors.Graphite, RebelionColors.White))),
        contentAlignment = Alignment.Center
    ) {
        Text("R", color = RebelionColors.DeepSpace, fontWeight = FontWeight.Black, fontSize = (size / 1.7).sp)
    }
}

@Composable
private fun MiniPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(99.dp))
            .background(RebelionColors.DeepSpace)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text, color = RebelionColors.Cyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, color = Color.White, fontWeight = FontWeight.Black, fontSize = 30.sp, lineHeight = 34.sp)
        Text(subtitle, color = RebelionColors.Mist, lineHeight = 21.sp)
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(text, color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
}

private fun sampleConversations() = listOf(
    Conversation("Luna", "online", "Mandei um áudio com efeito robô kkk", 2, RebelionColors.Slate),
    Conversation("Kai", "jogando", "Bora criar o grupo dos criadores hoje?", 0, RebelionColors.Steel),
    Conversation("Mika", "criando", "Fixei a ideia no núcleo do grupo.", 1, RebelionColors.GraphiteLight),
    Conversation("Ravi", "ocupado", "Depois entra na sala de voz.", 0, RebelionColors.WarmGrey)
)

private fun sampleGroups() = listOf(
    GroupSpace(
        name = "Base dos Criadores",
        members = "128",
        core = "Mostre seu dom da semana: arte, música, edição, gameplay ou ideia.",
        vibe = "criativo",
        accent = RebelionColors.Steel
    ),
    GroupSpace(
        name = "Noite de Watch Party",
        members = "54",
        core = "Escolher o próximo filme/série permitido para assistir em chamada.",
        vibe = "entretenimento",
        accent = RebelionColors.GraphiteLight
    ),
    GroupSpace(
        name = "Anime & Fandom BR",
        members = "302",
        core = "Tema do dia: personagens que representam sua personalidade.",
        vibe = "fandom",
        accent = RebelionColors.Slate
    )
)

private object RebelionColors {
    // Tema padrão: neutro, preto/branco/grafite.
    // Cores fortes devem entrar depois como personalização de perfil ou tema do usuário.
    val DeepSpace = Color(0xFF050609)
    val Ink = Color(0xFF0A0B0F)
    val Panel = Color(0xFF11131A)
    val Elevated = Color(0xFF171A22)
    val Graphite = Color(0xFF252A34)
    val GraphiteLight = Color(0xFF3A404C)
    val Slate = Color(0xFF565D6B)
    val Steel = Color(0xFF7F8794)
    val WarmGrey = Color(0xFF6F6B65)
    val Silver = Color(0xFFD8DBE2)
    val White = Color(0xFFF4F5F7)
    val Mist = Color(0xFFE1E3E8)
    val Muted = Color(0xFF9197A3)

    // Aliases temporários para manter os componentes prontos para o futuro sistema de temas.
    val Purple = White
    val Cyan = Silver
}

@Composable
private fun rebelionColorScheme() = androidx.compose.material3.darkColorScheme(
    primary = RebelionColors.White,
    secondary = RebelionColors.Silver,
    background = RebelionColors.DeepSpace,
    surface = RebelionColors.Panel,
    onPrimary = RebelionColors.DeepSpace,
    onSecondary = RebelionColors.DeepSpace,
    onBackground = RebelionColors.White,
    onSurface = RebelionColors.White
)

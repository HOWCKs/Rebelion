package com.rebelion.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
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
private enum class MainTab(val label: String, val icon: ImageVector) {
    Home("Hub", Icons.Filled.Home),
    Conversations("DMs", Icons.Filled.Chat),
    Groups("Espaços", Icons.Filled.Group),
    Explore("Radar", Icons.Filled.Search),
    Profile("Eu", Icons.Filled.Person)
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
        var bio by remember { mutableStateOf("criando meu universo, encontrando minha turma e deixando minha marca no Rebelion.") }
        var status by remember { mutableStateOf("Querendo conversar ✦") }
        var cardStyle by remember { mutableStateOf("Mono") }
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

                AppStage.Main -> MainExperience(
                    nickname = nickname,
                    bio = bio,
                    status = status,
                    cardStyle = cardStyle,
                    interests = selectedInterests,
                    onSaveCard = { newNickname, newBio, newStatus, newStyle, newInterests ->
                        nickname = newNickname.ifBlank { "Nox" }
                        bio = newBio
                        status = newStatus
                        cardStyle = newStyle
                        selectedInterests.clear()
                        selectedInterests.addAll(newInterests)
                    }
                )
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
                    text = "Seu hub. Sua voz. Seus espaços.",
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
                    Text("Entrar")
                }
                Text(
                    text = "Entre, escolha sua presença e comece a construir seus espaços.",
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
                text = "Construa seu universo social",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Crie espaços, inicie laços e personalize como você aparece para sua comunidade.",
                color = RebelionColors.Mist,
                fontSize = 15.sp,
                lineHeight = 21.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MiniPill("DMs")
                MiniPill("Espaços")
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
    val interests = interestOptions()

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
private fun MainExperience(
    nickname: String,
    bio: String,
    status: String,
    cardStyle: String,
    interests: List<String>,
    onSaveCard: (String, String, String, String, List<String>) -> Unit
) {
    var selectedTab by remember { mutableStateOf(MainTab.Home) }
    var editingCard by remember { mutableStateOf(false) }

    if (editingCard) {
        EditCardScreen(
            nickname = nickname,
            bio = bio,
            status = status,
            cardStyle = cardStyle,
            interests = interests,
            onBack = { editingCard = false },
            onSave = { newNickname, newBio, newStatus, newStyle, newInterests ->
                onSaveCard(newNickname, newBio, newStatus, newStyle, newInterests)
                editingCard = false
                selectedTab = MainTab.Profile
            }
        )
        return
    }

    Scaffold(
        containerColor = RebelionColors.DeepSpace,
        bottomBar = {
            RebelionGlassNavBar(
                selectedTab = selectedTab,
                onSelectTab = { selectedTab = it }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(RebelionColors.DeepSpace)
        ) {
            when (selectedTab) {
                MainTab.Home -> HomeScreen(nickname = nickname, onEditCard = { editingCard = true })
                MainTab.Conversations -> ConversationsScreen()
                MainTab.Groups -> GroupsScreen()
                MainTab.Explore -> ExploreScreen(interests = interests)
                MainTab.Profile -> ProfileScreen(
                    nickname = nickname,
                    bio = bio,
                    status = status,
                    cardStyle = cardStyle,
                    interests = interests,
                    onEditCard = { editingCard = true }
                )
            }
        }
    }
}

@Composable
private fun RebelionGlassNavBar(selectedTab: MainTab, onSelectTab: (MainTab) -> Unit) {
    val tabs = MainTab.entries
    val selectedIndex = tabs.indexOf(selectedTab).coerceAtLeast(0)
    val indicatorSize = 64.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .shadow(
                    elevation = 18.dp,
                    shape = RoundedCornerShape(42.dp),
                    ambientColor = RebelionColors.GlassGlow.copy(alpha = 0.22f),
                    spotColor = Color.Black.copy(alpha = 0.65f)
                )
                .clip(RoundedCornerShape(42.dp))
                .background(RebelionColors.Glass.copy(alpha = 0.38f))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.22f),
                            Color.White.copy(alpha = 0.06f),
                            Color.Black.copy(alpha = 0.18f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.42f),
                            Color.White.copy(alpha = 0.12f),
                            RebelionColors.GlassGlow.copy(alpha = 0.28f)
                        )
                    ),
                    shape = RoundedCornerShape(42.dp)
                )
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                val itemWidth = maxWidth / tabs.size
                val targetOffset = (itemWidth * selectedIndex.toFloat()) + ((itemWidth - indicatorSize) / 2)
                val animatedOffset by animateDpAsState(
                    targetValue = targetOffset,
                    animationSpec = tween(durationMillis = 380, easing = FastOutSlowInEasing),
                    label = "glass-nav-indicator"
                )

                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .size(indicatorSize)
                        .shadow(
                            elevation = 12.dp,
                            shape = CircleShape,
                            ambientColor = RebelionColors.GlassGlow.copy(alpha = 0.32f),
                            spotColor = Color.Black.copy(alpha = 0.70f)
                        )
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.26f),
                                    RebelionColors.GlassSelected.copy(alpha = 0.82f),
                                    RebelionColors.GlassSelected.copy(alpha = 0.48f)
                                )
                            )
                        )
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.24f),
                            CircleShape
                        )
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    tabs.forEach { tab ->
                        GlassNavItem(
                            tab = tab,
                            selected = selectedTab == tab,
                            onClick = { onSelectTab(tab) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GlassNavItem(tab: MainTab, selected: Boolean, onClick: () -> Unit) {
    val tint = if (selected) RebelionColors.NavActive else RebelionColors.Mist.copy(alpha = 0.72f)

    Box(
        modifier = Modifier
            .size(58.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            tab.icon,
            contentDescription = tab.label,
            tint = tint,
            modifier = Modifier.size(if (selected) 31.dp else 27.dp)
        )
    }
}

@Composable
private fun HomeScreen(nickname: String, onEditCard: () -> Unit) {
    RebelionScreen {
        item {
            HubHeader(nickname = nickname)
        }
        item {
            CoreNucleusCard()
        }
        item {
            SectionHeader("Mover agora")
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    QuickAction(Icons.Filled.Group, "Criar espaço", Modifier.weight(1f))
                    QuickAction(Icons.Filled.Chat, "Nova DM", Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    QuickAction(Icons.Filled.Mic, "Gravar voz", Modifier.weight(1f))
                    QuickAction(Icons.Filled.Edit, "Personalizar", Modifier.weight(1f), onClick = onEditCard)
                }
            }
        }
        item {
            EmptyStateCard(
                icon = Icons.Filled.Home,
                title = "O pulso está quieto",
                body = "Quando algo acontecer nas suas DMs, espaços ou convites, o Núcleo muda primeiro."
            )
        }
    }
}

@Composable
private fun ConversationsScreen() {
    RebelionScreen {
        item {
            SectionTitle(
                title = "DMs",
                subtitle = "Conexões diretas para conversar por texto, áudio normal ou voz com efeitos."
            )
        }
        item {
            RebelionButton(text = "Iniciar DM", onClick = {})
        }
        item {
            EmptyStateCard(
                icon = Icons.Filled.Chat,
                title = "Nenhum laço iniciado",
                body = "Comece uma DM e crie sua primeira conexão dentro do Rebelion."
            )
        }
    }
}

@Composable
private fun GroupsScreen() {
    RebelionScreen {
        item {
            SectionTitle(
                title = "Espaços",
                subtitle = "Crie lugares privados para sua turma conversar, organizar ideias e manter um núcleo vivo."
            )
        }
        item {
            RebelionButton(text = "Criar espaço", onClick = {})
        }
        item {
            EmptyStateCard(
                icon = Icons.Filled.Group,
                title = "Nenhum espaço criado",
                body = "Crie um lugar para sua turma existir do jeito de vocês."
            )
        }
    }
}

@Composable
private fun ExploreScreen(interests: List<String>) {
    RebelionScreen {
        item {
            SectionTitle(
                title = "Radar",
                subtitle = "Encontre espaços, pessoas e comunidades próximas dos seus interesses."
            )
        }
        if (interests.isNotEmpty()) {
            item { SectionHeader("Sinais do seu radar") }
            item {
                FlowLikeChips(values = interests, selectedValues = interests, onToggle = {})
            }
        }
        item {
            EmptyStateCard(
                icon = Icons.Filled.Search,
                title = "Nada no radar ainda",
                body = "Quando comunidades públicas surgirem, elas aparecem aqui para descoberta."
            )
        }
    }
}

@Composable
private fun ProfileScreen(nickname: String, bio: String, status: String, cardStyle: String, interests: List<String>, onEditCard: () -> Unit) {
    RebelionScreen {
        item {
            SectionTitle(
                title = "Eu",
                subtitle = "Seu Cartão Rebelion reúne presença, identidade, estilo e controle."
            )
        }
        item { ProfilePreviewCard(nickname = nickname.ifBlank { "Nox" }, status = status, cardStyle = cardStyle) }
        item {
            RebelionIdentityCard(bio = bio, status = status, cardStyle = cardStyle, interests = interests)
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                RebelionSectionTile(
                    icon = Icons.Filled.Person,
                    title = "Presença",
                    body = "Status, bio e como você quer ser encontrado."
                )
                RebelionSectionTile(
                    icon = Icons.Filled.Edit,
                    title = "Identidade",
                    body = "Nickname, interesses e marcas que definem seu perfil."
                )
                RebelionSectionTile(
                    icon = Icons.Filled.Home,
                    title = "Estilo",
                    body = "Banner, avatar, cores e aparência pública."
                )
                RebelionSectionTile(
                    icon = Icons.Filled.Search,
                    title = "Controle",
                    body = "Privacidade, DMs e visibilidade dentro dos espaços."
                )
            }
        }
        item {
            OutlinedButton(
                onClick = onEditCard,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, RebelionColors.Silver.copy(alpha = 0.8f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Editar Cartão")
            }
        }
    }
}

@Composable
private fun EditCardScreen(
    nickname: String,
    bio: String,
    status: String,
    cardStyle: String,
    interests: List<String>,
    onBack: () -> Unit,
    onSave: (String, String, String, String, List<String>) -> Unit
) {
    var draftNickname by remember { mutableStateOf(nickname) }
    var draftBio by remember { mutableStateOf(bio) }
    var draftStatus by remember { mutableStateOf(status) }
    val draftInterests = remember { mutableStateListOf<String>().apply { addAll(interests) } }
    var selectedStyle by remember { mutableStateOf(cardStyle) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(RebelionColors.DeepSpace)
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 22.dp, bottom = 28.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Editar Cartão", color = Color.White, fontWeight = FontWeight.Black, fontSize = 30.sp)
                    Text("Ajuste como sua presença aparece no Rebelion.", color = RebelionColors.Mist, lineHeight = 20.sp)
                }
                OutlinedButton(
                    onClick = onBack,
                    border = BorderStroke(1.dp, RebelionColors.Silver.copy(alpha = 0.45f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("Voltar")
                }
            }
        }
        item {
            ProfilePreviewCard(nickname = draftNickname.ifBlank { "Nox" }, status = draftStatus, cardStyle = selectedStyle)
        }
        item {
            RebelionCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Identidade", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    RebelionTextField(value = draftNickname, onValueChange = { draftNickname = it }, label = "Nickname")
                    RebelionTextField(value = draftBio, onValueChange = { draftBio = it }, label = "Bio")
                }
            }
        }
        item {
            RebelionCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Presença", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    FlowLikeChips(
                        values = statusOptions(),
                        selectedValues = listOf(draftStatus),
                        onToggle = { draftStatus = it }
                    )
                }
            }
        }
        item {
            RebelionCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Interesses", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    FlowLikeChips(
                        values = interestOptions(),
                        selectedValues = draftInterests,
                        onToggle = { interest ->
                            if (draftInterests.contains(interest)) draftInterests.remove(interest)
                            else draftInterests.add(interest)
                        }
                    )
                }
            }
        }
        item {
            RebelionCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Estilo do cartão", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    Text("O estilo muda o banner, avatar e destaque público do seu Cartão Rebelion.", color = RebelionColors.Mist, lineHeight = 20.sp)
                    FlowLikeChips(
                        values = listOf("Mono", "Neon", "Aurora", "Minimal"),
                        selectedValues = listOf(selectedStyle),
                        onToggle = { selectedStyle = it }
                    )
                }
            }
        }
        item {
            RebelionButton(
                text = "Salvar Cartão",
                onClick = { onSave(draftNickname, draftBio, draftStatus, selectedStyle, draftInterests.toList()) }
            )
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
private fun HubHeader(nickname: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Hub", color = Color.White, fontWeight = FontWeight.Black, fontSize = 32.sp)
            Text("$nickname, escolha o que quer mover agora.", color = RebelionColors.Mist, fontSize = 14.sp)
        }
        AvatarBubble(label = nickname.take(1).uppercase().ifBlank { "R" }, color = RebelionColors.Steel)
    }
}

@Composable
private fun CoreNucleusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Elevated),
        border = BorderStroke(1.dp, RebelionColors.Silver.copy(alpha = 0.18f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Núcleo", color = RebelionColors.Muted, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .size(148.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                RebelionColors.GraphiteLight,
                                RebelionColors.Graphite,
                                RebelionColors.Panel
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(112.dp)
                        .clip(CircleShape)
                        .background(RebelionColors.DeepSpace),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("QUIETO", color = RebelionColors.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                        Text("agora", color = RebelionColors.Muted, fontSize = 12.sp)
                    }
                }
            }
            Text(
                "Seu Núcleo mostra presença, movimento e sinais importantes dos seus espaços.",
                color = RebelionColors.Mist,
                lineHeight = 20.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                NucleusMetric("DMs", "0", Modifier.weight(1f))
                NucleusMetric("Espaços", "0", Modifier.weight(1f))
                NucleusMetric("Convites", "0", Modifier.weight(1f))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(RebelionColors.Panel)
                    .padding(12.dp)
            ) {
                Text("Presença: querendo conversar ✦", color = RebelionColors.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun NucleusMetric(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(RebelionColors.Panel)
            .padding(horizontal = 10.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, color = RebelionColors.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
            Text(label, color = RebelionColors.Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun RebelionIdentityCard(bio: String, status: String, cardStyle: String, interests: List<String>) {
    RebelionCard {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(RebelionColors.Elevated),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = RebelionColors.Silver)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Cartão Rebelion", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    Text("Sua identidade pública começa aqui.", color = RebelionColors.Muted, fontSize = 13.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(status, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                MiniStylePill(cardStyle)
            }
            Text(
                "Bio: $bio",
                color = RebelionColors.Mist,
                lineHeight = 20.sp
            )
            if (interests.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(RebelionColors.DeepSpace)
                        .padding(12.dp)
                ) {
                    Text("Identidade em branco: adicione interesses, status e estilo para ser reconhecido do seu jeito.", color = RebelionColors.Mist, lineHeight = 20.sp)
                }
            } else {
                FlowLikeChips(values = interests, selectedValues = interests, onToggle = {})
            }
        }
    }
}

@Composable
private fun RebelionSectionTile(icon: ImageVector, title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Panel),
        border = BorderStroke(1.dp, RebelionColors.Silver.copy(alpha = 0.08f))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(RebelionColors.Elevated),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = RebelionColors.Silver, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
                Text(body, color = RebelionColors.Mist, lineHeight = 18.sp, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun MiniStylePill(style: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(99.dp))
            .background(cardStyleAccent(style).copy(alpha = 0.16f))
            .padding(horizontal = 9.dp, vertical = 5.dp)
    ) {
        Text(style, color = cardStyleAccent(style), fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ProfilePreviewCard(nickname: String, status: String = "Querendo conversar ✦", cardStyle: String = "Mono") {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Panel),
        border = BorderStroke(1.dp, cardStyleAccent(cardStyle).copy(alpha = 0.38f))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(98.dp)
                    .background(
                        Brush.horizontalGradient(cardStyleGradient(cardStyle))
                    )
            )
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarBubble(label = nickname.take(1).uppercase(), color = cardStyleAccent(cardStyle), size = 64)
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(nickname, color = Color.White, fontWeight = FontWeight.Black, fontSize = 24.sp)
                    Text("@${nickname.lowercase().filter { it.isLetterOrDigit() }.ifBlank { "usuario" }}", color = RebelionColors.Muted)
                    Text(status, color = cardStyleAccent(cardStyle), fontSize = 13.sp)
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
private fun EmptyStateCard(icon: ImageVector, title: String, body: String) {
    RebelionCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(RebelionColors.Elevated),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = RebelionColors.Silver, modifier = Modifier.size(28.dp))
            }
            Text(title, color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
            Text(body, color = RebelionColors.Mist, lineHeight = 20.sp)
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
private fun QuickAction(icon: ImageVector, label: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier.height(92.dp).clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = RebelionColors.Panel),
        border = BorderStroke(1.dp, RebelionColors.Silver.copy(alpha = 0.08f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = RebelionColors.Silver, modifier = Modifier.size(28.dp))
            Spacer(Modifier.height(8.dp))
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

private fun cardStyleAccent(style: String): Color = when (style) {
    "Neon" -> Color(0xFF22D3EE)
    "Aurora" -> Color(0xFFFF5DA2)
    "Minimal" -> Color(0xFFF4F5F7)
    else -> RebelionColors.Steel
}

private fun cardStyleGradient(style: String): List<Color> = when (style) {
    "Neon" -> listOf(Color(0xFF080A12), Color(0xFF12303A), Color(0xFF22D3EE))
    "Aurora" -> listOf(Color(0xFF0B0712), Color(0xFF44205E), Color(0xFFFF5DA2))
    "Minimal" -> listOf(Color(0xFF050609), Color(0xFF1C1D22), Color(0xFFF4F5F7))
    else -> listOf(RebelionColors.Ink, RebelionColors.Graphite, RebelionColors.Silver)
}

private fun interestOptions() = listOf(
    "Jogos", "Anime", "Música", "Filmes", "Séries", "Tecnologia",
    "Humor", "Arte", "Estudo", "Criadores", "Fandoms", "Esportes"
)

private fun statusOptions() = listOf(
    "Querendo conversar ✦", "Criando", "Jogando", "Assistindo", "Estudando", "Ocupado"
)

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
    val Glass = Color(0xFF24212D)
    val GlassSelected = Color(0xFF5D536F)
    val GlassGlow = Color(0xFFB8A7D9)
    val NavActive = Color(0xFFF4F0FF)
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

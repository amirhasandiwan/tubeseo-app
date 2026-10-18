package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VideoNiche
import com.example.model.VideoTone
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDark2
import com.example.ui.theme.SurfaceDark3
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YouTubeRed
import com.example.ui.theme.YouTubeRedDark
import com.example.ui.viewmodel.TubeSeoViewModel
import com.example.ui.viewmodel.UiState

data class PromptPreset(
    val title: String,
    val topic: String,
    val niche: VideoNiche,
    val tone: VideoTone,
    val audience: String,
    val duration: String
)

@Composable
fun GeneratorScreen(
    uiState: UiState,
    viewModel: TubeSeoViewModel,
    modifier: Modifier = Modifier
) {
    val presets = listOf(
        PromptPreset(
            title = "10 AI Tools",
            topic = "10 AI Tools That Feel Illegal to Know in 2026",
            niche = VideoNiche.TECH,
            tone = VideoTone.HIGH_CTR,
            audience = "Entrepreneurs & Tech Enthusiasts",
            duration = "12-15 mins"
        ),
        PromptPreset(
            title = "100-Day Game Dev",
            topic = "I Made a Video Game in 100 Days with Zero Experience",
            niche = VideoNiche.GAMING,
            tone = VideoTone.STORYTELLING,
            audience = "Gamers & Indie Developers",
            duration = "18-22 mins"
        ),
        PromptPreset(
            title = "Side Hustle Guide",
            topic = "How to Build a $10,000/Month Passive Income System",
            niche = VideoNiche.FINANCE,
            tone = VideoTone.AUTHORITATIVE,
            audience = "Beginners & Freelancers",
            duration = "15 mins"
        ),
        PromptPreset(
            title = "30-Day Fitness",
            topic = "What Actually Happens When You Do 100 Pushups Every Day",
            niche = VideoNiche.FITNESS,
            tone = VideoTone.HIGH_CTR,
            audience = "Fitness Beginners & Men's Health",
            duration = "8-10 mins"
        ),
        PromptPreset(
            title = "Why React is Dying",
            topic = "Why Everyone is Leaving React in 2026 (The Brutal Truth)",
            niche = VideoNiche.TECH,
            tone = VideoTone.TREND_JACKING,
            audience = "Software Engineers & Tech Leads",
            duration = "14 mins"
        )
    )

    val durationOptions = listOf("5 mins", "8-10 mins", "12-15 mins", "20 mins", "30+ mins")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("generator_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                YouTubeRedDark.copy(alpha = 0.5f),
                                SurfaceDark2
                            )
                        )
                    )
                    .border(1.dp, YouTubeRed.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(YouTubeRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ElectricBolt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "YOUTUBE SEO SPECIALIST",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = AccentCyan,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Turn Any Video Idea Into a Viral YouTube Package",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Generates high-CTR titles, algorithm-friendly descriptions, 500-char tags, chapters, and hashtags backed by Gemini 3.5 AI.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }

        // Quick Inspiration Chips
        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = AccentAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Quick Inspiration Presets",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(presets) { preset ->
                        val isSelected = uiState.topic == preset.topic
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) YouTubeRed.copy(alpha = 0.2f) else SurfaceDark2)
                                .border(
                                    1.dp,
                                    if (isSelected) YouTubeRed else SurfaceDarkBorder,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    viewModel.setQuickPrompt(
                                        preset.topic,
                                        preset.niche,
                                        preset.tone,
                                        preset.audience,
                                        preset.duration
                                    )
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("preset_${preset.title.replace(" ", "_")}")
                        ) {
                            Text(
                                text = "${preset.niche.icon} ${preset.title}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) YouTubeRed else TextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Video Topic Input
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Video Topic or Working Title *",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${uiState.topic.length} chars",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = uiState.topic,
                    onValueChange = { viewModel.onTopicChange(it) },
                    placeholder = {
                        Text(
                            "e.g., How to Build an AI Agent in 2026 for Beginners",
                            color = TextSecondary.copy(alpha = 0.6f)
                        )
                    },
                    trailingIcon = {
                        if (uiState.topic.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onTopicChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = TextSecondary
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_topic"),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceDark2,
                        unfocusedContainerColor = SurfaceDark1,
                        focusedBorderColor = YouTubeRed,
                        unfocusedBorderColor = SurfaceDarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    minLines = 2,
                    maxLines = 4
                )
            }
        }

        // Niche Selector
        item {
            Column {
                Text(
                    text = "Content Category / Niche",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(VideoNiche.values()) { niche ->
                        val isSelected = uiState.selectedNiche == niche
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) AccentCyan.copy(alpha = 0.15f) else SurfaceDark2)
                                .border(
                                    1.dp,
                                    if (isSelected) AccentCyan else SurfaceDarkBorder,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { viewModel.onNicheChange(niche) }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .testTag("niche_${niche.name}")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = niche.icon, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = niche.displayName,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) AccentCyan else TextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Tone & Style
        item {
            Column {
                Text(
                    text = "Packaging Tone & Angle",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    VideoTone.values().forEach { tone ->
                        val isSelected = uiState.selectedTone == tone
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) SurfaceDark3 else SurfaceDark1)
                                .border(
                                    1.dp,
                                    if (isSelected) YouTubeRed else SurfaceDarkBorder,
                                    RoundedCornerShape(14.dp)
                                )
                                .clickable { viewModel.onToneChange(tone) }
                                .padding(12.dp)
                                .testTag("tone_${tone.name}")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .border(
                                            2.dp,
                                            if (isSelected) YouTubeRed else TextSecondary,
                                            CircleShape
                                        )
                                        .background(if (isSelected) YouTubeRed else Color.Transparent)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = tone.displayName,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) TextPrimary else TextSecondary
                                    )
                                    Text(
                                        text = tone.desc,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Estimated Duration
        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = AccentCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Estimated Video Length (For Chapters)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    durationOptions.forEach { dur ->
                        val isSelected = uiState.estimatedDuration == dur
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) YouTubeRed.copy(alpha = 0.2f) else SurfaceDark2)
                                .border(
                                    1.dp,
                                    if (isSelected) YouTubeRed else SurfaceDarkBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { viewModel.onDurationChange(dur) }
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = dur,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) YouTubeRed else TextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Target Audience & Extra Outline
        item {
            Column {
                Text(
                    text = "Target Audience",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.targetAudience,
                    onValueChange = { viewModel.onAudienceChange(it) },
                    placeholder = { Text("e.g. Intermediate programmers, college students") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_audience"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceDark2,
                        unfocusedContainerColor = SurfaceDark1,
                        focusedBorderColor = AccentCyan,
                        unfocusedBorderColor = SurfaceDarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Key Points / Outline (Optional)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.additionalNotes,
                    onValueChange = { viewModel.onNotesChange(it) },
                    placeholder = { Text("List any tools, steps, or secrets to include in chapters") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_notes"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceDark2,
                        unfocusedContainerColor = SurfaceDark1,
                        focusedBorderColor = AccentCyan,
                        unfocusedBorderColor = SurfaceDarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    minLines = 2
                )
            }
        }

        // Generate Button or Loading State
        item {
            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.isGenerating) {
                val infiniteTransition = rememberInfiniteTransition(label = "generating")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "rotation"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceDark2)
                        .border(1.dp, YouTubeRed.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                color = YouTubeRed,
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(54.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = AccentCyan,
                                modifier = Modifier
                                    .size(24.dp)
                                    .rotate(rotation)
                            )
                        }

                        Text(
                            text = "AI SEO Specialist at Work...",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Text(
                            text = "Crafting high-CTR curiosity hooks, 500-char tags, and timestamp chapters for '${uiState.topic.take(35)}...'",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                Button(
                    onClick = { viewModel.generateMetadata() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("btn_generate_metadata"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YouTubeRed,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "GENERATE YOUTUBE METADATA",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

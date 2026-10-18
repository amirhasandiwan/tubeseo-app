package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CopyUtil
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.SurfaceDark1
import com.example.ui.theme.SurfaceDark2
import com.example.ui.theme.SurfaceDark3
import com.example.ui.theme.SurfaceDarkBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YouTubeRed
import com.example.ui.viewmodel.TubeSeoViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun SeoToolsScreen(
    uiState: UiState,
    viewModel: TubeSeoViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Checklist state
    var checkedItems by remember {
        mutableStateOf(setOf(0, 1, 2, 3))
    }

    val checklist = listOf(
        "Focus keyword placed in the first 60 characters of Title",
        "Thumbnail has bold text (3-4 words max) with extreme color contrast",
        "First 125 characters of Description state the core hook and value",
        "Video has at least 5 structured Key Moment chapters with 00:00 start",
        "YouTube Studio tags are under the 500-character limit (350-450 optimal)",
        "3-5 targeted hashtags added (#niche #topic #angle)",
        "First 15-30 seconds of video immediately answers/previews the promise",
        "Clear call to action: Pinned comment with link + question to viewers"
    )

    // Tag Tool logic
    val rawTagInput = uiState.tagToolInput
    val tagCharCount = rawTagInput.length
    val isTagOver = tagCharCount > 500
    val tagProgress = (tagCharCount / 500f).coerceIn(0f, 1f)
    val parsedTagsCount = rawTagInput.split(",").map { it.trim() }.filter { it.isNotBlank() }.size

    // Title Analyzer logic
    val titleInput = uiState.titleToolInput
    val titleLen = titleInput.length
    val powerWords = listOf("secret", "proven", "illegal", "stop", "mistake", "truth", "insane", "how to", "ultimate", "never", "tested", "ruining", "warning", "revealed", "easy", "step-by-step")
    val hasPowerWord = powerWords.any { titleInput.contains(it, ignoreCase = true) }
    val hasNumbers = titleInput.any { it.isDigit() }
    val hasBrackets = titleInput.contains("[") || titleInput.contains("]") || titleInput.contains("(") || titleInput.contains(")")
    val isLenOptimal = titleLen in 45..70

    var calculatedScore = 50
    if (isLenOptimal) calculatedScore += 20 else if (titleLen in 30..80) calculatedScore += 10
    if (hasPowerWord) calculatedScore += 15
    if (hasNumbers) calculatedScore += 10
    if (hasBrackets) calculatedScore += 5
    calculatedScore = calculatedScore.coerceIn(0, 100)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("seo_tools_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TOOL 1: Title CTR & Power Word Analyzer
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark1)
                    .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(YouTubeRed.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Speed,
                                    contentDescription = null,
                                    tint = YouTubeRed,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Title CTR & Hook Analyzer",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Algorithm Sweet Spot & Power Word Check",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }

                        // Score badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (calculatedScore >= 80) AccentGreen.copy(alpha = 0.15f)
                                    else if (calculatedScore >= 60) AccentCyan.copy(alpha = 0.15f)
                                    else AccentAmber.copy(alpha = 0.15f)
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "$calculatedScore% CTR",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black,
                                color = if (calculatedScore >= 80) AccentGreen else if (calculatedScore >= 60) AccentCyan else AccentAmber
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { viewModel.onTitleToolInputChange(it) },
                        placeholder = { Text("Type or paste a video title to score...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_title_analyzer"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark2,
                            unfocusedContainerColor = SurfaceDark2,
                            focusedBorderColor = YouTubeRed,
                            unfocusedBorderColor = SurfaceDarkBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Insights Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricPill(
                            label = "Length ($titleLen)",
                            isGood = isLenOptimal,
                            detail = if (isLenOptimal) "Optimal (50-70)" else "Aim 50-70"
                        )
                        MetricPill(
                            label = "Power Word",
                            isGood = hasPowerWord,
                            detail = if (hasPowerWord) "Found" else "None"
                        )
                        MetricPill(
                            label = "Numbers",
                            isGood = hasNumbers,
                            detail = if (hasNumbers) "Present" else "Add listicle"
                        )
                        MetricPill(
                            label = "Brackets",
                            isGood = hasBrackets,
                            detail = if (hasBrackets) "Present" else "Add [...]"
                        )
                    }
                }
            }
        }

        // TOOL 2: YouTube Tag Counter & Formatter
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark1)
                    .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(AccentCyan.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Tag,
                                    contentDescription = null,
                                    tint = AccentCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "500-Character Tag Validator",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "$parsedTagsCount tags • $tagCharCount / 500 characters",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isTagOver) Color(0xFFEF5350) else AccentGreen
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { tagProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = if (isTagOver) Color(0xFFEF5350) else AccentGreen,
                        trackColor = SurfaceDark2
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = rawTagInput,
                        onValueChange = { viewModel.onTagToolInputChange(it) },
                        placeholder = { Text("Paste comma-separated tags to check character limit...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_tag_analyzer"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark2,
                            unfocusedContainerColor = SurfaceDark2,
                            focusedBorderColor = AccentCyan,
                            unfocusedBorderColor = SurfaceDarkBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        minLines = 3,
                        maxLines = 6
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                // Deduplicate & trim tags
                                val cleaned = rawTagInput.split(",")
                                    .map { it.trim() }
                                    .filter { it.isNotBlank() }
                                    .distinct()
                                    .joinToString(", ")
                                viewModel.onTagToolInputChange(cleaned)
                                viewModel.showSnackbar("Deduplicated & formatted tags! ✨")
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark2)
                        ) {
                            Text("Deduplicate", style = MaterialTheme.typography.labelSmall)
                        }

                        Button(
                            onClick = {
                                CopyUtil.copyToClipboard(context, "Tags", rawTagInput)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentCyan.copy(alpha = 0.2f),
                                contentColor = AccentCyan
                            )
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Copy CSV", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // TOOL 3: 2026 YouTube SEO Pre-Publish Checklist
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark1)
                    .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(AccentGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AccentGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Pre-Publish YouTube SEO Checklist",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "${checkedItems.size} of ${checklist.size} completed",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    checklist.forEachIndexed { index, itemText ->
                        val isChecked = checkedItems.contains(index)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    checkedItems = if (isChecked) checkedItems - index else checkedItems + index
                                }
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(
                                        1.5.dp,
                                        if (isChecked) AccentGreen else TextSecondary,
                                        RoundedCornerShape(6.dp)
                                    )
                                    .background(if (isChecked) AccentGreen else Color.Transparent),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isChecked) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = itemText,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isChecked) TextPrimary else TextSecondary,
                                fontWeight = if (isChecked) FontWeight.Medium else FontWeight.Normal
                            )
                        }
                        if (index < checklist.lastIndex) {
                            HorizontalDivider(color = SurfaceDarkBorder.copy(alpha = 0.4f), modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                }
            }
        }

        // TOOL 4: Gemini AI Engine & API Key Setup
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark1)
                    .border(1.dp, SurfaceDarkBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = null,
                            tint = AccentAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Gemini AI Engine Settings",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Powered by Google's latest Gemini 3.5 Flash model for structured JSON SEO generation. Automatically uses your AI Studio environment secret (GEMINI_API_KEY).",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = uiState.customApiKey,
                        onValueChange = { viewModel.onCustomApiKeyChange(it) },
                        placeholder = { Text("Override with custom Gemini API Key (optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_custom_api_key"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark2,
                            unfocusedContainerColor = SurfaceDark2,
                            focusedBorderColor = AccentAmber,
                            unfocusedBorderColor = SurfaceDarkBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (viewModel.hasApiKeyConfigured()) "✓ Active Gemini API connection detected" else "💡 Note: Configure GEMINI_API_KEY in Secrets panel or enter above",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (viewModel.hasApiKeyConfigured()) AccentGreen else AccentAmber
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MetricPill(
    label: String,
    isGood: Boolean,
    detail: String
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceDark2)
            .border(1.dp, if (isGood) AccentGreen.copy(alpha = 0.4f) else SurfaceDarkBorder, RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (isGood) AccentGreen else TextSecondary,
            fontSize = 10.sp
        )
        Text(
            text = detail,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            fontSize = 9.sp
        )
    }
}

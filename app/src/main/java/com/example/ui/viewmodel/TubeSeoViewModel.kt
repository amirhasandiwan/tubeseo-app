package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.database.TubeSeoDatabase
import com.example.data.entity.SeoProject
import com.example.data.repository.SeoRepository
import com.example.model.GenerationParams
import com.example.model.VideoNiche
import com.example.model.VideoTone
import com.example.model.YouTubeMetadata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainNavigationTab {
    GENERATOR,
    RESULTS,
    SAVED,
    TOOLS
}

data class UiState(
    val topic: String = "",
    val selectedNiche: VideoNiche = VideoNiche.TECH,
    val selectedTone: VideoTone = VideoTone.HIGH_CTR,
    val targetAudience: String = "Aspiring Creators & Tech Enthusiasts",
    val estimatedDuration: String = "10 - 15 mins",
    val additionalNotes: String = "",
    val isGenerating: Boolean = false,
    val currentMetadata: YouTubeMetadata? = null,
    val isCurrentSaved: Boolean = false,
    val activeTab: MainNavigationTab = MainNavigationTab.GENERATOR,
    val selectedTitleIndex: Int = 0,
    val customApiKey: String = "",
    val snackbarMessage: String? = null,
    val tagToolInput: String = "",
    val titleToolInput: String = ""
)

class TubeSeoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = TubeSeoDatabase.getDatabase(application)
    private val repository = SeoRepository(database.seoProjectDao())

    val savedProjects: StateFlow<List<SeoProject>> = repository.allProjects
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Pre-populate with default smart sample so user can immediately explore results
        val defaultParams = GenerationParams(
            topic = "10 AI Tools That Feel Illegal to Know in 2026",
            niche = VideoNiche.TECH,
            tone = VideoTone.HIGH_CTR,
            targetAudience = "Content Creators & Entrepreneurs",
            estimatedDuration = "12 mins",
            additionalNotes = "Cover real workflow automations, video editing AI, and free alternatives"
        )
        val defaultSample = repository.parseProjectToMetadata(
            SeoProject(
                topic = defaultParams.topic,
                niche = defaultParams.niche.displayName,
                tone = defaultParams.tone.displayName,
                targetAudience = defaultParams.targetAudience,
                videoDuration = defaultParams.estimatedDuration,
                titlesJson = """[
                    "10 AI Tools That Feel ILLEGAL To Know in 2026",
                    "Stop Paying for Software: 10 AI Tools You Need Now",
                    "How to Automate 90% of Your Work with AI (Step-by-Step)",
                    "I Tested 50 AI Tools: Only These 10 Are Actually Useful",
                    "The Dark Truth About AI Productivity Tools in 2026",
                    "10 INSANE AI Websites You Wish You Found Earlier",
                    "Why Top Creators Don't Want You to Know These 10 AI Tools",
                    "Top 10 Free AI Tools Everyone Should Use in 2026"
                ]""",
                description = """
Stop wasting hours on repetitive tasks. In this video, we break down 10 unbelievable AI tools in 2026 that feel genuinely unfair to use—from autonomous video editors and research assistants to automated coding engines.

We test each tool live, show realistic pros & cons, and reveal the free tiers you can start using today.

⏱️ TIMESTAMPS:
00:00 - The AI Revolution You're Missing
01:10 - Tool #1: Instant Video B-Roll Generator
02:45 - Tool #2: Research Paper & PDF Synthesizer
04:30 - Tool #3: Voice Cloning & Audio Enhancer
06:15 - Tool #4: Automated Workflow Agent
08:00 - Tool #5 & #6: Visual Design & Thumbnail AI
10:15 - Tool #7 & #8: Code & Script Autopilot
12:30 - Tool #9 & #10: Autonomous Social Growth Engines
14:15 - How to Stack These Tools for Maximum Output

🔗 RESOURCES MENTIONED:
- All tool links & cheat sheet: https://tubeseo.ai/tools-2026
- Free AI prompt swipe file: https://tubeseo.ai/prompts

🔔 Subscribe for weekly cutting-edge creator tools and YouTube growth breakdowns!
💬 Which tool blew your mind the most? Drop your favorite in the comments!

#AITools #ArtificialIntelligence #Tech2026 #ProductivityHacks #Automation
                """.trimIndent(),
                tags = "ai tools 2026, artificial intelligence, best ai tools, productivity ai, free ai software, video editing ai, workflow automation, chatgpt alternatives, tech review 2026, content creation tools",
                hashtagsJson = """["#AITools", "#TechTrends", "#ArtificialIntelligence", "#Productivity", "#CreatorEconomy", "#Automation"]""",
                chaptersJson = """[
                    {"time": "00:00", "title": "The AI Revolution You're Missing"},
                    {"time": "01:10", "title": "Tool #1: Instant Video B-Roll Generator"},
                    {"time": "02:45", "title": "Tool #2: Research Paper & PDF Synthesizer"},
                    {"time": "04:30", "title": "Tool #3: Voice Cloning & Audio Enhancer"},
                    {"time": "06:15", "title": "Tool #4: Automated Workflow Agent"},
                    {"time": "08:00", "title": "Tool #5: Visual Design & Thumbnail AI"},
                    {"time": "10:15", "title": "Tool #7: Code & Script Autopilot"},
                    {"time": "12:30", "title": "Tool #9: Autonomous Social Growth Engine"},
                    {"time": "14:15", "title": "How to Stack These for Maximum Output"}
                ]""",
                seoScore = 97,
                strategyTipsJson = """[
                    "Thumbnail Tip: Hold up a glowing smartphone with bright yellow text 'FEELS ILLEGAL' and a red arrow.",
                    "15-Sec Hook: Immediately demo the most impressive tool output before greeting viewers.",
                    "Search Intent: Target the exact query 'best ai tools 2026' in both the title and the first line of the description."
                ]""",
                thumbnailHookText = "FEELS ILLEGAL!",
                createdAt = System.currentTimeMillis() - 86400000L,
                isFavorite = true
            )
        )
        _uiState.value = _uiState.value.copy(
            topic = defaultParams.topic,
            selectedNiche = defaultParams.niche,
            selectedTone = defaultParams.tone,
            targetAudience = defaultParams.targetAudience,
            estimatedDuration = defaultParams.estimatedDuration,
            currentMetadata = defaultSample,
            tagToolInput = defaultSample.tags,
            titleToolInput = defaultSample.titles.firstOrNull() ?: ""
        )
    }

    fun onTopicChange(newTopic: String) {
        _uiState.value = _uiState.value.copy(topic = newTopic)
    }

    fun onNicheChange(niche: VideoNiche) {
        _uiState.value = _uiState.value.copy(selectedNiche = niche)
    }

    fun onToneChange(tone: VideoTone) {
        _uiState.value = _uiState.value.copy(selectedTone = tone)
    }

    fun onAudienceChange(audience: String) {
        _uiState.value = _uiState.value.copy(targetAudience = audience)
    }

    fun onDurationChange(duration: String) {
        _uiState.value = _uiState.value.copy(estimatedDuration = duration)
    }

    fun onNotesChange(notes: String) {
        _uiState.value = _uiState.value.copy(additionalNotes = notes)
    }

    fun onTabSelected(tab: MainNavigationTab) {
        _uiState.value = _uiState.value.copy(activeTab = tab)
    }

    fun onTitleSelected(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTitleIndex = index)
    }

    fun onCustomApiKeyChange(apiKey: String) {
        _uiState.value = _uiState.value.copy(customApiKey = apiKey)
    }

    fun onTagToolInputChange(text: String) {
        _uiState.value = _uiState.value.copy(tagToolInput = text)
    }

    fun onTitleToolInputChange(text: String) {
        _uiState.value = _uiState.value.copy(titleToolInput = text)
    }

    fun clearSnackbar() {
        _uiState.value = _uiState.value.copy(snackbarMessage = null)
    }

    fun showSnackbar(message: String) {
        _uiState.value = _uiState.value.copy(snackbarMessage = message)
    }

    fun setQuickPrompt(topic: String, niche: VideoNiche, tone: VideoTone, audience: String, duration: String) {
        _uiState.value = _uiState.value.copy(
            topic = topic,
            selectedNiche = niche,
            selectedTone = tone,
            targetAudience = audience,
            estimatedDuration = duration
        )
    }

    fun generateMetadata() {
        val topic = _uiState.value.topic.trim()
        if (topic.isBlank()) {
            showSnackbar("Please enter a video topic first!")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGenerating = true, isCurrentSaved = false)

            val params = GenerationParams(
                topic = topic,
                niche = _uiState.value.selectedNiche,
                tone = _uiState.value.selectedTone,
                targetAudience = _uiState.value.targetAudience,
                estimatedDuration = _uiState.value.estimatedDuration,
                additionalNotes = _uiState.value.additionalNotes
            )

            val result = repository.generateMetadata(params, _uiState.value.customApiKey)
            result.onSuccess { metadata ->
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    currentMetadata = metadata,
                    activeTab = MainNavigationTab.RESULTS,
                    selectedTitleIndex = 0,
                    tagToolInput = metadata.tags,
                    titleToolInput = metadata.titles.firstOrNull() ?: "",
                    snackbarMessage = "SEO metadata generated successfully! 🔥"
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    snackbarMessage = "Generation error: ${e.localizedMessage ?: "Unknown error"}"
                )
            }
        }
    }

    fun saveCurrentProject() {
        val metadata = _uiState.value.currentMetadata ?: return
        val topic = _uiState.value.topic.ifBlank { metadata.titles.firstOrNull() ?: "Untitled Project" }

        viewModelScope.launch {
            val params = GenerationParams(
                topic = topic,
                niche = _uiState.value.selectedNiche,
                tone = _uiState.value.selectedTone,
                targetAudience = _uiState.value.targetAudience,
                estimatedDuration = _uiState.value.estimatedDuration,
                additionalNotes = _uiState.value.additionalNotes
            )
            repository.saveProject(params, metadata)
            _uiState.value = _uiState.value.copy(
                isCurrentSaved = true,
                snackbarMessage = "Project saved to history! 💾"
            )
        }
    }

    fun loadSavedProject(project: SeoProject) {
        val metadata = repository.parseProjectToMetadata(project)
        val niche = VideoNiche.values().firstOrNull { it.displayName == project.niche } ?: VideoNiche.TECH
        val tone = VideoTone.values().firstOrNull { it.displayName == project.tone } ?: VideoTone.HIGH_CTR

        _uiState.value = _uiState.value.copy(
            topic = project.topic,
            selectedNiche = niche,
            selectedTone = tone,
            targetAudience = project.targetAudience,
            estimatedDuration = project.videoDuration,
            currentMetadata = metadata,
            isCurrentSaved = true,
            selectedTitleIndex = 0,
            activeTab = MainNavigationTab.RESULTS,
            tagToolInput = metadata.tags,
            titleToolInput = metadata.titles.firstOrNull() ?: "",
            snackbarMessage = "Loaded '${project.topic}'"
        )
    }

    fun toggleFavorite(project: SeoProject) {
        viewModelScope.launch {
            repository.toggleFavorite(project.id, project.isFavorite)
        }
    }

    fun deleteProject(project: SeoProject) {
        viewModelScope.launch {
            repository.deleteProject(project.id)
            showSnackbar("Deleted project")
        }
    }

    fun hasApiKeyConfigured(): Boolean {
        val buildKey = BuildConfig.GEMINI_API_KEY
        return (buildKey.isNotBlank() && buildKey != "MY_GEMINI_API_KEY") || _uiState.value.customApiKey.isNotBlank()
    }
}

package com.example.model

data class Chapter(
    val time: String,
    val title: String
)

data class YouTubeMetadata(
    val titles: List<String>,
    val description: String,
    val tags: String,
    val hashtags: List<String>,
    val chapters: List<Chapter>,
    val seoScore: Int = 94,
    val strategyTips: List<String> = emptyList(),
    val thumbnailHookText: String = ""
)

enum class VideoNiche(val displayName: String, val icon: String) {
    TECH("Tech & AI", "💻"),
    GAMING("Gaming", "🎮"),
    TUTORIAL("Education & Tutorials", "📚"),
    FINANCE("Finance & Crypto", "💰"),
    ENTERTAINMENT("Entertainment & Pop Culture", "🍿"),
    FITNESS("Health & Fitness", "💪"),
    VLOGS("Lifestyle & Travel", "✈️"),
    BUSINESS("Business & Marketing", "📈"),
    SCIENCE("Science & Deep Dives", "🔬"),
    MUSIC("Music & Podcasts", "🎧")
}

enum class VideoTone(val displayName: String, val desc: String) {
    HIGH_CTR("Click-Worthy & High Energy", "Maximizes CTR with curiosity gaps and bold hooks"),
    AUTHORITATIVE("Authoritative & Deep-Dive", "Positions you as the trusted industry leader"),
    STORYTELLING("Storytelling & Cinematic", "Emotion-driven hook for high retention"),
    CASUAL("Casual & Relatable", "Friendly conversational tone for strong community connection"),
    TREND_JACKING("Trending & Urgent", "Fast-paced, breaks down breaking news or viral topics")
}

data class GenerationParams(
    val topic: String,
    val niche: VideoNiche = VideoNiche.TECH,
    val tone: VideoTone = VideoTone.HIGH_CTR,
    val targetAudience: String = "Creators & General Public",
    val estimatedDuration: String = "10-15 mins",
    val additionalNotes: String = ""
)

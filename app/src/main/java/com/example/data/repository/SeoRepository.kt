package com.example.data.repository

import com.example.data.api.GeminiApiClient
import com.example.data.dao.SeoProjectDao
import com.example.data.entity.SeoProject
import com.example.model.Chapter
import com.example.model.GenerationParams
import com.example.model.YouTubeMetadata
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class SeoRepository(
    private val dao: SeoProjectDao,
    private val apiClient: GeminiApiClient = GeminiApiClient()
) {
    val allProjects: Flow<List<SeoProject>> = dao.getAllProjects()
    val favoriteProjects: Flow<List<SeoProject>> = dao.getFavoriteProjects()

    suspend fun generateMetadata(params: GenerationParams, userApiKey: String? = null): Result<YouTubeMetadata> {
        return apiClient.generateMetadata(params, userApiKey)
    }

    suspend fun saveProject(
        params: GenerationParams,
        metadata: YouTubeMetadata
    ): Long {
        val titlesArray = JSONArray()
        metadata.titles.forEach { titlesArray.put(it) }

        val hashtagsArray = JSONArray()
        metadata.hashtags.forEach { hashtagsArray.put(it) }

        val chaptersArray = JSONArray()
        metadata.chapters.forEach { ch ->
            val obj = JSONObject()
            obj.put("time", ch.time)
            obj.put("title", ch.title)
            chaptersArray.put(obj)
        }

        val tipsArray = JSONArray()
        metadata.strategyTips.forEach { tipsArray.put(it) }

        val entity = SeoProject(
            topic = params.topic,
            niche = params.niche.displayName,
            tone = params.tone.displayName,
            targetAudience = params.targetAudience,
            videoDuration = params.estimatedDuration,
            titlesJson = titlesArray.toString(),
            description = metadata.description,
            tags = metadata.tags,
            hashtagsJson = hashtagsArray.toString(),
            chaptersJson = chaptersArray.toString(),
            seoScore = metadata.seoScore,
            strategyTipsJson = tipsArray.toString(),
            thumbnailHookText = metadata.thumbnailHookText,
            isFavorite = false
        )
        return dao.insertProject(entity)
    }

    suspend fun toggleFavorite(projectId: Long, currentStatus: Boolean) {
        dao.toggleFavorite(projectId, !currentStatus)
    }

    suspend fun deleteProject(projectId: Long) {
        dao.deleteProjectById(projectId)
    }

    fun parseProjectToMetadata(project: SeoProject): YouTubeMetadata {
        val titles = mutableListOf<String>()
        try {
            val arr = JSONArray(project.titlesJson)
            for (i in 0 until arr.length()) titles.add(arr.getString(i))
        } catch (_: Exception) {}

        val hashtags = mutableListOf<String>()
        try {
            val arr = JSONArray(project.hashtagsJson)
            for (i in 0 until arr.length()) hashtags.add(arr.getString(i))
        } catch (_: Exception) {}

        val chapters = mutableListOf<Chapter>()
        try {
            val arr = JSONArray(project.chaptersJson)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                chapters.add(Chapter(obj.optString("time", "00:00"), obj.optString("title", "")))
            }
        } catch (_: Exception) {}

        val tips = mutableListOf<String>()
        try {
            val arr = JSONArray(project.strategyTipsJson)
            for (i in 0 until arr.length()) tips.add(arr.getString(i))
        } catch (_: Exception) {}

        return YouTubeMetadata(
            titles = titles,
            description = project.description,
            tags = project.tags,
            hashtags = hashtags,
            chapters = chapters,
            seoScore = project.seoScore,
            strategyTips = tips,
            thumbnailHookText = project.thumbnailHookText
        )
    }
}

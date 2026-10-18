package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seo_projects")
data class SeoProject(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val topic: String,
    val niche: String,
    val tone: String,
    val targetAudience: String,
    val videoDuration: String,
    val titlesJson: String,
    val description: String,
    val tags: String,
    val hashtagsJson: String,
    val chaptersJson: String,
    val seoScore: Int = 92,
    val strategyTipsJson: String = "[]",
    val thumbnailHookText: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.api.GeminiApiClient
import com.example.model.GenerationParams
import com.example.model.VideoNiche
import com.example.model.VideoTone
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("TubeSEO", appName)
  }

  @Test
  fun `verify intelligent preset generation produces valid metadata structure`() {
    val client = GeminiApiClient()
    val params = GenerationParams(
      topic = "How to Code with AI",
      niche = VideoNiche.TECH,
      tone = VideoTone.HIGH_CTR,
      targetAudience = "Programmers",
      estimatedDuration = "15 mins"
    )

    val metadata = client.generateIntelligentOfflinePreset(params)
    assertNotNull(metadata)
    assertTrue("Should generate multiple titles", metadata.titles.isNotEmpty())
    assertTrue("Should generate non-empty description", metadata.description.isNotBlank())
    assertTrue("Should generate tags", metadata.tags.isNotBlank())
    assertTrue("Should generate hashtags", metadata.hashtags.isNotEmpty())
    assertTrue("Should generate chapters", metadata.chapters.size >= 3)
    assertEquals("First chapter starts at 00:00", "00:00", metadata.chapters.first().time)
  }
}

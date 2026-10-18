package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.model.YouTubeMetadata

object CopyUtil {
    fun copyToClipboard(context: Context, label: String, text: String, toastMsg: String? = null) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(context, toastMsg ?: "Copied $label to clipboard! 📋", Toast.LENGTH_SHORT).show()
    }

    fun shareText(context: Context, title: String, content: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, content)
        }
        val chooser = Intent.createChooser(intent, "Share YouTube SEO Package")
        context.startActivity(chooser)
    }

    fun formatFullPackageText(topic: String, metadata: YouTubeMetadata): String {
        val sb = StringBuilder()
        sb.append("=== YOUTUBE SEO PACKAGE: $topic ===\n\n")

        sb.append("--- RECOMMENDED TITLES ---\n")
        metadata.titles.forEachIndexed { i, title ->
            sb.append("${i + 1}. $title (${title.length} chars)\n")
        }

        sb.append("\n--- DESCRIPTION ---\n")
        sb.append(metadata.description)
        sb.append("\n\n")

        sb.append("--- TAGS (CSV) ---\n")
        sb.append(metadata.tags)
        sb.append("\n\n")

        sb.append("--- HASHTAGS ---\n")
        sb.append(metadata.hashtags.joinToString(" "))
        sb.append("\n\n")

        sb.append("--- CHAPTERS / KEY MOMENTS ---\n")
        metadata.chapters.forEach { ch ->
            sb.append("${ch.time} - ${ch.title}\n")
        }

        if (metadata.strategyTips.isNotEmpty()) {
            sb.append("\n--- CONTENT STRATEGY TIPS ---\n")
            metadata.strategyTips.forEachIndexed { i, tip ->
                sb.append("${i + 1}. $tip\n")
            }
        }

        return sb.toString()
    }
}

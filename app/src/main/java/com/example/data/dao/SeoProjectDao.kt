package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.SeoProject
import kotlinx.coroutines.flow.Flow

@Dao
interface SeoProjectDao {
    @Query("SELECT * FROM seo_projects ORDER BY createdAt DESC")
    fun getAllProjects(): Flow<List<SeoProject>>

    @Query("SELECT * FROM seo_projects WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteProjects(): Flow<List<SeoProject>>

    @Query("SELECT * FROM seo_projects WHERE id = :id LIMIT 1")
    fun getProjectById(id: Long): Flow<SeoProject?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: SeoProject): Long

    @Update
    suspend fun updateProject(project: SeoProject)

    @Query("DELETE FROM seo_projects WHERE id = :id")
    suspend fun deleteProjectById(id: Long)

    @Query("UPDATE seo_projects SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: Long, isFavorite: Boolean)

    @Query("DELETE FROM seo_projects")
    suspend fun clearAll()
}

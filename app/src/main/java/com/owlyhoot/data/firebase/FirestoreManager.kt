package com.owlyhoot.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.owlyhoot.data.model.User
import com.owlyhoot.data.model.Post
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreManager @Inject constructor(private val firestore: FirebaseFirestore) {
    
    companion object {
        private const val USERS_COLLECTION = "users"
        private const val POSTS_COLLECTION = "posts"
        private const val LEADERBOARD_COLLECTION = "leaderboard"
    }
    
    suspend fun createUser(userId: String, user: User): Result<Unit> {
        return try {
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUser(userId: String): Result<User> {
        return try {
            val document = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .await()
            
            if (document.exists()) {
                val user = document.toObject(User::class.java)
                Result.success(user!!)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateUser(userId: String, userData: Map<String, Any>): Result<Unit> {
        return try {
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .update(userData)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createPost(post: Post): Result<String> {
        return try {
            val result = firestore.collection(POSTS_COLLECTION)
                .add(post)
                .await()
            Result.success(result.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPosts(limit: Int = 20): Result<List<Post>> {
        return try {
            val documents = firestore.collection(POSTS_COLLECTION)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()
            
            val posts = documents.toObjects(Post::class.java)
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPostsByUserId(userId: String, limit: Int = 20): Result<List<Post>> {
        return try {
            val documents = firestore.collection(POSTS_COLLECTION)
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()
            
            val posts = documents.toObjects(Post::class.java)
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateScore(userId: String, newScore: Int): Result<Unit> {
        return try {
            // Aggiorna il punteggio dell'utente
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .update("score", newScore)
                .await()
            
            // Potenzialmente aggiorna anche la leaderboard
            // Questo potrebbe essere fatto in una funzione separata o tramite Cloud Functions
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getLeaderboard(limit: Int = 100): Result<List<User>> {
        return try {
            val documents = firestore.collection(USERS_COLLECTION)
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()
            
            val users = documents.toObjects(User::class.java)
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getWeeklyLeaderboard(limit: Int = 100): Result<List<User>> {
        // Implementazione per ottenere la leaderboard settimanale
        // Questo richiederebbe dati aggiuntivi o una struttura diversa nel database
        // Per ora, restituiamo la leaderboard generale
        return getLeaderboard(limit)
    }
}
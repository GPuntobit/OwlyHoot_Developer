# Configurazione Firebase per OwlyHoot

## Passaggi necessari per integrare Firebase nell'app:

### 1. Creare un progetto Firebase
- Accedere a https://console.firebase.google.com/
- Creare un nuovo progetto chiamato "OwlyHoot"
- Abilitare Google Analytics (opzionale ma consigliato)

### 2. Aggiungere l'app Android al progetto Firebase
- Dal dashboard di Firebase, cliccare su "Aggiungi app"
- Selezionare l'icona Android
- Inserire il package name: `com.owlyhoot`
- Registrare l'app

### 3. Scaricare e aggiungere il file google-services.json
- Dopo aver registrato l'app, scaricare il file `google-services.json`
- Posizionare il file nella cartella `/app` del progetto (accanto a build.gradle)
- Il file dovrebbe trovarsi in: `/workspace/app/google-services.json`

### 4. Configurare l'autenticazione Firebase
- Nel dashboard di Firebase, andare in "Authentication"
- Abilitare i provider di autenticazione necessari:
  - Email/password
  - Wallet (tramite provider personalizzato)

### 5. Configurare Firestore
- Nel dashboard di Firebase, andare in "Firestore Database"
- Creare un database in modalità test (inizialmente)
- Impostare le regole di sicurezza appropriate

### 6. File di configurazione aggiuntivi
Dopo aver ottenuto il file google-services.json, assicurarsi che il plugin sia configurato correttamente nel build.gradle dell'app:

```gradle
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'com.google.gms.google-services'  // Questo deve essere presente
}
```

### 7. Codice per l'integrazione in Kotlin

Esempio di classe per la gestione dell'autenticazione:

```kotlin
class FirebaseAuthManager {
    private val auth: FirebaseAuth = Firebase.auth

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser
}
```

### 8. Regole di sicurezza Firestore di esempio

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Collezione users - gli utenti possono leggere e scrivere solo i propri dati
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Collezione posts - lettura pubblica, scrittura solo per utenti autenticati
    match /posts/{postId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == resource.data.userId;
    }
    
    // Collezione leaderboard - lettura pubblica, scrittura solo da parte del server
    match /leaderboard/{document} {
      allow read: if request.auth != null;
      allow write: if request.auth.token.admin == true;
    }
  }
}
```
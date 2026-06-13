# MyNotes

A clean, modern Android note-taking app built with **MVVM architecture**, **Room Database**, and **Material Design 3**.

![Platform](https://img.shields.io/badge/Platform-Android-green)
![Min SDK](https://img.shields.io/badge/Min%20SDK-24-blue)
![Language](https://img.shields.io/badge/Language-Java-orange)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-purple)

---

## Features

- **Create** — Add new notes with a title and content
- **Read** — All notes load instantly from local Room database via LiveData
- **Update** — Tap any note card or swipe left to edit
- **Delete** — Swipe right to delete a note
- **Validation** — Empty title or content is caught before saving
- **Empty state** — Friendly prompt shown when no notes exist
- **Beautiful UI** — Material Design 3 with a deep purple & pink theme
- **Offline-first** — No internet required; all data stored locally

---

## Screenshots

| Splash Screen | Notes List | Add Note | Edit Note |
|:---:|:---:|:---:|:---:|
| Purple branded splash | All notes in cards | Styled input form | Pre-filled edit form |

---

## Architecture

This app follows the **MVVM (Model-View-ViewModel)** pattern for clean separation of concerns:

```
┌──────────────────────────────────────────┐
│                UI Layer                  │
│  SplashScreen ──► MainActivity           │
│                      │                   │
│              DataInsertActivity2         │
└──────────────────┬───────────────────────┘
                   │  observes / calls
┌──────────────────▼───────────────────────┐
│            ViewModel Layer               │
│            NoteViewModel                 │
└──────────────────┬───────────────────────┘
                   │
┌──────────────────▼───────────────────────┐
│           Repository Layer               │
│           NoteRepository                 │
└──────────────────┬───────────────────────┘
                   │  async (Executor)
┌──────────────────▼───────────────────────┐
│           Database Layer                 │
│   noteDB (Room) ──► notedao (DAO)        │
│                ──► note (Entity)         │
└──────────────────────────────────────────┘
```

---

## Tech Stack

| Technology | Purpose |
|---|---|
| **Java** | Primary language |
| **Room Database** | Local SQLite persistence |
| **ViewModel** | Survives configuration changes |
| **LiveData** | Reactive, lifecycle-aware data |
| **RecyclerView** | Efficient scrolling note list |
| **View Binding** | Type-safe view access (no `findViewById`) |
| **Material Design 3** | UI components and theming |
| **ItemTouchHelper** | Swipe-to-delete / swipe-to-edit |
| **ActivityResultLauncher** | Modern replacement for `startActivityForResult` |

---

## Project Structure

```
app/src/main/java/com/example/mynotes/
├── MainActivity.java          # Main screen — displays all notes
├── DataInsertActivity2.java   # Add / Edit note screen
├── SplashScreen.java          # Branded launch screen (3s)
├── NoteAdapter.java           # RecyclerView adapter with click listeners
├── NoteViewModel.java         # Bridges UI and repository
├── NoteRepository.java        # Single source of truth for data
├── notedao.java               # Room DAO — insert, update, delete, query
├── noteDB.java                # Room database singleton
└── note.java                  # Entity model (id, title, disp)
```

---

## CRUD Operations

| Operation | Trigger |
|---|---|
| **Create** | Tap the pink FAB (`+`) → fill form → Save |
| **Read** | Notes load automatically via LiveData |
| **Update** | Tap a note card **or** swipe left → edit → Update |
| **Delete** | Swipe a note card to the **right** |

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34
- Java 17
- Gradle 8.1+

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/Noor-ulain555/MyNotes.git

# 2. Open Android Studio
#    File → Open → select the MyNotes folder

# 3. Let Gradle sync, then run on a device or emulator (API 24+)
```

---

## How It Works

1. **Launch** — Splash screen appears for 3 seconds, then navigates to the notes list.
2. **Add a note** — Tap the pink FAB → enter a title and content → tap **Save Note**.
3. **Edit a note** — Tap the note card (or swipe left) → make changes → tap **Update Note**.
4. **Delete a note** — Swipe the card to the right. The note is removed immediately.
5. **Persistence** — All notes survive app restarts via Room (SQLite under the hood).

---

## License

[MIT](LICENSE)

---

Built with passion by [Noor-ulain555](https://github.com/Noor-ulain555)
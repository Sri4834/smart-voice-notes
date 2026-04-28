# 🎙️ Smart Voice Notes

An intuitive Android application designed for hands-free note-taking and AI-powered summarization. Convert speech to text in real-time, save notes in multiple formats, and generate intelligent summaries using Google's Gemini API.

---

## ✨ Key Features

- **🎤 Voice-to-Text Conversion**: Real-time speech recognition using Android's built-in speech recognition engine
- **🤖 AI-Powered Summarization**: Automatically convert lectures and notes into well-structured study materials using Google Gemini 2.0 Flash API
- **📄 Multi-Format Support**: Save notes as `.txt` files for easy sharing and storage
- **📤 Instant Sharing**: Share notes directly via WhatsApp, Email, and other messaging platforms
- **⚡ Lightweight & Fast**: Optimized for performance with minimal battery consumption
- **🔐 Privacy-Focused**: Local processing with secure file storage

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Platform** | Android 12+ (API Level 31+) |
| **Language** | Java |
| **UI Framework** | Material Design 3 with XML layouts |
| **Build Tool** | Gradle |
| **AI Service** | Google Gemini 2.0 Flash API |
| **Architecture** | MVVM with Activity Result Contracts |
| **Data Format** | JSON |
| **File Management** | FileProvider for secure URI sharing |

---

## 📋 Project Structure

```
smart-voice-notes/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/smartvoicenotes/
│   │       │   ├── MainActivity.java          # Main UI & user interactions
│   │       │   └── AISummarizer.java          # Gemini API integration
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   ├── drawable/
│   │       │   ├── values/
│   │       │   └── xml/
│   │       │       └── file_paths.xml         # FileProvider configuration
│   │       └── AndroidManifest.xml            # App permissions & configuration
│   └── build.gradle                            # App-level dependencies
├── README.md
└── LICENSE

```

---

## 🚀 Quick Start Guide

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 31+
- Google Gemini API key

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Sri4834/smart-voice-notes.git
   cd smart-voice-notes
   ```

2. **Open in Android Studio**:
   - File → Open → Select the project folder
   - Wait for Gradle sync to complete

3. **Configure API Key**:
   - Create a `local.properties` file in the project root
   - Add your Google Gemini API key:
     ```properties
     API_KEY=your_gemini_api_key_here
     ```

4. **Build and Run**:
   - Connect your Android device or open an emulator
   - Click **Run** → **Run 'app'**

---

## 📱 Usage Guide

### Taking Voice Notes
1. Tap the **🎤 Speak** button
2. Speak clearly into the microphone
3. The app will automatically convert speech to text

### Summarizing Notes
1. Enter or paste text in the note editor
2. Tap the **Summarize** button
3. The app will use AI to generate structured study notes
4. Review and edit as needed

### Saving Notes
1. Type or record your note
2. Tap the **Save** button
3. Choose a location on your device
4. File is saved as `.txt` format

### Sharing Notes
1. Prepare your note text
2. Tap the **Share** button
3. Select your preferred app (WhatsApp, Email, etc.)
4. Send to recipients

---

## 🔐 Android Permissions

The app requires the following permissions:

| Permission | Purpose |
|-----------|---------|
| `INTERNET` | Connect to Gemini API for summarization |
| `ACCESS_NETWORK_STATE` | Check network connectivity |
| `RECORD_AUDIO` | Capture voice input for speech recognition |
| `READ_EXTERNAL_STORAGE` | Read saved files (Android 12 and below) |
| `WRITE_EXTERNAL_STORAGE` | Write notes to device storage (Android 9 and below) |

These permissions are automatically requested on first use.

---

## 🔑 API Configuration

### Google Gemini API Setup

1. Go to [Google AI Studio](https://aistudio.google.com/apikey)
2. Click **Create API Key**
3. Copy your API key
4. Paste it in `local.properties`:
   ```properties
   API_KEY=your_api_key_here
   ```

The app uses **Gemini 2.0 Flash** model with:
- Temperature: 0.3 (for consistent, focused summaries)
- Max Output Tokens: 4096
- Response Format: Structured study notes with headings and sections

---

## 📊 Code Overview

### MainActivity.java
Handles core functionality:
- **Voice Input**: Uses `RecognizerIntent` for speech-to-text
- **Note Management**: Text editing and display
- **File Operations**: Save and share functionality
- **UI Binding**: View binding with Material Design components

### AISummarizer.java
Manages API communication:
- HTTP requests to Gemini API
- Response parsing and formatting
- Error handling with user feedback
- Async execution with ExecutorService

---

## 🔄 Activity Flow

```
MainActivity
├── startVoiceInput() → SpeechRecognizer Intent
├── summarizeText() → AISummarizer → Gemini API
├── saveNote() → Storage Intent
└── shareNote() → Share Intent (WhatsApp/Email/etc)
```

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Speech recognition not working | Ensure microphone permission is granted |
| Summarization fails | Check internet connection and API key validity |
| Can't save notes | Verify storage permissions are enabled |
| App crashes on startup | Clear app cache and reinstall |

---

## 📦 Dependencies

- `androidx.appcompat:appcompat` - Compatibility library
- `androidx.core:core` - Core utilities and FileProvider
- `org.json:json` - JSON parsing
- Material Design 3 components

---

## 📄 License

This project is distributed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📧 Support & Contact

For issues, questions, or suggestions:
- Open an [Issue](https://github.com/Sri4834/smart-voice-notes/issues)
- Check existing documentation

---

## 🎯 Future Enhancements

- [ ] PDF export support
- [ ] Cloud sync and backup
- [ ] Multiple note categories/folders
- [ ] Dark mode support
- [ ] Offline summarization
- [ ] Voice commands for hands-free control
- [ ] Note search and filtering
- [ ] User authentication and accounts

---

## 🌟 Acknowledgments

- Google Gemini API for AI summarization
- Android Framework for speech recognition
- Material Design for UI/UX guidelines

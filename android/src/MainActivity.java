package com.ceylan.pipitalk;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private WebView webView;
    private TextToSpeech tts;
    private boolean ttsReady = false;
    private SpeechRecognizer speechRecognizer;
    private static final int REQ_AUDIO_PERMISSION = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tts = new TextToSpeech(this, this);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setDatabaseEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webView.addJavascriptInterface(new PipiBridge(), "PipiBridge");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        webView.loadUrl("file:///android_asset/index.html");

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO_PERMISSION);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            tts.setSpeechRate(0.88f);
            ttsReady = (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED);
        }
    }

    private void evalJs(final String js) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (webView != null) {
                    webView.evaluateJavascript(js, null);
                }
            }
        });
    }

    private void startNativeListening(final String langCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO_PERMISSION);
                    return;
                }
                if (!SpeechRecognizer.isRecognitionAvailable(MainActivity.this)) {
                    evalJs("window.onMicError && window.onMicError('Konuşma tanıma bu cihazda desteklenmiyor.')");
                    return;
                }
                if (tts != null) {
                    tts.stop();
                }
                if (speechRecognizer != null) {
                    speechRecognizer.destroy();
                }
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
                speechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override public void onReadyForSpeech(Bundle params) { evalJs("window.onMicState && window.onMicState(true)"); }
                    @Override public void onBeginningOfSpeech() {}
                    @Override public void onRmsChanged(float rmsdB) {}
                    @Override public void onBufferReceived(byte[] buffer) {}
                    @Override public void onEndOfSpeech() { evalJs("window.onMicState && window.onMicState(false)"); }
                    @Override public void onError(int error) {
                        evalJs("window.onMicState && window.onMicState(false)");
                        evalJs("window.onMicError && window.onMicError('Ses algılanamadı, tekrar deneyin.')");
                    }
                    @Override public void onResults(Bundle results) {
                        evalJs("window.onMicState && window.onMicState(false)");
                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        if (matches != null && !matches.isEmpty()) {
                            String spoken = matches.get(0).replace("\\", "\\\\").replace("'", "\\'");
                            evalJs("window.onMicResult && window.onMicResult('" + spoken + "')");
                        }
                    }
                    @Override public void onPartialResults(Bundle partialResults) {}
                    @Override public void onEvent(int eventType, Bundle params) {}
                });

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                String targetLang = (langCode != null && !langCode.isEmpty()) ? langCode : "en-US";
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLang);
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
                speechRecognizer.startListening(intent);
            }
        });
    }

    public class PipiBridge {
        @JavascriptInterface
        public void speak(String text) {
            speakWithLangRate(text, "en-US", 0.88f);
        }

        @JavascriptInterface
        public void speakSlow(String text) {
            speakWithLangRate(text, "en-US", 0.45f);
        }

        @JavascriptInterface
        public void speakWithLang(String text, String langCode) {
            speakWithLangRate(text, langCode, 0.88f);
        }

        @JavascriptInterface
        public void speakWithLangRate(String text, String langCode, float rate) {
            if (ttsReady && tts != null && text != null) {
                if ("tr-TR".equals(langCode)) {
                    tts.setLanguage(new Locale("tr", "TR"));
                } else {
                    tts.setLanguage(Locale.US);
                }
                tts.setPitch(rate < 0.6f ? 1.20f : 1.32f);
                tts.setSpeechRate(rate);
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "pipi_tts");
            }
        }

        @JavascriptInterface
        public void startListening() {
            startNativeListening("en-US");
        }

        @JavascriptInterface
        public void startListeningLang(String langCode) {
            startNativeListening(langCode);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}

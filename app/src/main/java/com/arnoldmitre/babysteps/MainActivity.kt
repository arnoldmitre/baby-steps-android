package com.arnoldmitre.babysteps

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.databaseEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()

        webView.addJavascriptInterface(WebAppInterface(this), "AndroidBridge")
        webView.loadUrl("file:///android_asset/index.html")
    }

    inner class WebAppInterface(private val context: Context) {

        @JavascriptInterface
        fun haptic() {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    vibratorManager.defaultVibrator.vibrate(
                        VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(20)
                }
            } catch (_: Exception) {}
        }

        @JavascriptInterface
        fun savePreference(key: String, value: String) {
            val prefs = context.getSharedPreferences("baby_steps_prefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            val intVal = value.toIntOrNull()
            if (intVal != null && (key == "widgetTransparency" || key == "weeks")) {
                editor.putInt(key, intVal)
            } else {
                editor.putString(key, value)
            }
            editor.apply()
            BabyStepsWidgetProvider.updateAllWidgets(context)
        }

        @JavascriptInterface
        fun getPreference(key: String, defaultValue: String): String {
            val prefs = context.getSharedPreferences("baby_steps_prefs", Context.MODE_PRIVATE)
            if (key == "widgetTransparency" || key == "weeks") {
                val defInt = defaultValue.toIntOrNull() ?: 100
                return prefs.getInt(key, defInt).toString()
            }
            return prefs.getString(key, defaultValue) ?: defaultValue
        }

        @JavascriptInterface
        fun saveWeeks(weeks: Int) {
            val prefs = context.getSharedPreferences("baby_steps_prefs", Context.MODE_PRIVATE)
            prefs.edit().putInt("weeks", weeks).apply()
            BabyStepsWidgetProvider.updateAllWidgets(context)
        }
    }
}

package br.com.printfacil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private val projectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val service = Intent(this, CaptureProjectionService::class.java).apply {
                putExtra("resultCode", result.resultCode)
                putExtra("data", result.data)
            }
            startForegroundService(service)
            webView.evaluateJavascript("window.onNativeCaptureReady && window.onNativeCaptureReady()", null)
        } else {
            webView.evaluateJavascript("window.onNativeCaptureError && window.onNativeCaptureError('Permissão negada')", null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(NativeBridge(), "PrintFacilNative")
        webView.loadUrl("file:///android_asset/www/index.html")
    }

    inner class NativeBridge {
        @JavascriptInterface fun requestScreenCapture() {
            runOnUiThread {
                val manager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
                projectionLauncher.launch(manager.createScreenCaptureIntent())
            }
        }
        @JavascriptInterface fun requestOverlayPermission() {
            runOnUiThread {
                if (!android.provider.Settings.canDrawOverlays(this@MainActivity)) {
                    val intent = Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    startActivity(intent)
                } else {
                    startService(Intent(this@MainActivity, FloatingCaptureService::class.java))
                }
            }
        }
    }
}
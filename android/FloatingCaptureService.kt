package br.com.printfacil

import android.app.*
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.*
import android.widget.ImageButton

class FloatingCaptureService : Service() {
    private var windowManager: WindowManager? = null
    private var button: View? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channel = "printfacil_overlay"
        if (Build.VERSION.SDK_INT >= 26) {
            getSystemService(NotificationManager::class.java).createNotificationChannel(
                NotificationChannel(channel, "Print Fácil", NotificationManager.IMPORTANCE_LOW)
            )
        }
        val notification = Notification.Builder(this, channel)
            .setContentTitle("Print Fácil ativo")
            .setContentText("Botão flutuante disponível")
            .setSmallIcon(android.R.drawable.ic_menu_camera).build()
        startForeground(41, notification)
        if (Settings.canDrawOverlays(this)) showOverlay()
        return START_STICKY
    }
    private fun showOverlay() {
        if (button != null) return
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val b = ImageButton(this).apply {
            setImageResource(android.R.drawable.ic_menu_camera)
            contentDescription = "Capturar tela"
            setBackgroundResource(android.R.drawable.btn_default)
            setOnClickListener {
                // Dispare o fluxo MediaProjection autorizado na Activity/integração.
                // A captura não pode ser iniciada sem o consentimento exigido pelo Android.
            }
        }
        val type = if (Build.VERSION.SDK_INT >= 26) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                   else WindowManager.LayoutParams.TYPE_PHONE
        val params = WindowManager.LayoutParams(60, 60, type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)
        params.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        windowManager?.addView(b, params); button = b
    }
    override fun onDestroy() { button?.let { windowManager?.removeView(it) }; button = null; super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
}
package br.com.printfacil

import android.app.Service
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.IBinder

class CaptureProjectionService : Service() {
    private var projection: MediaProjection? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val code = intent?.getIntExtra("resultCode", 0) ?: 0
        val data = if (android.os.Build.VERSION.SDK_INT >= 33)
            intent?.getParcelableExtra("data", Intent::class.java)
        else @Suppress("DEPRECATION") intent?.getParcelableExtra("data")
        if (data != null) {
            val manager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            projection = manager.getMediaProjection(code, data)
            // Próximo passo: criar VirtualDisplay + ImageReader, gravar PNG/JPEG
            // em MediaStore e encerrar projection.stop() após a captura.
        }
        return START_NOT_STICKY
    }
    override fun onDestroy() { projection?.stop(); projection = null; super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
}
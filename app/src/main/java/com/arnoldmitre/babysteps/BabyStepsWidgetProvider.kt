package com.arnoldmitre.babysteps

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.RemoteViews
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.max

class BabyStepsWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {

        private val crawlSprite = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "...wwwwwwsssss....",
            "..wwwwwwwwssss....",
            ".wwuwwwwww.ss.ee..",
            ".wdddddddw.ss.ee..",
            "..sssssddw.ss.ee..",
            "...ss...ss.ss.ss.."
        )

        private val sleepSprite = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......ysssssssy..",
            ".......yssskkkss..",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "..wwwwwwwsssss....",
            ".wwwwwwwwwssss....",
            ".wwuwwwwww.sssss..",
            ".wdddddddw..sssss.",
            "..sssssddw...sss..",
            "...sssssss........"
        )

        private val cribSprite = arrayOf(
            ".......rr.........",
            "......rrrr........",
            ".......rr.........",
            ".......mm.........",
            "...yyyyyyyyyyy....",
            "..y...........y...",
            "..y.wwwwwwwww.y...",
            "..y.wwwwwwwww.y...",
            "..y.m.m.m.m.m.y...",
            "..y.m.m.m.m.m.y...",
            "..y.m.m.m.m.m.y...",
            "..yyyyyyyyyyyyy...",
            "..y...........y...",
            "..y...........y...",
            "..y...........y..."
        )

        private val moonSprite = arrayOf(
            "...yyyy",
            "..yy...",
            ".yy....",
            ".yy....",
            ".yy....",
            "..yy...",
            "...yyyy"
        )

        private val smallZSprite = arrayOf(
            "zzzz",
            "..z.",
            ".z..",
            "zzzz"
        )

        private fun getPalette(gender: String): Map<Char, Int> {
            val isGirl = gender == "girl"
            val isBoy = gender == "boy"
            return mapOf(
                'k' to 0xFF281919.toInt(),
                'w' to 0xFFFFFFFF.toInt(),
                's' to 0xFFFEDBC0.toInt(),
                'e' to 0xFFE8B696.toInt(),
                'c' to (if (isGirl) 0xFFFB7185.toInt() else 0xFFFF90A2.toInt()),
                'm' to (if (isGirl) 0xFFF472B6.toInt() else 0xFF38BDF8.toInt()),
                'd' to (if (isGirl) 0xFFE9A9CB.toInt() else 0xFF93C5FD.toInt()),
                'y' to 0xFFF5B041.toInt(),
                'b' to (if (isGirl) 0xFFF472B6.toInt() else 0xFFF5B041.toInt()),
                'o' to (if (isGirl) 0xFFFDA4AF.toInt() else 0xFFF5B041.toInt()),
                'u' to (if (isGirl) 0xFFF472B6.toInt() else if (isBoy) 0xFF60A5FA.toInt() else 0xFFFBBF24.toInt()),
                'p' to 0xFFF43F5E.toInt(),
                'r' to (if (isGirl) 0xFFFB7185.toInt() else 0xFFEF4444.toInt()),
                'z' to 0xFF818CF8.toInt(),
                'g' to 0xFF22C55E.toInt()
            )
        }

        private fun drawSprite(
            canvas: Canvas,
            sprite: Array<String>,
            x: Float,
            y: Float,
            scale: Float,
            palette: Map<Char, Int>,
            paint: Paint
        ) {
            for (r in sprite.indices) {
                val row = sprite[r]
                for (c in row.indices) {
                    val ch = row[c]
                    if (ch != '.' && palette.containsKey(ch)) {
                        paint.color = palette[ch]!!
                        canvas.drawRect(
                            x + c * scale,
                            y + r * scale,
                            x + (c + 1) * scale,
                            y + (r + 1) * scale,
                            paint
                        )
                    }
                }
            }
        }

        private fun renderTrackBitmap(context: Context, safeWeeks: Int, progressPct: Int, gender: String): Bitmap {
            val width = 600
            val height = 130
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint().apply { isAntiAlias = false }

            val palette = getPalette(gender)
            val isGirl = gender == "girl"

            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val isNight = hour < 7 || hour >= 20

            val groundY = height - 20f
            val trackStartX = 18f
            val cribScale = 2.4f
            val cribW = 18f * cribScale
            val trackEndX = width - cribW - 20f
            val trackLen = max(20f, trackEndX - trackStartX)

            // Night sky stars and moon (transparent overlay)
            if (isNight) {
                drawSprite(canvas, moonSprite, trackEndX - 30f, 6f, 1.8f, palette, paint)

                paint.color = 0xDDFDE047.toInt()
                canvas.drawRect(trackStartX + trackLen * 0.15f, 10f, trackStartX + trackLen * 0.15f + 3f, 13f, paint)
                canvas.drawRect(trackStartX + trackLen * 0.42f, 8f, trackStartX + trackLen * 0.42f + 3f, 11f, paint)
                canvas.drawRect(trackStartX + trackLen * 0.72f, 14f, trackStartX + trackLen * 0.72f + 3f, 17f, paint)
            }

            // Checkered track dots
            val dotsCount = (trackLen / 12f).toInt()
            for (i in 0 until dotsCount) {
                val dotX = trackStartX + i * 12f
                paint.color = if (i % 2 == 0) (if (isGirl) 0x88F472B6.toInt() else 0x8860A5FA.toInt()) else 0x44FFFFFF.toInt()
                canvas.drawRect(dotX, groundY + 4f, dotX + 6f, groundY + 7f, paint)
            }

            // Milestones markers (w10, w20, w30)
            paint.color = 0x66FFFFFF.toInt()
            canvas.drawRect(trackStartX + trackLen * 0.25f, groundY - 2f, trackStartX + trackLen * 0.25f + 2f, groundY + 7f, paint)
            paint.color = if (isGirl) 0xCCF472B6.toInt() else 0xCC60A5FA.toInt()
            canvas.drawRect(trackStartX + trackLen * 0.5f, groundY - 5f, trackStartX + trackLen * 0.5f + 3f, groundY + 9f, paint)
            paint.color = 0x66FFFFFF.toInt()
            canvas.drawRect(trackStartX + trackLen * 0.75f, groundY - 2f, trackStartX + trackLen * 0.75f + 2f, groundY + 7f, paint)

            // Due date crib at track end
            val cribX = width - cribW - 12f
            val cribY = groundY - 15f * cribScale + 6f
            drawSprite(canvas, cribSprite, cribX, cribY, cribScale, palette, paint)

            // Floating heart above crib
            paint.color = if (isGirl) 0xFFFB7185.toInt() else 0xFFEF4444.toInt()
            canvas.drawRect(cribX + 7f * cribScale, cribY - 8f, cribX + 7f * cribScale + 5f, cribY - 3f, paint)

            // Baby position on track
            val babyPct = (progressPct / 100f).coerceIn(0f, 1f)
            val babyX = trackStartX + babyPct * (trackLen - 30f)
            val scaleFactor = when {
                safeWeeks <= 12 -> 2.0f
                safeWeeks <= 26 -> 2.4f
                safeWeeks <= 36 -> 2.7f
                else -> 3.0f
            }
            val babyY = groundY - 13f * scaleFactor + 6f
            val sprite = if (isNight) sleepSprite else crawlSprite
            drawSprite(canvas, sprite, babyX, babyY, scaleFactor, palette, paint)

            // Floating Zzz if sleeping at night
            if (isNight) {
                val zX = babyX + 16f * scaleFactor
                val zY = groundY - 14f * scaleFactor - 4f
                drawSprite(canvas, smallZSprite, zX, zY, 1.4f, palette, paint)
            }

            return bitmap
        }

        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, BabyStepsWidgetProvider::class.java)
            val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            for (widgetId in allWidgetIds) {
                updateAppWidget(context, appWidgetManager, widgetId)
            }
        }

        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val prefs = context.getSharedPreferences("baby_steps_prefs", Context.MODE_PRIVATE)
            val babyName = prefs.getString("babyName", "Sara") ?: "Sara"
            val gender = prefs.getString("gender", "girl") ?: "girl"
            val weeks = prefs.getInt("weeks", 26)
            val dueDateStr = prefs.getString("dueDate", "") ?: ""

            val isGirl = gender == "girl"
            val isBoy = gender == "boy"
            val icon = if (isGirl) "🎀" else if (isBoy) "🧢" else "👶"

            val nowMs = System.currentTimeMillis()
            var safeWeeks = weeks
            var daysLeft = 100
            var weeksLeft = 14
            var progressPct = 65

            try {
                if (dueDateStr.isNotEmpty()) {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    val dueDate = sdf.parse(dueDateStr)
                    if (dueDate != null) {
                        val diffDays = ((dueDate.time - nowMs) / 86400000L).toInt()
                        daysLeft = max(0, diffDays)
                        weeksLeft = max(0, daysLeft / 7)
                        safeWeeks = (40 - weeksLeft).coerceIn(1, 40)
                        progressPct = ((safeWeeks / 40.0) * 100).toInt().coerceIn(0, 100)
                    }
                }
            } catch (_: Exception) {}

            val trimester = when {
                safeWeeks <= 12 -> "1ST TRI"
                safeWeeks <= 26 -> "2ND TRI"
                else -> "3RD TRI"
            }

            var fruitName = "Lettuce"
            var fruitLength = "35.6 cm"
            try {
                context.assets.open("milestones.json").use { stream ->
                    val jsonStr = stream.bufferedReader().readText()
                    val arr = JSONArray(jsonStr)
                    val idx = (safeWeeks - 1).coerceIn(0, arr.length() - 1)
                    val obj = arr.getJSONObject(idx)
                    fruitName = obj.optString("fruit", "Lettuce")
                    fruitLength = obj.optString("length", "35.6 cm")
                }
            } catch (_: Exception) {}

            val views = RemoteViews(context.packageName, R.layout.widget_baby_steps)
            views.setTextViewText(R.id.tv_widget_week, "$icon WK $safeWeeks")
            views.setTextViewText(R.id.tv_widget_trimester, trimester)
            views.setTextViewText(R.id.tv_widget_name, "$icon ${babyName.uppercase(Locale.US)}")
            views.setTextViewText(R.id.tv_widget_days_left, "${daysLeft}d left (${weeksLeft}w)")
            views.setProgressBar(R.id.widget_progress, 100, progressPct, false)
            views.setTextViewText(R.id.tv_widget_fruit, "$icon $babyName • $fruitName ($fruitLength)")
            views.setTextViewText(R.id.tv_widget_percent, "$progressPct%")

            // Render and set the 8-bit Track Canvas with crawling baby & crib
            val trackBitmap = renderTrackBitmap(context, safeWeeks, progressPct, gender)
            views.setImageViewBitmap(R.id.widget_track_canvas, trackBitmap)

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

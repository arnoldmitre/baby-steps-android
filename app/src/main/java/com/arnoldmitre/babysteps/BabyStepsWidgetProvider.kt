package com.arnoldmitre.babysteps

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioManager
import android.view.View
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

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action ?: return
        if (action.contains("playstate", ignoreCase = true) ||
            action.contains("metachanged", ignoreCase = true) ||
            action.contains("playback", ignoreCase = true) ||
            action == Intent.ACTION_USER_PRESENT ||
            action == Intent.ACTION_SCREEN_ON) {
            updateAllWidgets(context)
        }
    }

    companion object {

        // 4 Crawling Animation Frames (18x13)
        private val crawl1 = arrayOf(
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

        private val crawl2 = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "...wwwwwwsssss....",
            "..wwwwwwwwssss....",
            ".wwuwwwwww.ssss...",
            ".wdddddddw..sss...",
            "..sssssddw..sss...",
            "...ssss.....sss..."
        )

        private val crawl3 = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "...wwwwwwsssss....",
            "..wwwwwwwwssss....",
            ".wwuwwwwww..ssss..",
            ".wdddddddw..ssss..",
            "..sssssddw.ee.ss..",
            "..ssss..ss.ee.ss.."
        )

        private val crawl4 = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "...wwwwwwsssss....",
            "..wwwwwwwwssss....",
            ".wwuwwwwww.ssss...",
            ".wdddddddw..sss...",
            "..sssssddw..sss...",
            "....ssss....sss..."
        )

        // 4 Music Dance Animation Frames (18x13)
        private val musicCrawl1 = arrayOf(
            ".........booby....",
            ".........ybbssy.s.",
            "........yssssssys.",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "...wwwwwwsssss....",
            "..wwwwwwwwssss....",
            ".wwuwwwwww.ss.....",
            ".wdddddddw.ss.....",
            "..sssssddw.ss.....",
            "...ss...ss.ss....."
        )

        private val musicCrawl2 = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmmm..",
            "...wwwwwwssssss...",
            "..wwwwwwwwssss....",
            ".wwuwwwwww.ssss...",
            ".wdddddddw..sss...",
            "..sssssddw..sss...",
            "...ssss.....sss..."
        )

        private val musicCrawl3 = arrayOf(
            ".........booby....",
            ".......ssybbssy...",
            ".......ssyssssssy.",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "...wwwwwwsssss....",
            "..wwwwwwwwssss....",
            ".wwuwwwwww..ssss..",
            ".wdddddddw..ssss..",
            "..sssssddw.ee.ss..",
            "..ssss..ss.ee.ss.."
        )

        private val musicCrawl4 = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......yssskwssy..",
            ".......yssskkssss.",
            ".......ysccsssss..",
            ".......ssssssmmm..",
            "...wwwwwwssssss...",
            "..wwwwwwwwssss....",
            ".wwuwwwwww.ssss...",
            ".wdddddddw..sss...",
            "..sssssddw..sss...",
            "....ssss....sss..."
        )

        // 2 Sleep Animation Frames (18x13)
        private val sleep1 = arrayOf(
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

        private val sleep2 = arrayOf(
            ".........booby....",
            ".........ybbssy...",
            "........yssssssy..",
            ".......ysssssssy..",
            ".......yssskkkss..",
            ".......ysccsssss..",
            ".......ssssssmss..",
            "...wwwwwwsssss....",
            "..wwwwwwwwssss....",
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

        private val noteA = arrayOf(
            "...p.",
            "...p.",
            "...p.",
            ".ppp.",
            ".ppp."
        )

        private val noteB = arrayOf(
            "..ppppp",
            "..p...p",
            "..p...p",
            "ppp.ppp",
            "ppp.ppp"
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

        // Render Background Plate with customizable One UI transparency
        private fun renderBackgroundPlate(width: Int, height: Int, transparency: Int): Bitmap? {
            if (transparency >= 100) return null
            val alpha = ((100 - transparency) / 100f * 240).toInt().coerceIn(0, 255)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint().apply { isAntiAlias = true }
            paint.color = Color.argb(alpha, 15, 23, 42)
            canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 20f, 20f, paint)
            return bitmap
        }

        // Render One of the 4 Animated Track Frames
        private fun renderTrackBitmap(
            safeWeeks: Int,
            progressPct: Int,
            gender: String,
            frameIndex: Int,
            isMusicPlaying: Boolean
        ): Bitmap {
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

            // Night sky stars and moon (transparent background)
            if (isNight && !isMusicPlaying) {
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

            // Floating heart above crib (bobs with animation frame)
            val heartOffset = if (frameIndex % 2 == 0) -8f else -11f
            paint.color = if (isGirl) 0xFFFB7185.toInt() else 0xFFEF4444.toInt()
            canvas.drawRect(cribX + 7f * cribScale, cribY + heartOffset, cribX + 7f * cribScale + 5f, cribY + heartOffset + 5f, paint)

            // Baby position on track
            val babyPct = (progressPct / 100f).coerceIn(0f, 1f)
            val babyX = trackStartX + babyPct * (trackLen - 30f)
            val scaleFactor = when {
                safeWeeks <= 12 -> 2.0f
                safeWeeks <= 26 -> 2.4f
                safeWeeks <= 36 -> 2.7f
                else -> 3.0f
            }

            // Select sprite and motion bob based on state and frame
            val sprite: Array<String>
            val bobY: Float

            if (isMusicPlaying) {
                // Dance groove animation
                sprite = when (frameIndex % 4) {
                    0 -> musicCrawl1
                    1 -> musicCrawl2
                    2 -> musicCrawl3
                    else -> musicCrawl4
                }
                bobY = when (frameIndex % 4) {
                    0 -> 0f
                    1 -> 3f
                    2 -> -3f
                    else -> 4f
                }

                // Floating musical notes dancing around baby
                val noteOffset = (frameIndex * 6f) % 20f
                drawSprite(canvas, noteA, babyX + 16f * scaleFactor, groundY - 18f * scaleFactor - noteOffset, 1.8f, palette, paint)
                drawSprite(canvas, noteB, babyX - 8f, groundY - 14f * scaleFactor - ((noteOffset + 8f) % 18f), 1.6f, palette, paint)
            } else if (isNight) {
                // Peaceful sleep breathing cycle
                sprite = if (frameIndex % 2 == 0) sleep1 else sleep2
                bobY = if (frameIndex % 2 == 0) 0f else 1.5f

                // Drifting Zzz
                val zOffset = (frameIndex * 5f) % 18f
                drawSprite(canvas, smallZSprite, babyX + 16f * scaleFactor, groundY - 14f * scaleFactor - zOffset, 1.4f, palette, paint)
            } else {
                // Natural 4-frame cross-crawling cycle
                sprite = when (frameIndex % 4) {
                    0 -> crawl1
                    1 -> crawl2
                    2 -> crawl3
                    else -> crawl4
                }
                bobY = when (frameIndex % 4) {
                    0 -> 0f
                    1 -> 2.5f
                    2 -> 0f
                    else -> 3f
                }
            }

            val babyY = groundY - 13f * scaleFactor + 6f + bobY
            drawSprite(canvas, sprite, babyX, babyY, scaleFactor, palette, paint)

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
            val transparency = prefs.getInt("widgetTransparency", 100) // Default 100% transparent

            val isGirl = gender == "girl"
            val isBoy = gender == "boy"
            val icon = if (isGirl) "🎀" else if (isBoy) "🧢" else "👶"

            // Check if audio / music is currently playing on Android!
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            val isMusicPlaying = audioManager?.isMusicActive == true

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
            val danceNote = if (isMusicPlaying) " 🎶" else ""
            views.setTextViewText(R.id.tv_widget_fruit, "$icon $babyName • $fruitName ($fruitLength)$danceNote")
            views.setTextViewText(R.id.tv_widget_percent, "$progressPct%")

            // Dynamic Background Plate for One UI Transparency
            val bgPlate = renderBackgroundPlate(600, 240, transparency)
            if (bgPlate != null) {
                views.setImageViewBitmap(R.id.widget_bg_plate, bgPlate)
                views.setViewVisibility(R.id.widget_bg_plate, View.VISIBLE)
            } else {
                views.setViewVisibility(R.id.widget_bg_plate, View.GONE)
            }

            // Render all 4 frames of the moving / dancing baby for ViewFlipper!
            val frame0 = renderTrackBitmap(safeWeeks, progressPct, gender, 0, isMusicPlaying)
            val frame1 = renderTrackBitmap(safeWeeks, progressPct, gender, 1, isMusicPlaying)
            val frame2 = renderTrackBitmap(safeWeeks, progressPct, gender, 2, isMusicPlaying)
            val frame3 = renderTrackBitmap(safeWeeks, progressPct, gender, 3, isMusicPlaying)

            views.setImageViewBitmap(R.id.widget_frame_0, frame0)
            views.setImageViewBitmap(R.id.widget_frame_1, frame1)
            views.setImageViewBitmap(R.id.widget_frame_2, frame2)
            views.setImageViewBitmap(R.id.widget_frame_3, frame3)

            // Speed up flip interval when grooving to music (250ms), normal crawl (350ms)
            val flipSpeed = if (isMusicPlaying) 260 else 360
            views.setInt(R.id.widget_flipper, "setFlipInterval", flipSpeed)

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

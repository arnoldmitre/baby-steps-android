package com.arnoldmitre.babysteps

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import org.json.JSONArray
import java.text.SimpleDateFormat
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

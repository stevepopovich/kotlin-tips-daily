package com.popovich.kotlintipsdaily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.popovich.kotlintipsdaily.database.TipSyncService
import com.popovich.kotlintipsdaily.database.room.AppDatabase
import com.popovich.kotlintipsdaily.ui.theme.KotlinTipsDailyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinTipsDailyTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        AppDatabase.database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tips-database"
        ).build()

        TipSyncService.start()
//        TipsService.scheduleNotification(applicationContext)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinTipsDailyTheme {
        Greeting("Android")
    }
}

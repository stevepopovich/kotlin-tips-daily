package com.popovich.kotlintipsdaily

import android.net.Uri
import java.util.*

typealias TipID = UUID

data class Tip(
    val id: TipID,
    val content: String,
    val title: String,
    val link: Uri
)

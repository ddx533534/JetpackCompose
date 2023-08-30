package com.ddx.compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Term(val title: String, val subTitle: String, var content: String) : Parcelable
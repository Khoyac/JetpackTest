package com.khoyac.jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khoyac.jetpack.MainActivity.Companion.getPainter
import com.khoyac.jetpack.MainActivity.Companion.sampleData
import com.khoyac.jetpack.ui.theme.JetpackTheme
import org.json.JSONArray
import org.json.JSONObject

class MsgActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle : Bundle? ? = intent.extras
        val pos : Int = bundle!!.getInt("pos")
        setContent {
            JetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    News(sampleData[pos-1].datos.title,
                        sampleData[pos-1].datos.desc,
                        getPainter(sampleData[pos-1].datos.picture, this)
                    )
                }
            }
        }
    }
}

@Composable
fun News(title: String, desc: String, id: Int) {
    val painter: Painter = painterResource(id = id)
    Column {
        Box {
            Image(
                painter = painter,
                contentDescription = desc,
                contentScale = ContentScale.Crop
            )
        }
        Text(title, color = Color.Blue, fontSize = 30.sp, textAlign = TextAlign.Center)
        Text(desc)
    }
}
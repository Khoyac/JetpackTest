package com.khoyac.jetpack

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.khoyac.jetpack.MainActivity.Companion.getPainter
import com.khoyac.jetpack.ui.theme.JetpackTheme
import java.io.IOException
import kotlin.math.log

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var sampleData: List<Data>

        //Funcion para obtener el ID de una imagen que esta en Drawable pasandole el nombre
        fun getPainter(picture: String, context: Context): Int {
            val Res: Resources = context.resources
            val idInt = Res.getIdentifier(picture, "drawable", context.packageName)
            return idInt
        }
    }
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val dataFileString = getJsonFromAsset(this, "SampleData.json")
            val gson = Gson()
            val gridSampleType = object : TypeToken<List<Data>>() {}.type
            sampleData = gson.fromJson(dataFileString, gridSampleType)

            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(10.dp)
            ) {
                items(sampleData) { data ->
                    SampleDataGridItems(data, applicationContext)
                }
            }

        }
    }

}
//Se llama a la funcion que crea el Card
@Composable
fun SampleDataGridItems(data: Data, context: Context) {
    ImageCard(
        //painter = painterResource(id = R.drawable.cat),
        painter = painterResource(id = getPainter(data.datos.picture, context)),
        //painter = getPainter(data.datos.picture, context),
        contentDescription = data.datos.desc,
        title = data.datos.title,
        context = context,
        pos = data.id
    )
}


//Funcion que crea el nuevo componente con el Card que mostrara los datos
@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    context: Context,
    pos: Int,
    modifier: Modifier = Modifier
    ) {

    Card(
        modifier = modifier.fillMaxWidth()
            .clickable(
                onClick = {
                    //Creamos el intent para cambiar de activity
                    //Y pasar la posicion del JSON que se ha clickado
                    Log.i("CODIGO", "POSICION -------------: $pos")
                    val homeIntent = Intent(context, MsgActivity::class.java).apply {
                        putExtra("pos", pos)
                    }
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(homeIntent)
                }
        ),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
                )
            Box(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }

}

//Funcion que recoge los datos del JSON
fun getJsonFromAsset(context: Context, data: String): String {
    return context.assets.open(data).bufferedReader().use { it.readText() }
}

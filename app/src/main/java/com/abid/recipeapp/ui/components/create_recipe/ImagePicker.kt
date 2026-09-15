package com.abid.recipeapp.ui.components.create_recipe

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.abid.recipeapp.util.Constants.CLICK_HERE_TO_PICK_FROM_GALLERY
import com.abid.recipeapp.util.Dims


@Composable
fun ImagePicker(onSuccessImageUri: (Uri?) -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Android Photo Picker — no storage/media permission required
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        imageUri = uri
        onSuccessImageUri(imageUri)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dims.Dp200)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        imageUri?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Text(
            CLICK_HERE_TO_PICK_FROM_GALLERY,
            textAlign = TextAlign.Center
        )
    }
}
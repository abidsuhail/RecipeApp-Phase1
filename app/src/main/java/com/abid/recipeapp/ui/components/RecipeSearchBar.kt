package com.abid.recipeapp.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A pill-shaped search bar matching the design: search icon, editable text
 * with placeholder, and a trailing filter (sliders) icon.
 */
@Composable
fun RecipeSearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = "Search recipes...",
    backgroundColor: Color = Color(0xFF8E8E93),
    contentColor: Color = Color.Black,
    onFilterClick: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = contentColor,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(modifier = Modifier.weight(1f)) {
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    color = contentColor,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(contentColor),
                interactionSource = remember { MutableInteractionSource() },
                modifier = Modifier.fillMaxWidth()
            )

            if (query.isEmpty()) {
                Text(
                    text = placeholder,
                    color = contentColor.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = "Filter",
            tint = contentColor,
            modifier = Modifier
                .size(22.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onFilterClick
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeSearchBarPreview() {
    RecipeSearchBar(
        modifier = Modifier.padding(16.dp)
    )
}
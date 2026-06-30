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
import androidx.compose.ui.text.style.TextOverflow
import com.abid.recipeapp.ui.theme.LocalAppCustomColors
import com.abid.recipeapp.util.Constants.FILTER
import com.abid.recipeapp.util.Constants.SEARCH
import com.abid.recipeapp.util.Constants.SEARCH_RECIPES
import com.abid.recipeapp.util.Dims


/**
 * A pill-shaped search bar matching the design: search icon, editable text
 * with placeholder, and a trailing filter (sliders) icon.
 */
@Composable
fun RecipeSearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = SEARCH_RECIPES,
    onTxtEntered: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dims.Dp56)
            .background(
                color = LocalAppCustomColors.current.searchCardBg,
                shape = RoundedCornerShape(percent = Dims.const50)
            )
            .padding(horizontal = Dims.Dp18),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = SEARCH,
            modifier = Modifier.size(Dims.Dp22)
        )

        Spacer(modifier = Modifier.width(Dims.Dp12))

        Box(modifier = Modifier.weight(Dims.W1)) {
            BasicTextField(
                value = query,
                onValueChange = {
                    query = it
                    onTxtEntered(it)
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                interactionSource = remember { MutableInteractionSource() },
                modifier = Modifier.fillMaxWidth()
            )

            if (query.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = Dims.const1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.width(Dims.Dp12))

        Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = FILTER,
            modifier = Modifier
                .size(Dims.Dp22)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onFilterClick
                )
        )
    }
}
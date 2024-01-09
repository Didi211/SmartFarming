package com.elfak.smartfarming.ui.components.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.R

@Composable
fun LogoImage(size: Dp, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = stringResource(id = R.string.logo_description),
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .size(size)
    )
}

//@Preview(
//    showBackground = true,
//)
//@Composable
//fun LogoPreview() {
//    LogoImage(size = 180.dp)
//}
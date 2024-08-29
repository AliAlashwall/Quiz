package com.example.quizzard.presentation.screens.categorySelection.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizzard.R

@Composable
fun CategoryTopAppBar(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp),
                text = "👋 Hi $name ,",
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = stringResource(R.string.great_to_see_you),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.padding(20.dp),
            painter = painterResource(id = R.drawable.person),
            contentDescription = null
        )
    }
}
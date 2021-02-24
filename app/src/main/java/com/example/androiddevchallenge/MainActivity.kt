/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.blue500
import com.example.androiddevchallenge.ui.theme.pink500
import androidx.compose.runtime.remember

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

val examplePuppy1 = Puppy(
    name = "Macallan",
    picture = R.drawable.puppy_1,
    age = "12 Weeks",
    gender = Gender.Male,
    description = "Cute as a button and extremely smart!"
)

val examplePuppy2 = Puppy(
    name = "Astrid",
    picture = R.drawable.puppy_2,
    age = "4 Months",
    gender = Gender.Female,
    description = "Full of energy and looking for anyone or anything to play with."
)

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(text = "Adoptable Puppies",
                style = typography.h4)
            PuppyList(puppies = listOf(
                examplePuppy1,
                examplePuppy2
            ))
        }
    }
}

@Composable
fun PuppyList(puppies: List<Puppy>) {
    // Holds the topic that is currently expanded to show its body.
    var expandedPuppy by remember { mutableStateOf<Puppy?>(null) }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(puppies) { puppy ->
            PuppyRow(
                puppy = puppy,
                expanded = expandedPuppy == puppy,
                onClick = {
                    expandedPuppy = if (expandedPuppy == puppy) null else puppy
                }
            )
        }
    }
}

@Composable
fun PuppyRow(puppy: Puppy, expanded: Boolean, onClick: () -> Unit) {

    Card(
        elevation = 4.dp,
        backgroundColor = if(puppy.gender == Gender.Male) blue500 else pink500,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .animateContentSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(painter = painterResource(id = puppy.picture),
                    contentDescription = null,
                    modifier = Modifier
                        .height(80.dp)
                        .width(160.dp)
                        .clip(RoundedCornerShape(4.dp)))
                Column {
                    Text(text = puppy.name,
                        style = typography.h6
                    )
                }
            }

            if (expanded) {
                Text(text = "Age", style = typography.subtitle1, modifier = Modifier.padding(start = 16.dp))
                Text(text = puppy.age, style = typography.caption, modifier = Modifier.padding(start = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description", style = typography.subtitle1, modifier = Modifier.padding(start = 16.dp))
                Text(text = puppy.description, style = typography.caption, modifier = Modifier.padding(start = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

data class Puppy(
    val name: String,
    val picture: Int,
    val age: String,
    val gender: Gender,
    val description: String
)

enum class Gender {
    Male,
    Female
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

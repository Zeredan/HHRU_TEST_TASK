package com.example.test_task.Screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import android.view.WindowManager
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_task.Models.Offer
import com.example.test_task.Models.Vacancy
import com.example.test_task.Models.Words
import com.example.test_task.R
import com.example.test_task.ScreenViewModels.MainScreenViewModel
import com.example.test_task.ScreenViewModels.SearchScreenViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun OfferCard(offer: Offer){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                Intent()
                    .apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(offer.link)
                    }
                    .run {
                        context.startActivity(this)
                    }
            }
            .background(colorResource(R.color.Gray1))
            .padding(10.dp)
            .size(150.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    )
    {
        Image(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(
                id = when(offer.id) {
                    "near_vacancies" -> R.drawable.location_mark
                    "level_up_resume" -> R.drawable.star_empty
                    "temporary_job" -> R.drawable.notebook
                    else -> R.drawable.ic_launcher_foreground
                }
            ),
            contentDescription = null
        )
        Text(offer.title, maxLines = offer.button?.text?.let{2} ?: 3, color = colorResource(R.color.White))
        offer.button?.text?.let{ obt ->
            Text(obt, color = colorResource(R.color.DarkGreen))
        }
    }
}
@Composable
fun VacancyCard(vacancy: Vacancy, isFavorite: Boolean, onClick: () -> Unit, vm: SearchScreenViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .background(colorResource(R.color.Gray1))
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        )
        {
            if (vacancy.lookingNumber > 0) Text("сейчас просматрива${if (vacancy.lookingNumber > 1) "ю" else "е"}т ${vacancy.lookingNumber} ${Words.Человек[vacancy.lookingNumber]}", color = colorResource(R.color.Green))
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        vm.toggleFavorite(vacancy)
                    },
                painter = painterResource(id = if (isFavorite) R.drawable.heart_active else R.drawable.empty_heart),
                contentDescription = null
            )
        }
        Text(vacancy.title, fontSize = 18.sp, color = colorResource(R.color.White))
        Text(vacancy.address.town, color = colorResource(R.color.White))
        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(vacancy.company, color = colorResource(R.color.White))
            Spacer(Modifier.width(5.dp))
            Image(
                modifier = Modifier
                    .size(15.dp),
                painter = painterResource(id = R.drawable.check_mark),
                contentDescription = null
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                modifier = Modifier
                    .size(15.dp),
                painter = painterResource(id = R.drawable.luggage),
                contentDescription = null
            )
            Spacer(Modifier.width(5.dp))
            Text(vacancy.experience.previewText, color = colorResource(R.color.White))
        }
        Text("Опубликовано: ${vacancy.publishedDate}", color = colorResource(R.color.Gray3))
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.Green))
        )
        {
            Text("Откликнуться", color = Color.White)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable //typo: unnecessary, but replace navControllers straightforward actions with onClick outer lambdas, bcs this function may not know upper navigation model
fun UnexpandedSearchScreenShowcase(navController: NavController, vm: SearchScreenViewModel, anchoredDraggableState: AnchoredDraggableState<String>){
    val density = LocalDensity.current.density
    Column(
        modifier = Modifier
            .background(colorResource(R.color.Black))
            .padding(15.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            var textFieldValue by remember {
                mutableStateOf("")
            }
            var textFieldHeight by remember{
                mutableStateOf(10.dp)
            }
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .onSizeChanged { textFieldHeight = it.height.dp / density },
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                leadingIcon = {
                    Image(
                        modifier = Modifier
                            .size(25.dp),
                        painter = painterResource(id = R.drawable.search_gray),
                        contentDescription = null
                    )
                },
                label = {
                    Text("Должность, ключевые слова", color = colorResource(R.color.Gray3))
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(R.color.Gray2),
                    focusedContainerColor = colorResource(R.color.Gray2),
                    errorContainerColor = colorResource(R.color.Gray2),
                    focusedTextColor = colorResource(R.color.White),
                    unfocusedTextColor = colorResource(R.color.White)
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(textFieldHeight)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(R.color.Gray2)),
                contentAlignment = Alignment.Center
            )
            {
                Image(
                    modifier = Modifier
                        .size(25.dp),
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.height(25.dp))
        if (vm.offers.size > 0){
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                items(vm.offers){
                    OfferCard(offer = it)
                }
            }
        }
        Spacer(Modifier.height(50.dp))
        Text("Вакансии для вас", fontSize = 24.sp, color = colorResource(R.color.White))
        Spacer(Modifier.height(25.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            vm.vacancies.run{try{this.slice(0..2)}catch(e: Exception){this} }.forEach {
                VacancyCard(vacancy = it, vm.isFavorite(it), { vm.currentVacancy = it; navController.navigate("Vacancy") }, vm)
            }
        }
        if (vm.vacancies.size > 3){
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {
                          navController.navigate("Expanded")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.Green))
            )
            {
                Text("Показать еще ${vm.vacancies.size - 3} вакансии", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpandedSearchScreenShowcase(navController: NavController, vm: SearchScreenViewModel, anchoredDraggableState: AnchoredDraggableState<String>){
    val density = LocalDensity.current.density
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    )
    {
        Column(
            modifier = Modifier
                .background(colorResource(R.color.Black))
                .padding(15.dp)
                .fillMaxSize()
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                var textFieldValue by remember {
                    mutableStateOf("")
                }
                var textFieldHeight by remember {
                    mutableStateOf(10.dp)
                }
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .onSizeChanged { textFieldHeight = it.height.dp / density },
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                    },
                    leadingIcon = {
                        Image(
                            modifier = Modifier
                                .clickable {
                                    navController.popBackStack()
                                }
                                .size(25.dp),
                            painter = painterResource(id = R.drawable.left_arrow),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Должность по подходящим вакансиям", color = colorResource(R.color.Gray3))
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = colorResource(R.color.Gray2),
                        focusedContainerColor = colorResource(R.color.Gray2),
                        errorContainerColor = colorResource(R.color.Gray2),
                        focusedTextColor = colorResource(R.color.White),
                        unfocusedTextColor = colorResource(R.color.White)
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(textFieldHeight)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(R.color.Gray2)),
                    contentAlignment = Alignment.Center
                )
                {
                    Image(
                        modifier = Modifier
                            .size(25.dp),
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = null
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                item {
                    Spacer(Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text("${vm.vacancies.size} ${Words.Вакансия[vm.vacancies.size]}", color = colorResource(R.color.White))
                        Spacer(Modifier.weight(1f))
                        Text("По соответствию", color = colorResource(R.color.Blue))
                        Image(
                            modifier = Modifier
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.arrows_top_and_bot),
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.height(25.dp))
                }
                items(vm.vacancies) {
                    VacancyCard(
                        vacancy = it,
                        vm.isFavorite(it),
                        { vm.currentVacancy = it; navController.navigate("Vacancy") },
                        vm
                    )
                }
            }
        }
        var mapElem = createRef()
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { }
                .background(colorResource(R.color.Gray3))
                .padding(10.dp)
                .constrainAs(mapElem) {
                    bottom.linkTo(parent.bottom, 30.dp)
                    end.linkTo(parent.end, 20.dp)
                }
        )
        {
            Image(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(id = R.drawable.map),
                contentDescription = null
            )
            Spacer(Modifier.width(5.dp))
            Text("Карта", color = colorResource(R.color.White))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyShowcase(navController: NavController, vm: SearchScreenViewModel){
    Column(
        modifier = Modifier
            .background(colorResource(R.color.Black))
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    )
    {
        vm.currentVacancy?.let{ vc ->
            var dialogExpanded by remember{ mutableStateOf(false) }
            var messageMode by remember{ mutableStateOf(false) }
            var currentMessage by remember{ mutableStateOf("") }

            if (dialogExpanded) {
                AlertDialog(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(colorResource(R.color.Black))
                        .fillMaxWidth(1f),
                    onDismissRequest = {
                        dialogExpanded = false
                        messageMode = false
                    }
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        ConstraintLayout {
                            var (pic, topText, bottomText) = createRefs()
                            Image(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .constrainAs(pic) {
                                        this.start.linkTo(parent.start)
                                        this.top.linkTo(parent.top, 50.dp)
                                    },
                                painter = painterResource(id = R.drawable.happy_face),
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier
                                    .constrainAs(topText){
                                        this.top.linkTo(pic.top)
                                        this.start.linkTo(pic.end, 10.dp)
                                        this.width = Dimension.fillToConstraints
                                    },
                                text = "Резюме для отклика",
                                color = colorResource(R.color.Gray3)
                            )
                            Text(
                                modifier = Modifier
                                    .constrainAs(bottomText){
                                        this.bottom.linkTo(pic.bottom)
                                        this.start.linkTo(pic.end, 10.dp)
                                        this.width = Dimension.fillToConstraints
                                    },
                                text = vc.company,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        Divider(thickness = 1.dp, color = Color.LightGray)
                        Spacer(Modifier.height(40.dp))
                        if (!messageMode){
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        messageMode = true
                                    }
                                    .padding(5.dp),
                                text = "Добавить сопроводительное",
                                color = Color.Green
                            )
                        }
                        else{
                            TextField(
                                value = currentMessage,
                                onValueChange = { currentMessage = it },
                                label = {Text("Ваше сопроводительное письмо", color = colorResource(R.color.Gray3))},
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = colorResource(R.color.Black),
                                    focusedContainerColor = colorResource(R.color.Black),
                                    unfocusedTextColor = colorResource(R.color.White),
                                    focusedTextColor = colorResource(R.color.White)
                                )
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(0.7f)
                                .height(50.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(colorResource(R.color.Green))
                                .clickable {
                                    dialogExpanded = false
                                },
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text("Откликнуться", color = Color.White)
                        }
                    }
                }
            }
            Spacer(Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Image(
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .padding(10.dp)
                        .size(25.dp),
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = null
                )
                Spacer(Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(25.dp),
                    painter = painterResource(id = R.drawable.eye_active),
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(25.dp),
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .clickable {
                            vm.toggleFavorite(vc)
                        }
                        .padding(10.dp)
                        .size(25.dp),
                    painter = painterResource(id = if (vm.isFavorite(vc)) R.drawable.heart_active else R.drawable.empty_heart),
                    contentDescription = null
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(vc.title, color = colorResource(R.color.White), fontSize = 24.sp)
            Spacer(Modifier.height(5.dp))
            Text(vc.salary.full, color = colorResource(R.color.White))
            Spacer(Modifier.height(5.dp))
            Text(vc.experience.text, color = colorResource(R.color.White))
            Text(vc.schedules.joinToString(", "), color = colorResource(R.color.White))
            Spacer(Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            {
                if (vc.appliedNumber > 0)
                {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(colorResource(R.color.Green))
                            .padding(10.dp)
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.Top,
                    )
                    {
                        Text("${vc.appliedNumber} человек уже откликнулось", modifier = Modifier
                            .weight(15f)
                            .align(Alignment.CenterVertically), color = colorResource(R.color.White))
                        Spacer(Modifier.weight(1f))
                        Image(
                            modifier = Modifier
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.user_icon),
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                }
                if (vc.lookingNumber > 0)
                {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(colorResource(R.color.Green))
                            .padding(10.dp)
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.Top
                    )
                    {
                        Text("${vc.lookingNumber} ${Words.Человек[vc.lookingNumber]} сейчас смотр${if (vc.lookingNumber > 1) "я" else "и"}т", modifier = Modifier
                            .weight(15f)
                            .align(Alignment.CenterVertically), color = colorResource(R.color.White))
                        Spacer(Modifier.weight(1f))
                        Image(
                            modifier = Modifier
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.eye_on_green),
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(R.color.Gray2))
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(vc.company, fontSize = 18.sp, color = colorResource(R.color.White))
                    Image(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.check_mark),
                        contentDescription = null
                    )
                }
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(100.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Text("${vc.address.town}, ${vc.address.street}, ${vc.address.house}", color = colorResource(R.color.White))
            }
            Spacer(Modifier.height(5.dp))
            Text(
                modifier = Modifier
                .fillMaxWidth(),
                text = vc.description ?: "",
                color = colorResource(R.color.White)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text("Ваши Задачи", color = colorResource(R.color.White), fontSize = 22.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = vc.responsibilities,
                color = colorResource(R.color.White)
            )
            Spacer(Modifier.height(15.dp))
            Text("Задайте вопрос работодателю", color = colorResource(R.color.White))
            Text("Он получит его с отклиом на вакансию", color = colorResource(R.color.Gray3))
            vc.questions.forEach { question ->
                Button(
                    onClick = {
                        messageMode = true
                        currentMessage = question
                        dialogExpanded = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Gray2)
                    )
                )
                {
                    Text("$question")
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Green)
                    .clickable {
                        messageMode = false
                        currentMessage = ""
                        dialogExpanded = true
                    },
                contentAlignment = Alignment.Center,
            )
            {
                Text("Откликнуться", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    vm: SearchScreenViewModel = viewModel<SearchScreenViewModel>(),
    anchoredDraggableState: AnchoredDraggableState<String>
)
{
    val navController = rememberNavController()
    navController.enableOnBackPressed(false)
    NavHost(
        navController = navController,
        startDestination = "Unexpanded"
    )
    {
        composable("Unexpanded"){
            UnexpandedSearchScreenShowcase(vm = vm, navController = navController, anchoredDraggableState = anchoredDraggableState)
        }
        composable("Expanded"){
            ExpandedSearchScreenShowcase(vm = vm, navController = navController, anchoredDraggableState = anchoredDraggableState)
        }
        composable("Vacancy"){
            VacancyShowcase(navController, vm)
        }
    }

}
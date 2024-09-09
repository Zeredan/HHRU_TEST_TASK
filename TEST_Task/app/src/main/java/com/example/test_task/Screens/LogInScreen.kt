package com.example.test_task.Screens

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
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_task.Models.Offer
import com.example.test_task.Models.Vacancy
import com.example.test_task.R
import com.example.test_task.ScreenViewModels.LogInScreenViewModel
import com.example.test_task.ScreenViewModels.MainScreenViewModel
import com.example.test_task.ScreenViewModels.SearchScreenViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun DigitalTextField(value: String, onValueChanged: (String) -> Unit, countDigits: Int, normalTextColor: Color, selectedTextColor: Color, normalBorderColor: Color, selectedBorderColor: Color){
    BasicTextField(
        value,
        onValueChange = onValueChanged,
        singleLine = true,
    )
    {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        )
        {
            repeat(countDigits) {
                Text(
                    modifier = Modifier
                        .border(
                            if (it == value.length) 3.dp else 2.dp,
                            color = if (it < value.length) selectedBorderColor else normalBorderColor,
                            RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .padding(15.dp)
                        .size(40.dp, 60.dp),
                    text = try{value[it].toString()}catch (e: Exception){"*"},
                    textAlign = TextAlign.Center,
                    color = if (it < value.length) selectedTextColor else normalTextColor
                )
            }
        }
    }
}


@Composable
fun EmailInputScreen(vm: LogInScreenViewModel, onGoodInput: () -> Unit){
    LaunchedEffect(vm.email){
        vm.isError = false
    }
    Column(
        modifier = Modifier
            .background(colorResource(R.color.Black))
            .padding(15.dp)
            .fillMaxSize()
    )
    {
        Spacer(Modifier.height(40.dp))
        Text("Вход в личный кабинет", color = colorResource(R.color.White), fontSize = 24.sp)
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(R.color.Gray2))
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        )
        {
            Text("Поиск работы", color = colorResource(R.color.White), fontSize = 18.sp)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = vm.email,
                onValueChange = { vm.email = it },
                label = { Text("Электронная почта или телефон") },
                leadingIcon = {
                    if (vm.email.isEmpty()){
                        Image(
                            modifier = Modifier
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.email_inactive),
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (vm.email.isNotEmpty()){
                        Image(
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    vm.email = ""
                                },
                            painter = painterResource(id = R.drawable.x),
                            contentDescription = null
                        )
                    }
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                isError = vm.isError,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(R.color.Gray2),
                    focusedContainerColor = colorResource(R.color.Gray2),
                    errorContainerColor = colorResource(R.color.Gray2),
                    unfocusedTextColor = colorResource(R.color.White),
                    focusedTextColor = colorResource(R.color.White),
                    errorTextColor = colorResource(R.color.Red)
                )
            )
            if (vm.isError) Text("Вы ввели неверный e-mail", color = Color.Red)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Button(
                    onClick = {
                        if (vm.isEmailValid()){
                            onGoodInput()
                        }
                        else{
                            vm.isError = true
                        }
                    },
                    modifier = Modifier
                        .weight(1f),
                    enabled = vm.email.length > 0,
                    contentPadding = PaddingValues(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.Blue), disabledContainerColor = colorResource(R.color.DarkBlue))
                )
                {
                    Text("Продолжить", color = colorResource(R.color.White))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(5.dp),
                    text = "Войти с паролем",
                    color = colorResource(R.color.Blue)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(R.color.Gray2))
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        )
        {
            Text("Поиск сотрудников", color = colorResource(R.color.White), fontSize = 18.sp)
            Text("Размещение вакансий и доступ к базе резюме", color = colorResource(R.color.White))
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green))
            )
            {
                Text("Я ищу сотрудников", color = colorResource(R.color.White))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CodeVerificationScreen(vm: LogInScreenViewModel, onCodeVerify: () -> Unit){
    Column(
        modifier = Modifier
            .background(colorResource(R.color.Black))
            .padding(15.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = "Отправили код на ${vm.email}", modifier = Modifier.align(Alignment.Start), color = colorResource(R.color.White), fontSize = 24.sp)
        Spacer(Modifier.height(15.dp))
        Text(text = "Напишите его, чтобы подтвердить, что это вы,а не кто-то другой входит в личный кабинет", modifier = Modifier.align(Alignment.Start), color = colorResource(R.color.White), fontSize = 18.sp)
        Spacer(Modifier.height(15.dp))
        DigitalTextField(
            value = vm.passwordCode, onValueChanged = { if (it.length < 5) vm.passwordCode = it },
            countDigits = 4,
            Color.Yellow,
            Color.Green,
            Color.Yellow,
            Color.Green
        )
        Spacer(Modifier.height(15.dp))
        Button(
            onClick = {
                      onCodeVerify()
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(10.dp),
            enabled = vm.passwordCode.length >= 4,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.Blue), disabledContainerColor = colorResource(R.color.DarkBlue))
        )
        {
            Text("Подтвердить", color = colorResource(R.color.White), fontSize = 18.sp)
        }
    }
}

@Composable
fun LogInScreen(
    onLoggedIn: () -> Unit,
    vm : LogInScreenViewModel = viewModel<LogInScreenViewModel>()
)
{
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = "EmailInput"
    )
    {
        composable("EmailInput"){
            EmailInputScreen(vm){
                navController.navigate("CodeVerification")
            }
        }
        composable("CodeVerification"){
            CodeVerificationScreen(vm){
                onLoggedIn()
            }
        }
    }
}
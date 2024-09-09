package com.example.test_task.Screens

import android.annotation.SuppressLint
import android.os.Build
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_task.R
import com.example.test_task.ScreenViewModels.MainScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainScreenViewModel = viewModel<MainScreenViewModel>()
)
{
    val density = LocalDensity.current.density
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val coroutineScope = rememberCoroutineScope()

    var anchoredDraggableState = remember(vm.screens.firstStateRecord){
        AnchoredDraggableState<String>(
            initialValue = vm.screens[0].name,
            anchors = DraggableAnchors {
                vm.screens.forEachIndexed { ind, (name, nCount, func) ->
                    name at ind * screenWidth.toFloat() * density
                }
            },
            positionalThreshold = {
                it * 0.5f
            },
            velocityThreshold = {
                100f
            },
            animationSpec = tween(500, 0, EaseInOutCubic)
        )
    }

    var contentScrollState = rememberScrollState()
    LaunchedEffect(anchoredDraggableState.offset){
        contentScrollState.scrollTo(anchoredDraggableState.offset.toInt())
    }

    BackHandler(
        enabled = anchoredDraggableState.currentValue != "Поиск"
    )
    {
        coroutineScope.launch { anchoredDraggableState.animateTo("Поиск") }
    }

    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    )
    {
        NavHost(
            modifier = Modifier
                .weight(19f)
                .fillMaxWidth(),
            navController = navController,
            startDestination = "LogIn"
        )
        {
            composable("MainContent")
            {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(contentScrollState, enabled = false)
                        .anchoredDraggable(
                            anchoredDraggableState,
                            orientation = Orientation.Horizontal,
                            reverseDirection = true
                        )
                )
                {
                    vm.screens.forEach {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(screenWidth.dp),
                            contentAlignment = Alignment.Center
                        )
                        {
                            it.callingComposable(anchoredDraggableState)
                        }
                    }
                }
            }
            composable("LogIn")
            {
                LogInScreen(onLoggedIn = { navController.navigate("MainContent") } )
            }
        }
        Divider(
            thickness = 2.dp
        )
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            vm.screens.forEach { scrInfo ->
                val animatedColor by animateColorAsState(
                    targetValue = if (scrInfo.name == anchoredDraggableState.targetValue) Color.Blue else Color.White,
                    animationSpec = tween(1500, 0, EaseOutExpo)
                )
                Column(
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                anchoredDraggableState.animateTo(scrInfo.name)
                            }
                        }
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    if (scrInfo.notificationCount <= 0){
                        AnimatedContent(
                            targetState = anchoredDraggableState.targetValue == scrInfo.name,
                            label = "",
                            transitionSpec = {
                                ContentTransform(
                                    targetContentEnter = fadeIn(tween(500)),
                                    initialContentExit = fadeOut(tween(500))
                                )
                            }
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(30.dp),
                                painter = painterResource(id = if (scrInfo.name == anchoredDraggableState.targetValue) scrInfo.imgSelected else scrInfo.imgNotSelected),
                                contentDescription = scrInfo.name
                            )
                        }
                    }
                    else {
                        BadgedBox(
                            modifier = Modifier
                                .weight(1f),
                            badge = {
                                Box(
                                    modifier = Modifier
                                        .offset(-10.dp, 10.dp)
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(Color.Red),
                                    contentAlignment = Alignment.Center
                                )
                                {
                                    Text(scrInfo.notificationCount.toString(), color = Color.White)
                                }
                            }
                        )
                        {
                            AnimatedContent(
                                targetState = anchoredDraggableState.targetValue == scrInfo.name,
                                label = "",
                                transitionSpec = {
                                    ContentTransform(
                                        targetContentEnter = fadeIn(),
                                        initialContentExit = fadeOut()
                                    )
                                }
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(30.dp),
                                    painter = painterResource(id = if (scrInfo.name == anchoredDraggableState.targetValue) scrInfo.imgSelected else scrInfo.imgNotSelected),
                                    contentDescription = scrInfo.name
                                )
                            }
                        }
                    }
                    Text(scrInfo.name, fontSize = 14.sp, color = animatedColor)
                }
            }
        }
    }
}
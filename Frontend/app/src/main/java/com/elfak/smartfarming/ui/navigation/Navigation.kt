package com.elfak.smartfarming.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Cable
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elfak.smartfarming.data.models.DrawerMenuItem
import com.elfak.smartfarming.ui.screens.loginScreen.LoginScreen
import com.elfak.smartfarming.ui.screens.loginScreen.LoginScreenViewModel
import com.elfak.smartfarming.ui.screens.registerScreen.RegisterScreen
import com.elfak.smartfarming.ui.screens.registerScreen.RegisterScreenViewModel
import com.elfak.smartfarming.ui.screens.splashScreen.SplashScreen
import com.elfak.smartfarming.ui.screens.splashScreen.SplashScreenViewModel
import com.elfak.smartfarming.ui.screens.welcomeScreen.WelcomeScreen
import kotlinx.coroutines.launch


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = Screen.fromRoute(currentDestination?.route ?: "")

    val showScaffold = currentDestination?.parent?.route == Screen.Main.route
    if (showScaffold) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val items = prepareMenuList(navController)
        val selectedItem = remember { mutableStateOf(items[0]) }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = item.name) },
                            label = { Text(item.name) },
                            selected = item.name == selectedItem.value.name,
                            onClick = {
                                item.action()
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            content = {
                Scaffold(
                    topBar = {
                        /*TODO create component*/
                        AnimatedVisibility(visible = currentDestination?.parent?.route == Screen.Main.route) {
                            Text(text = currentScreen?.displayName ?: "ERROR")
                        }
                    },
                    bottomBar = {
                        AnimatedVisibility(visible = currentScreen?.route == Screen.HomeScreen.route) {
                            /*TODO create component*/
                            BottomAppBar {
                                Row {
                                    Button(onClick = { /*TODO*/ }) {
                                        Text ("Add device")
                                    }
                                    Spacer(Modifier.width(3.dp))
                                    Button(onClick = {
                                        navController.navigate(Screen.AddRuleScreen.route)
                                    }) {
                                        Text ("Add rule")
                                    }
                                }
                            }
                        }
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.SplashScreen.route) {
                            val viewModel = hiltViewModel<SplashScreenViewModel>()
                            SplashScreen(
                                viewModel = viewModel,
                                navigateToHome = {
                                    navController.navigate(Screen.Main.route) {
                                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                    }
                                },
                                navigateToWelcome = {
                                    navController.navigate(Screen.WelcomeScreen.route) {
                                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.WelcomeScreen.route) {
                            WelcomeScreen(
                                navigateToLogin = { navController.navigate(Screen.LoginScreen.route) },
                                navigateToRegister = { navController.navigate(Screen.RegisterScreen.route) }
                            )
                        }
                        composable(Screen.LoginScreen.route) {
                            val loginViewModel = hiltViewModel<LoginScreenViewModel>()
                            LoginScreen(
                                viewModel = loginViewModel,
                                navigateBack = { navController.popBackStack() },
                                navigateToHome = {
                                    navController.navigate(Screen.Main.route) {
                                        // removes all from backstack so when user clicks back button it will close the app
                                        popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                                    }
                                },
                            )
                        }
                        composable(Screen.RegisterScreen.route){
                            val registerViewModel = hiltViewModel<RegisterScreenViewModel>()
                            RegisterScreen(
                                viewModel = registerViewModel,
                                navigateBack = { navController.popBackStack() },
                                navigateToHome = {
                                    navController.navigate(Screen.Main.route){
                                        popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        mainGraph(navController = navController)
                    }
                }
            }
        )

    }
    else {
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
        ) {
            composable(Screen.SplashScreen.route) {
                val viewModel = hiltViewModel<SplashScreenViewModel>()
                SplashScreen(
                    viewModel = viewModel,
                    navigateToHome = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.SplashScreen.route) { inclusive = true }
                        }
                    },
                    navigateToWelcome = {
                        navController.navigate(Screen.WelcomeScreen.route) {
                            popUpTo(Screen.SplashScreen.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.WelcomeScreen.route) {
                WelcomeScreen(
                    navigateToLogin = { navController.navigate(Screen.LoginScreen.route) },
                    navigateToRegister = { navController.navigate(Screen.RegisterScreen.route) }
                )
            }
            composable(Screen.LoginScreen.route) {
                val loginViewModel = hiltViewModel<LoginScreenViewModel>()
                LoginScreen(
                    viewModel = loginViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToHome = {
                        navController.navigate(Screen.Main.route) {
                            // removes all from backstack so when user clicks back button it will close the app
                            popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(Screen.RegisterScreen.route){
                val registerViewModel = hiltViewModel<RegisterScreenViewModel>()
                RegisterScreen(
                    viewModel = registerViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToHome = {
                        navController.navigate(Screen.Main.route){
                            popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                        }
                    }
                )
            }
            mainGraph(navController = navController)
        }
    }

}

fun prepareMenuList(navController: NavController): List<DrawerMenuItem> {
    val items = listOf(
        DrawerMenuItem(
            name = Screen.HomeScreen.displayName,
            icon = Icons.Rounded.Home,
            action = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.Main.route) { inclusive = true }
                }
            }
        ),
        DrawerMenuItem(
            name = Screen.ListScreen.displayName,
            icon = Icons.Rounded.Sensors,
            action = {
                navController.navigate(Screen.ListScreen.route) {
                    popUpTo(Screen.Main.route)
                }
            }
        ),
        DrawerMenuItem(
            name = Screen.SettingScreen.displayName,
            icon = Icons.Rounded.Settings,
            action = {
                navController.navigate(Screen.SettingScreen.route) {
                    popUpTo(Screen.Main.route)
                }
            }
        ),
        DrawerMenuItem(
            name = Screen.NotificationScreen.displayName,
            icon = Icons.Rounded.Home,
            action = {
                navController.navigate(Screen.NotificationScreen.route) {
                    popUpTo(Screen.Main.route)
                }
            }
        ),
        DrawerMenuItem(
            name = "Sign out",
            icon = Icons.Rounded.ExitToApp,
            action = {
                navController.navigate(Screen.WelcomeScreen.route) {
                    popUpTo(Screen.Main.route) { inclusive = true }
                }
            }
        ),

    )
    return items
}

